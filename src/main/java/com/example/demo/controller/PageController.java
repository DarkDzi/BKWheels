package com.example.demo.controller;

import com.example.demo.FeedBack.SalvarFormData;
import com.example.demo.FeedBack.UseFormData;
import com.example.demo.Modelos.FormData;
import com.example.demo.Modelos.QRCodeData;
import com.example.demo.QrGeneration.ListarQr;
import com.example.demo.QrGeneration.QRgenerator;
import jakarta.annotation.Resource;
import jakarta.annotation.Resources;
import jakarta.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class PageController {

    @Autowired
    private QRgenerator qrGenerator;



    @PostMapping("/QrGenerate/generate-multiple")
    public String generateMultipleQRCodes(@RequestParam("ids") String ids, Model model) {
        qrGenerator.generateMultiple(ids);
        model.addAttribute("mensagem", "QRCodes gerados com sucesso!");
        return "QrGenerate";
    }

    @PostMapping("/QrGenerate/generate-single")
    public String generateSingle(@RequestParam("bikeId") int bikeId, Model model) {
        qrGenerator.GenerateQR(bikeId);
        model.addAttribute("mensagem", "QR Code gerado para ID: " + bikeId);
        return "QrGenerate";
    }

    @GetMapping("/AdminMenu")
    public String Menu(Model mode){
        return"AdminMenu";
    }


    @GetMapping("/AdminMenu/QrGenerate")
    public String Admin(Model model) {
        model.addAttribute("mensagem", "Admin");
    return"QrGenerate";
        }

    @GetMapping("/AdminMenu/FeedBackView")
    public String FeedBack(Model model){
        UseFormData useFormData = new UseFormData();
        List<FormData> feedsData = useFormData.GetFeedsData();

        model.addAttribute("feedsData", feedsData);



        return"FeedBackView";
    }





    @GetMapping("/AdminMenu/QrGenerate/List")
    public String list(Model model) {
        ListarQr listarQr = new ListarQr();
        List<QRCodeData> listaQRCodes = listarQr.Listar();
        model.addAttribute("qrcodes", listaQRCodes);
        return "Lista";
    }
    @PostMapping("/QrGenerate/delete")
    public String deleteQRCode(@RequestParam("id") int id) {
        com.example.demo.QrGeneration.DeletarQr deletarQr = new com.example.demo.QrGeneration.DeletarQr();
        deletarQr.Delete(id);
        return "redirect:/AdminMenu/QrGenerate/List";
    }


    @GetMapping("/qr/image/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getQRCodeImage(@PathVariable int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:./data", "admin", "123")) {
            String sql = "SELECT QR FROM qrcodes WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("QR");

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/download/qr/image/{id}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> downloadQRCode(@PathVariable int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:./data", "admin", "123")) {
            String sql = "SELECT QR, nome_arquivo FROM qrcodes WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("QR");
                String nomeArquivo = rs.getString("nome_arquivo");

                // Garante que o nome tenha .png
                if (!nomeArquivo.toLowerCase().endsWith(".png")) {
                    nomeArquivo += ".png";
                }

                ByteArrayResource resource = new ByteArrayResource(imageBytes);

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + nomeArquivo + "\"")
                        .body(resource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }







    @GetMapping("/form")
    public String mostrarFormulario(@RequestParam int bikeid, Model model) {
        model.addAttribute("bikeid", bikeid);
        return "form";
    }

    @PostMapping("/form")
    public String receberFeedback(@RequestParam int nota,
                                  @RequestParam String comentario,
                                  @RequestParam(required = false) boolean reparo,
                                  @RequestParam int bikeid,
                                  @RequestParam String nome,
                                  Model model) {

        SalvarFormData SalvaForm = new SalvarFormData();
        SalvaForm.Save(nota, comentario, reparo, bikeid, nome);
        model.addAttribute("mensagem", "Obrigado Pelo Feedback!");
        return "form";
    }
    @GetMapping(value = "/feedgrafico", produces = MediaType.IMAGE_PNG_VALUE)
        public void gerarGrafico(HttpServletResponse response) throws IOException {
           UseFormData graficoData = new UseFormData();

        List<FormData> top3 = graficoData.GetFeedsData().stream()
                .sorted(Comparator.comparingInt(FormData::getNota).reversed())
                .limit(3)
                .collect(Collectors.toList());






            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for(FormData f : top3) {
                String idnota = "bikedid" + f.getBikeid();
                dataset.addValue(f.getNota(), "Nota", idnota);
            }
            JFreeChart chart = ChartFactory.createBarChart(
                    "Gráfico de Vendas",
                    "Mês",
                    "Valor",
                    dataset
            );

            response.setContentType("image/png");
            OutputStream out = response.getOutputStream();
            ChartUtils.writeChartAsPNG(out, chart, 600, 400);
            out.close();
        }
    }






