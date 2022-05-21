package com.example.myapplication;

import javax.xml.transform.sax.SAXResult;

public class Opinion {

    private String calificacion;
    private String usuario;
    private String comentario;

    Opinion(String usuario, String calificacion,  String comentario){
        this.usuario = usuario;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }


    public String getCalificacion(){
        return calificacion;
    }

    public String getUsuario(){
        return  usuario;
    }

    public String getComentario(){
        return comentario;
    }
}
