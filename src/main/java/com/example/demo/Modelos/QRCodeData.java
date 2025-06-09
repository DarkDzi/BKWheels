package com.example.demo.Modelos;

import java.awt.image.BufferedImage;

public class QRCodeData {
    private int id;
    private String nome_arquivo;
    private String QRUrl;
    private BufferedImage QRImage;

    public QRCodeData(int id, String nome_arquivo, String QRUrl, BufferedImage QRImage) {
        this.id = id;
        this.nome_arquivo = nome_arquivo;
        this.QRUrl = QRUrl;
        this.QRImage = QRImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_arquivo() {
        return nome_arquivo;
    }

    public void setNome_arquivo(String nome_arquivo) {
        this.nome_arquivo = nome_arquivo;
    }

    public String getQRUrl() {
        return QRUrl;
    }

    public void setQRUrl(String QRUrl) {
        this.QRUrl = QRUrl;
    }

    public BufferedImage getQRImage() {
        return QRImage;
    }

    public void setQRImage(BufferedImage QRImage) {
        this.QRImage = QRImage;
    }
}
