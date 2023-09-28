package com.signum.smartcontect.Model;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message="Name is require")
    @Size(min = 3,max = 20,message = "must be 3-20 characters")
    private String name;
    @Column(unique = true)
    @NotBlank(message="Email is require")
    @Email()
    private String email;
    private String password;
    private String role;
    private boolean enable;
    @AssertTrue()
    private boolean terms;
    private String imageUri;
    @Column(length  = 1000)
    private String about;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user" ,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Contact> contacts= new ArrayList<>();


    public User() {
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


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }


    public boolean isEnable() {
        return enable;
    }
    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public boolean isTerms() {
        return terms;
    }
    public void setTerms(boolean terms) {
        this.terms = terms;
    }


    public String getImageUri() {
        return imageUri;
    }
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }


    public List<Contact> getContacts() {
        return contacts;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }


    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
                + ", enable=" + enable + ", imageUri=" + imageUri + ", about=" + about + ", contacts=" + contacts + "]";
    } 


    
}
