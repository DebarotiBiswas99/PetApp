package com.example.petapp1.models;

public class addpetfood_data {
    private String add_uid;
    private String add_pettype;
    private String add_foodname;
    private String add_foodquantiy;
    private String add_foodtype;
    private String add_suitablefor;
    private String add_id;

    addpetfood_data(){}

    public String getAdd_uid() { return add_uid; }

    public void setAdd_uid(String add_uid) { this.add_uid = add_uid; }

    public String getAdd_pettype() { return add_pettype; }

    public void setAdd_pettype(String add_pettype) { this.add_pettype = add_pettype; }

    public String getAdd_foodname() { return add_foodname; }

    public void setAdd_foodname(String add_foodname) { this.add_foodname = add_foodname; }

    public String getAdd_foodquantiy() { return add_foodquantiy; }

    public void setAdd_foodquantiy(String add_foodquantiy) { this.add_foodquantiy = add_foodquantiy; }

    public String getAdd_foodtype() {
        return add_foodtype;
    }

    public void setAdd_foodtype(String add_foodtype) {
        this.add_foodtype = add_foodtype;
    }

    public String getAdd_suitablefor() {
        return add_suitablefor;
    }

    public void setAdd_suitablefor(String add_suitablefor) {
        this.add_suitablefor = add_suitablefor;
    }

    public String getAdd_id() { return add_id; }

    public void setAdd_id(String add_id) { this.add_id = add_id; }


    public addpetfood_data(String add_uid, String add_pettype, String add_foodname, String add_foodquantity, String add_foodtype, String add_suitablefor, String add_id)
    {
        this.add_uid = add_uid;
        this.add_pettype = add_pettype;
        this.add_foodname= add_foodname;
        this.add_foodquantiy = add_foodquantity;
        this.add_foodtype = add_foodtype;
        this.add_suitablefor = add_suitablefor;
        this.add_id = add_id;
    }
}
