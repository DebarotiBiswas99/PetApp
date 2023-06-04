package com.example.petapp1.models;

public class adoption_data {
    private String adoption_ownername;
    private String adoption_pettype;
    private String adoption_breed;

    adoption_data() {}

    public String getAdoption_ownername() { return adoption_ownername; }

    public void setAdoption_ownername(String adoption_ownername) { this.adoption_ownername = adoption_ownername; }

    public String getAdoption_pettype() { return adoption_pettype; }

    public void setAdoption_pettype(String adoption_pettype) { this.adoption_pettype = adoption_pettype; }

    public String getAdoption_breed() { return adoption_breed; }

    public void setAdoption_breed(String adoption_breed) { this.adoption_breed = adoption_breed; }

    public adoption_data(String adoption_ownername , String adoption_pettype, String adoption_breed)
    {
        this.adoption_ownername = adoption_ownername;
        this.adoption_pettype= adoption_pettype;
        this.adoption_breed = adoption_breed;
    }
}
