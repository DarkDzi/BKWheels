package com.example.demo.controller;

import com.example.demo.QrGeneration.QRgenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<String> listaQRCodes = qrGenerator.listarQRCodes();
        model.addAttribute("qrcodes", listaQRCodes);
        return "Lista";
    }


}

