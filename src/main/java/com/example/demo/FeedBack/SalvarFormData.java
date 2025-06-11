package com.example.demo.FeedBack;

import com.example.demo.Modelos.QRCodeData;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.Date;


public class SalvarFormData {
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
    public void Save(int nota, String comentario, Boolean reparo) {

        Date data = new Date();
        java.sql.Timestamp datasql = new java.sql.Timestamp(data.getTime());
        try(Connection conn = DriverManager.getConnection(Url, Usuario, Senha)){

            String sqltable = "CREATE TABLE formdata (" +
                    "    id IDENTITY PRIMARY KEY," +
                    "    nota INT NOT NULL CHECK (nota BETWEEN 1 AND 5)," +
                    "    comentario VARCHAR(1000)," +
                    "    reparo BOOLEAN,";

            Statement ststable = conn.createStatement();
            boolean existe = tabelaExiste("formdata");
            if(!existe) {
                ststable.execute(sqltable);
            }
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







