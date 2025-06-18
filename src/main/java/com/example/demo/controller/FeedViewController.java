package com.example.demo.controller;

import com.example.demo.FeedBack.DeletarFeed;
import com.example.demo.FeedBack.UseFormData;
import com.example.demo.Modelos.FormData;
import jakarta.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class FeedViewController {
    @GetMapping("/AdminMenu/FeedBackView")
    public String FeedBack(Model model){
        UseFormData useFormData = new UseFormData();
        List<FormData> feedsData = useFormData.GetFeedsData();

        model.addAttribute("feedsData", feedsData);



        return"FeedBackView";
    }
    @PostMapping("/FeedBackView/delete")
    public String deleteFeedBack(@RequestParam("id") int id) {
        DeletarFeed deletarFeedBack = new DeletarFeed();
        deletarFeedBack.Delete(id);
        return "redirect:/AdminMenu/FeedBackView";
    }




    @GetMapping(value = "/feedgraficobar", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public void gerarGraficoBarra(HttpServletResponse response) throws IOException {
        UseFormData graficoData = new UseFormData();

        List<FormData> dados = graficoData.GetFeedsData().stream()
                .sorted(Comparator.comparingInt(FormData::getNota).reversed())
                .collect(Collectors.toList());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (FormData f : dados) {
            String idnota = "Bike " + f.getBikeid();
            dataset.addValue(f.getNota(), "Nota", idnota);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Avaliação De Bicicleta",
                "BikeID",
                "Nota",
                dataset
        );


        var plot = chart.getCategoryPlot();
        var domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                org.jfree.chart.axis.CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0) // 30°
        );


        chart.setPadding(new RectangleInsets(10, 10, 40, 10));

        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chart, 700, 400);
        }
    }

    @GetMapping(value = "/feedgraficopizza", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public void gerarGraficoPizza1(HttpServletResponse response) throws IOException {
        UseFormData graficoData = new UseFormData();
        List<FormData> feedbacks = graficoData.GetFeedsData();

        Map<Integer, Long> notasDistribuicao = feedbacks.stream()
                .collect(Collectors.groupingBy(FormData::getNota, Collectors.counting()));

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<Integer, Long> entry : notasDistribuicao.entrySet()) {
            String label = entry.getKey() + " Estrelas";
            dataset.setValue(label, entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribuição de Notas de Feedback",
                dataset,
                true, true, false
        );


        org.jfree.chart.plot.PiePlot plot = (org.jfree.chart.plot.PiePlot) chart.getPlot();
        plot.setLabelGenerator(new org.jfree.chart.labels.StandardPieSectionLabelGenerator(
                "{0} ({2})"
        ));

        response.setContentType("image/png");
        try (OutputStream out = response.getOutputStream()) {
            ChartUtils.writeChartAsPNG(out, chart, 700, 400);
        }
    }

        }










