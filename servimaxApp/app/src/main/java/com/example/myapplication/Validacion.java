package com.example.myapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacion {

    String correo_val = "^([a-zA-Z]{1}[a-zA-Z0-9_.-]{0,}){4}@{1}(hotmail|gmail|yahoo|HOTMAIL|GMAIL|YAHOO){1}\\.(COM|com{1})$";
    String password_val = "^([0-9a-zA-Z@#$%&-_=.]{1,}){4,}$";
    String nombre_val = "^([a-zA-Z]{3,})$";
    String apellido_val= "^([a-zA-Z\\s]{3,})$";
    String telefono_val = "^([0-9]{10})$";
    String oficio_val = "^([a-zA-Z\\s]{5,})$";
    String mini_val = "^([0-9,]){1,}$";
    String max_val = "^([0-9,]){1,}$";


    Validacion(){

    }

    public boolean valCorreo(String s){
        Pattern pattern = Pattern.compile(correo_val, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        boolean matchFound = matcher.find();
        return matchFound;

    }

    public boolean valTelefono(String s){
        Pattern pattern = Pattern.compile(telefono_val, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        boolean matchFound = matcher.find();
        return matchFound;
    }

    public boolean valPass(String s){
        Pattern pattern = Pattern.compile(password_val, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        boolean matchFound = matcher.find();
        return matchFound;
    }

    public boolean valNombre(String s){
        Pattern pattern = Pattern.compile(nombre_val, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        boolean matchFound = matcher.find();
        return matchFound;

    }

    public boolean valApellido(String s){
        Pattern pattern = Pattern.compile(apellido_val, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        boolean matchFound = matcher.find();
        return matchFound;

    }

    public boolean valOficio(String s){
        Pattern pattern = Pattern.compile(oficio_val, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        boolean matchFound = matcher.find();
        return matchFound;

    }

    public boolean valMax(String s){
        Pattern pattern = Pattern.compile(max_val, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        boolean matchFound = matcher.find();
        return matchFound;
    }

    public boolean valMin(String s){
        Pattern pattern = Pattern.compile(mini_val, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        boolean matchFound = matcher.find();
        return matchFound;

    }
}
