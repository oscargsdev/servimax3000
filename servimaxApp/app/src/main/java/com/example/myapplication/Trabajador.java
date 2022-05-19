package com.example.myapplication;

/*
* Modelo para las vistas de trabajador
* */

public class Trabajador {

    private String nombre;
    private String apellido;
    private String oficio;
    private double precioMinimo;
    private double precioMaximo;
    private String noTelefono;
    private String email;
    private int fotoResource;
    private double calificacion;

    //Constructor para la vista en la lista de trabajadores;
    Trabajador(String nombre, double calificacion, int fotoResource){
        this.nombre = nombre;
        this.calificacion = calificacion;
        this.fotoResource = fotoResource;
    }

    Trabajador(String nombre, double calificacion, int fotoResource, String email){
        this.nombre = nombre;
        this.calificacion = calificacion;
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
}
