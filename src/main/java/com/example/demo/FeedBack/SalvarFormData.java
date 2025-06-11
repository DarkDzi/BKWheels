package com.example.demo.FeedBack;

import com.example.demo.CriarTabela;
import com.example.demo.Modelos.QRCodeData;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.Date;


public class SalvarFormData {
    private String Url = "jdbc:h2:file:./data";
    private String Usuario = "admin";
    private String Senha = "123";


    public void Save(int nota, String comentario, Boolean reparo, int bikeid, String nome) {
        CriarTabela criarTabela = new CriarTabela();
        criarTabela.criarTabelaForm();
        Date data = new Date();
        java.sql.Timestamp datasql = new java.sql.Timestamp(data.getTime());
        try(Connection conn = DriverManager.getConnection(Url, Usuario, Senha)){
            String sql = "INSERT INTO formdata (nota,bikeid, comentario, reparo, data, nome) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement sts = conn.prepareStatement(sql);


            sts.setInt(1, nota);
            sts.setInt(2, bikeid);
            sts.setString(3, comentario);
            sts.setBoolean(4, reparo);
            sts.setTimestamp(5, datasql);
            sts.setString(6, nome);
            sts.executeUpdate();

            sts.close();
            conn.close();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}







