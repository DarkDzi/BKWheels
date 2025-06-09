package com.example.demo.QrGeneration;

import com.example.demo.Modelos.QRCodeData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListarQr {

    private String Url = "jdbc:h2:file:./data";
    private String Usuario = "admin";
    private String Senha = "123";

    public List<QRCodeData> Listar() {
        List<QRCodeData> ListaQR = new ArrayList<QRCodeData>();

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


            QRCodeData QRCode = new QRCodeData(id, nome, url, QR);
            ListaQR.add(QRCode);
            }


            rs.close();
            stamt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
return ListaQR;
    }



}
