package com.example.demo.Modelos;

import java.sql.Timestamp;

public class FormData {
    private int id;
    private int bikeid;
    private int nota;
    private String comentario;
    private boolean reparo;
    private Timestamp data;

    public FormData(int bikeid, int nota, int id, String comentario, boolean reparo, Timestamp data) {
        this.bikeid = bikeid;
        this.nota = nota;
        this.id = id;
        this.comentario = comentario;
        this.reparo = reparo;
        this.data = data;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBikeid() {
        return bikeid;
    }

    public void setBikeid(int bikeid) {
        this.bikeid = bikeid;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isReparo() {
        return reparo;
    }

    public void setReparo(boolean reparo) {
        this.reparo = reparo;
    }
}
