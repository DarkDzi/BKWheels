package com.example.demo.FeedBack;

import com.example.demo.Modelos.QRCodeData;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;


public class SalvarFormData {
    private String Url = "jdbc:h2:file:./data";
    private String Usuario = "admin";
    private String Senha = "123";

    public void Save(int nota, String comentario, Boolean reparo) {

        Date data = new Date();
        java.sql.Timestamp datasql = new java.sql.Timestamp(data.getTime());
        try(Connection conn = DriverManager.getConnection(Url, Usuario, Senha)){

            String sql = "INSERT INTO formdata (nota, comentario, reparo, data) VALUES(?, ?, ?, ?)";
            PreparedStatement sts = conn.prepareStatement(sql);


            sts.setInt(1, nota);
            sts.setString(2, comentario);
            sts.setBoolean(3, reparo);
            sts.setTimestamp(4, datasql);
            sts.executeUpdate();

            sts.close();
            conn.close();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}







