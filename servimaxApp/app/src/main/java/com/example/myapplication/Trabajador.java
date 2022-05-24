package com.example.myapplication;

/*
* Modelo para las vistas de trabajador
* */

import android.net.Uri;

public class Trabajador {

    private String nombre;
    private String apellido;
    private String oficio;
    private String precioMinimo;
    private String precioMaximo;
    private String noTelefono;
    private String email;
    private Uri fotoResource;
    private double calificacion;

    //Constructor para la vista en la lista de trabajadores;
    Trabajador(String nombre, String apellido, double calificacion, Uri fotoResource){
        this.nombre = nombre;
        this.apellido = apellido;
        this.calificacion = calificacion;
        this.fotoResource = fotoResource;
    }

    Trabajador(String nombre, String apellido, double calificacion, Uri fotoResource, String email){
        this.nombre = nombre;
        this.apellido = apellido;
        this.calificacion = calificacion;
        this.fotoResource = fotoResource;
        this.email = email;
    }


    Trabajador(String nombre, String apellido,  String noTelefono, String oficio, Double calificacion,String precioMinimo, String precioMaximo, Uri fotoResource,  String email){
        this.nombre = nombre;
        this.apellido = apellido;
        this.calificacion = calificacion;
        this.noTelefono = noTelefono;
        this.oficio = oficio;
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.fotoResource = fotoResource;
        this.email = email;
        this.calificacion = calificacion;
    }

    // Getters
    public String getNombre(){
        return nombre;
    }

    public double getCalificacion(){
        return  calificacion;
    }

    public Uri getFotoResource(){
        return fotoResource;
    }

    public void setFotoResource(Uri uri){
        this.fotoResource = uri;
    }

    public String getEmail(){
        return email;
    }

    public String getApellido(){
        return apellido;
    }

    public String getMin(){
        return precioMinimo;
    }

    public String getMax(){
        return precioMaximo;
    }

    public String getNoTel(){
        return noTelefono;
    }

    public String getOficio(){
        return oficio;
    }
}
