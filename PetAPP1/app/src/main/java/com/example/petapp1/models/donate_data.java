package com.example.petapp1.models;

public class donate_data {
    private String donate_uid;
    private String donate_pettype;
    private String donate_bloodgroup;
    
    donate_data(){}

    public String getdonate_uid() { return donate_uid; }

    public void setdonate_uid(String donate_uid) { this.donate_uid = donate_uid; }

    public String getdonate_pettype() { return donate_pettype; }

    public void setdonate_pettype(String donate_pettype) { this.donate_pettype = donate_pettype; }

    public String getdonate_bloodgroup() { return donate_bloodgroup; }

    public void setdonate_bloodgroup(String donate_bloodgroup) { this.donate_bloodgroup = donate_bloodgroup; }

    public  donate_data(String donate_uid, String donate_pettype, String donate_bloodgroup)
    {
        this.donate_uid = donate_uid;
        this.donate_pettype= donate_pettype;
        this.donate_bloodgroup= donate_bloodgroup;
    }
}
