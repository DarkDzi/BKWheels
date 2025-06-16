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
    @GetMapping("/AdminMenu")
    public String Menu(Model mode){
        return"AdminMenu";
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
    }






