package com.example.petapp1.models;

public class taker_data {
        private String taker_ownername;
        private String taker_pettype;
        private String taker_breed;
        private String taker_bloodgroup;

        taker_data() {}

        public String getTaker_ownername() { return taker_ownername; }

        public void setTaker_ownername(String taker_ownername) { this.taker_ownername = taker_ownername; }

        public String getTaker_pettype() { return taker_pettype; }

        public void setTaker_pettype(String taker_pettype) { this.taker_pettype = taker_pettype; }

        public String getTaker_breed() { return taker_breed; }

        public void setTaker_breed(String taker_breed) { this.taker_breed = taker_breed; }

        public String getTaker_bloodgroup() { return taker_bloodgroup; }

        public void setTaker_bloodgroup(String taker_bloodgroup) { this.taker_bloodgroup = taker_bloodgroup; }

        public taker_data(String taker_ownername , String taker_pettype, String taker_breed, String taker_bloodgroup)
        {
        this.taker_ownername = taker_ownername;
        this.taker_pettype= taker_pettype;
        this.taker_breed = taker_breed;
        this.taker_bloodgroup= taker_bloodgroup;
        }
}
