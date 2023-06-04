package com.example.petapp1.models;

public class adopt_data {
    private String adopt_uid;
    private String adopt_pettype;

    adopt_data() {}

    public String getAdopt_uid() {
        return adopt_uid;
    }

    public void setAdopt_uid(String adopt_uid) {
        this.adopt_uid = adopt_uid;
    }

    public String getAdopt_pettype() {
        return adopt_pettype;
    }

    public void setAdopt_pettype(String adopt_pettype) {
        this.adopt_pettype = adopt_pettype;
    }

    public  adopt_data(String adopt_uid, String adopt_pettype)
    {
        this.adopt_uid = adopt_uid;
        this.adopt_pettype= adopt_pettype;
    }
}