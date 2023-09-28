package com.signum.smartcontect.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="CONTACT")
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String surName;
    @Column(unique = false)
    private String email;
    private String phone;
    private String workTitle;
    private String address;
    private String dpUri;
    @Column(length =500)
    private String about;
    @ManyToOne
    private User user;


   


    public Contact() {
        super();
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }
    public void setSurName(String surName) {
        this.surName = surName;
    }

    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }


     public String getWorkTitle() {
        return workTitle;
    }


    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    
    
    public String getDpUri() {
        return dpUri;
    }
    public void setDpUri(String dpUri) {
        this.dpUri = dpUri;
    }
    
    
    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }

     public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Contact [id=" + id + ", name=" + name + ", nickName=" + surName + ", email=" + email + ", phone="
                + phone + ", workTitle=" + workTitle + ", dpUri=" + dpUri + ", about=" + about +", Address=" + address + "]";
    }


    public void deleteById(Integer id2) {
    }
}
