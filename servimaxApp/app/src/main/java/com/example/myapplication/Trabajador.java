package com.example.myapplication;

/*
* Modelo para las vistas de trabajador
* */

public class Trabajador {

    private String nombre;
    private String apellido;
    private String oficio;
    private String precioMinimo;
    private String precioMaximo;
    private String noTelefono;
    private String email;
    private int fotoResource;
    private double calificacion;

    //Constructor para la vista en la lista de trabajadores;
    Trabajador(String nombre, String apellido, double calificacion, int fotoResource){
        this.nombre = nombre;
        this.apellido = apellido;
        this.calificacion = calificacion;
        this.fotoResource = fotoResource;
    }

    Trabajador(String nombre, String apellido, double calificacion, int fotoResource, String email){
        this.nombre = nombre;
        this.apellido = apellido;
        this.calificacion = calificacion;
        this.fotoResource = fotoResource;
        this.email = email;
    }


    Trabajador(String nombre, String apellido,  String noTelefono, String oficio, String precioMinimo, String precioMaximo, int fotoResource,  String email){
        this.nombre = nombre;
        this.apellido = apellido;
        this.calificacion = calificacion;
        this.noTelefono = noTelefono;
        this.oficio = oficio;
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.fotoResource = fotoResource;
        this.email = email;
    }

    // Getters
    public String getNombre(){
        return nombre;
    }

    public double getCalificacion(){
        return  calificacion;
    }

    public int getFotoResource(){
        return fotoResource;
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
