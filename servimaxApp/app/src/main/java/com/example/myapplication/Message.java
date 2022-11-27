package com.example.myapplication;

public class Message {

    private String sender;
    private String message;
    private String date;

    Message(String sender, String message, String date){
        this.sender = sender;
        this.message = message;
        this.date = date;
    }

    public String getSender(){
        return sender;
    }
     public String getMessage(){
        return message;
     }

     public String getDate(){
        return date;
     }
}
