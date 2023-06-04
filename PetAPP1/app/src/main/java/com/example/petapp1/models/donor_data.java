package com.example.petapp1.models;

public class donor_data {
    private String donor_uid;
    private String donor_pettype;
    private String donor_breed;
    private String donor_age;
    private String donor_weight;
    private String donor_gender;
    private String donor_bloodgroup;
    private String donor_ownername;
    private String donor_phone;
    private String donor_address;
    private String donor_city;
    private String donor_state;
    private String donor_country;
    private String donor_pincode;
    private String donor_id;

    donor_data(){}

    public String getDonor_id() {
        return donor_id;
    }

    public void setDonor_id(String donor_id) {
        this.donor_id = donor_id;
    }

    public String getDonor_uid() {
        return donor_uid;
    }

    public void setDonor_uid(String donor_uid) {
        this.donor_uid = donor_uid;
    }

    public String getDonor_pettype() { return donor_pettype; }

    public void setDonor_pettype(String donor_pettype) { this.donor_pettype = donor_pettype; }

    public String getDonor_breed() { return donor_breed; }

    public void setDonor_breed(String donor_breed) { this.donor_breed = donor_breed; }

    public String getDonor_age() { return donor_age; }

    public void setDonor_age(String donor_age) { this.donor_age = donor_age; }

    public String getDonor_weight() { return donor_weight; }

    public void setDonor_weight(String donor_weight) { this.donor_weight = donor_weight; }

    public String getDonor_gender() { return donor_gender; }

    public void setDonor_gender(String donor_gender) { this.donor_gender = donor_gender; }

    public String getDonor_bloodgroup() { return donor_bloodgroup; }

    public void setDonor_bloodgroup(String donor_bloodgroup) { this.donor_bloodgroup = donor_bloodgroup; }

    public String getDonor_ownername() { return donor_ownername; }

    public void setDonor_ownername(String donor_ownername) { this.donor_ownername = donor_ownername; }

    public String getDonor_phone() { return donor_phone; }

    public void setDonor_phone(String donor_phone) { this.donor_phone = donor_phone; }

    public String getDonor_address() { return donor_address; }

    public void setDonor_address(String donor_address) { this.donor_address = donor_address; }

    public String getDonor_city() { return donor_city; }

    public void setDonor_city(String donor_city) { this.donor_city = donor_city; }

    public String getDonor_state() { return donor_state; }

    public void setDonor_state(String donor_state) { this.donor_state = donor_state; }

    public String getDonor_country() { return donor_country; }

    public void setDonor_country(String donor_country) { this.donor_country = donor_country; }

    public String getDonor_pincode() { return donor_pincode; }

    public void setDonor_pincode(String donor_pincode) { this.donor_pincode = donor_pincode; }

    public donor_data(String donor_uid, String donor_pettype, String donor_breed, String donor_age, String donor_weight, String donor_gender, String donor_bloodgroup, String donor_ownername, String donor_phone, String donor_address, String donor_city, String donor_state, String donor_country, String donor_pincode,String donor_id)
    {
        this.donor_uid = donor_uid;
        this.donor_pettype= donor_pettype;
        this.donor_breed = donor_breed;
        this.donor_age = donor_age;
        this.donor_weight = donor_weight;
        this.donor_gender = donor_gender;
        this.donor_bloodgroup= donor_bloodgroup;
        this.donor_ownername = donor_ownername;
        this.donor_phone = donor_phone;
        this.donor_address = donor_address;
        this.donor_city = donor_city;
        this.donor_state = donor_state;
        this.donor_country = donor_country;
        this.donor_pincode = donor_pincode;
        this.donor_id = donor_id;
    }
}
