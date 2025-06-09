package com.example.demo.controller;

import com.example.demo.Modelos.QRCodeData;
import com.example.demo.QrGeneration.ListarQr;
import com.example.demo.QrGeneration.QRgenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class PageController {

    @Autowired
    private QRgenerator qrGenerator;

    @PostMapping("/Admin/generate-multiple")
    public String generateMultipleQRCodes(@RequestParam("ids") String ids, Model model) {
        qrGenerator.generateMultiple(ids);
        model.addAttribute("mensagem", "QRCodes gerados com sucesso!");
        return "Admin";
    }

    @PostMapping("/Admin/generate-single")
    public String generateSingle(@RequestParam("bikeId") int bikeId, Model model) {
        qrGenerator.GenerateQR(bikeId);
        model.addAttribute("mensagem", "QR Code gerado para ID: " + bikeId);
        return "Admin";
    }


    @GetMapping("/Admin")
    public String Admin(Model model) {
        model.addAttribute("mensagem", "Admin");
    return"Admin";
        }
    @GetMapping("/Admin/List")
    public String list(Model model) {
        ListarQr listarQr = new ListarQr();
        List<QRCodeData> listaQRCodes = listarQr.Listar();
        model.addAttribute("qrcodes", listaQRCodes);
        return "Lista";
    }
    @PostMapping("/Admin/delete")
    public String deleteQRCode(@RequestParam("id") int id) {
        com.example.demo.QrGeneration.DeletarQr deletarQr = new com.example.demo.QrGeneration.DeletarQr();
        deletarQr.Delete(id);
        return "redirect:/Admin/List";
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
    @GetMapping("/form")
    public String mostrarFormulario() {
        return "form";
    }

    @PostMapping("/form")
    public String receberFeedback(@RequestParam int nota,
                                  @RequestParam String comentario,
                                  @RequestParam(required = false) boolean reparo,
                                  Model model) {


        model.addAttribute("mensagem", "Feedback enviado com sucesso!");
        return "redirect:/Admin";
    }

}

