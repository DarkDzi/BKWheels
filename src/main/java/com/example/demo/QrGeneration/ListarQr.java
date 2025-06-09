package com.example.demo.QrGeneration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;

public class ListarQr {

    private String Url = "jdbc:h2:mem:banco";
    private String Usuario = "admin";
    private String Senha = "123";

    public void Listar() {
        try (
                Connection conn = DriverManager.getConnection(Url, Usuario, Senha)) {
            String sql = "SELECt * FROM qrcodes";
            Statement stamt = conn.createStatement();
            ResultSet rs = stamt.executeQuery(sql);

            while(rs.next()){
            int id = rs.getInt("id");
            String nome = rs.getString("nome_arquivo");
            String url = rs.getString("QRUrl");
            byte[] qrbytes = rs.getBytes("QR");
            BufferedImage QR = ImageIO.read(new ByteArrayInputStream(qrbytes));
            }


            rs.close();
            stamt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
