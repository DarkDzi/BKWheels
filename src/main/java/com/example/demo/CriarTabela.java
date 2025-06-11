package com.example.demo;

import java.sql.*;

public class CriarTabela {
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
    public void criarTabelaQR() {
        try (Connection conn = DriverManager.getConnection(Url, Usuario, Senha)) {
            String sqltable = "CREATE TABLE IF NOT EXISTS qrcodes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome_arquivo VARCHAR(255) NOT NULL, " +
                    "QR BLOB NOT NULL, " +
                    "QRUrl VARCHAR(255))";

            Statement ststable = conn.createStatement();
            boolean existe = tabelaExiste("qrcodes");
            if (!existe) {
                ststable.execute(sqltable);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
   public void criarTabelaForm() {
       try (Connection conn = DriverManager.getConnection(Url, Usuario, Senha)) {

           String sqltable = "CREATE TABLE formdata (" +
                   "id IDENTITY PRIMARY KEY, " +
                   "bikeid INT NOT NULL," +
                   "nota INT NOT NULL CHECK (nota BETWEEN 1 AND 5), " +
                   "comentario VARCHAR(1000), " +
                   "data TIMESTAMP NOT NULL," +
                   "nome VARCHAR(150), " +
                   "reparo BOOLEAN)";


           Statement ststable = conn.createStatement();
           boolean existe = tabelaExiste("formdata");
           if (!existe) {
               ststable.execute(sqltable);
           }
       }catch (Exception e)
       {
       e.printStackTrace();
       }
   }
   }


