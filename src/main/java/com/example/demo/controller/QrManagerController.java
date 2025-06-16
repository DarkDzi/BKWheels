package com.example.demo.controller;

import com.example.demo.Modelos.QRCodeData;
import com.example.demo.QrGeneration.ListarQr;
import com.example.demo.QrGeneration.QRgenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Controller
public class QrManagerController {

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
    @GetMapping("/AdminMenu/QrGenerate")
    public String Admin(Model model) {
        model.addAttribute("mensagem", "Admin");
        return"QrGenerate";
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
}
