package com.example.demo.QrGeneration;

import com.example.demo.Modelos.QRCodeData;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Service
public class QRgenerator {



    public void GenerateQR(int bikeid) {
        String caminho = "QrCode_" + bikeid + ".png";
        String texto = "localhost:8080/form?bikeid=" + bikeid;
        SalvarQr Salvar = new SalvarQr() ;
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(
                    texto,
                    BarcodeFormat.QR_CODE,
                    300,
                    300
            );

            Path path = FileSystems.getDefault().getPath(caminho);
            if (Files.exists(path)) {
                System.out.println("Código já existente, cheque no diretório do projeto em CodeImages");
            } else {
                QRCodeData Qr = new QRCodeData(bikeid, caminho, texto, MatrixToImageWriter.toBufferedImage(matrix));
                Salvar.Save(Qr);
                System.out.println("Código QR gerado com sucesso, confira na pasta CodeImages");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public List<String> listarQRCodes() {
        //List<String> codigos = new ArrayList<>();
        //Path dir = Paths.get("CodeImages");

        //try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.png")) {
            //for (Path file : stream) {
                //codigos.add(file.getFileName().toString());
            //}
        //} catch (IOException e) {
           // e.printStackTrace();
       // }

       // return ;
    //}
    //Inutilizado Devido a atualização para tudo ir para o banco de dados

    public void DeletarQrCodePorId(int id) {
        //Path path = Paths.get("CodeImages/QrCode_" + id + ".png");
        DeletarQr Deletar = new DeletarQr();
        try {

            //if (Files.exists(path)) {
                //Files.delete(path);
                //System.out.println("Arquivo deletado com sucesso!");
            //} else {
              //  System.out.println("Arquivo não encontrado.");
            //}
            Deletar.Delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void generateMultiple(String ids) {
        String[] idArray = ids.split(",");

        for (String idStr : idArray) {
            try {
                int id = Integer.parseInt(idStr.trim());
                GenerateQR(id);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido: " + idStr);
            }
        }
    }

}
