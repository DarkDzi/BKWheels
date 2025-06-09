package com.example.demo.QrGeneration;

import com.example.demo.Modelos.QRCodeData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SalvarQr {
    private String Url = "jdbc:h2:file:./data";
    private String Usuario = "admin";
    private String Senha = "123";

    public void Save(QRCodeData QR) {

        try(Connection conn = DriverManager.getConnection(Url, Usuario, Senha)){

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(QR.getQRImage(), "png", baos);
            byte[] QrBytes = baos.toByteArray();



            String sql = "INSERT INTO qrcodes (nome_arquivo, Qr, QRUrl) VALUES(?, ?, ?)";
            PreparedStatement sts = conn.prepareStatement(sql);

            sts.setString(1, QR.getNome_arquivo());
            sts.setString(3, QR.getQRUrl());
            sts.setBytes(2, QrBytes);
            sts.executeUpdate();

            sts.close();
            conn.close();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
