package com.example.demo.QrGeneration;

import com.example.demo.Modelos.QRCodeData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;

public class SalvarQr {
    private String Url = "jdbc:h2:file:./data";
    private String Usuario = "admin";
    private String Senha = "123";

    public boolean tabelaExiste(String nomeTabela) {
        try (Connection conn = DriverManager.getConnection(Url, Usuario, Senha)) {
            DatabaseMetaData meta = conn.getMetaData();

            try (ResultSet rs = meta.getTables(null, null, nomeTabela.toUpperCase(), null)) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void Save(QRCodeData QR) {

        try(Connection conn = DriverManager.getConnection(Url, Usuario, Senha)){

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(QR.getQRImage(), "png", baos);
            byte[] QrBytes = baos.toByteArray();


            String sqltable = "CREATE TABLE IF NOT EXISTS qrcodes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome_arquivo VARCHAR(255) NOT NULL, " +
                    "QR BLOB NOT NULL, " +
                    "QRUrl VARCHAR(255))";

            Statement ststable = conn.createStatement();
             boolean existe = tabelaExiste("qrcodes");
            if(!existe) {
                ststable.execute(sqltable);
            }
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
