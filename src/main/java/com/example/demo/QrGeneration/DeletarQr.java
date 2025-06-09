package com.example.demo.QrGeneration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeletarQr {
    private String Url = "jdbc:h2:mem:banco";
    private String Usuario = "admin";
    private String Senha = "123";

    public void Delete(Integer DeletID) {
        try (
                Connection conn = DriverManager.getConnection(Url, Usuario, Senha)) {
            String sql = "DELETE FROM qrcodes WHERE id =" + DeletID;
            PreparedStatement sts = conn.prepareStatement(sql);

            sts.executeUpdate();
            sts.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}