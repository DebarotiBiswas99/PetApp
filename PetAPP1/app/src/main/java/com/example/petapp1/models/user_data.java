package com.example.petapp1.models;

public class user_data {

    private  String user_name;
    private String user_phone;
    private String user_id;
    private String user_email;

    user_data(){}

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public  user_data(String user_name , String user_phone, String user_id, String user_email)
    {
        this.user_name = user_name;
        this.user_email= user_email;
        this.user_phone = user_phone;
        this.user_id= user_id;
    }

}

