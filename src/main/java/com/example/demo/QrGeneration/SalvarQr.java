package com.example.demo.QrGeneration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SalvarQr {
    private String Url = "jdbc:h2:mem:banco";
    private String Usuario = "admin";
    private String Senha = "123";

    public void Save(BufferedImage Qr, String nomeQr, String QRUrl) {
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(Qr, "png", baos);
            byte[] QrBytes = baos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }

        try(Connection conn = DriverManager.getConnection(Url, Usuario, Senha)){
            String sql = "INSERT INTO qrcodes (nome_arquivo, QR, QRUrl) VALUES(?, ?)";
            PreparedStatement sts = conn.prepareStatement(sql);

            sts.setString(1, nomeQr);
            sts.setString(2, QRUrl);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
