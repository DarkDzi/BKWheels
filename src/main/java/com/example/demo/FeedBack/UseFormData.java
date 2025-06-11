package com.example.demo.FeedBack;

import com.example.demo.Modelos.FormData;
import com.example.demo.Modelos.QRCodeData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UseFormData {
    private String Url = "jdbc:h2:file:./data";
    private String Usuario = "admin";
    private String Senha = "123";



    public List<FormData> GetFeedsData(){
        List<FormData> feedsData = new ArrayList<FormData>();
        try (
                Connection conn = DriverManager.getConnection(Url, Usuario, Senha)) {
            String sql = "SELECt * FROM formdata";
            Statement stamt = conn.createStatement();
            ResultSet rs = stamt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("id");
                int bikeid = rs.getInt("bikeid");
                int nota = rs.getInt("nota");
                String comentario = rs.getString("comentario");
                boolean reparo = rs.getBoolean("reparo");
                Timestamp data = rs.getTimestamp("data");
                String nome = rs.getString("nome");
                FormData formData = new FormData(bikeid, nota, id, comentario, reparo, data, nome);
                feedsData.add(formData);
            }


            rs.close();
            stamt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }






        return feedsData;
    }

}
