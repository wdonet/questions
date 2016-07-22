package com.nearsoft.questions.controller.form.auth;

import com.nearsoft.questions.domain.auth.User;
import org.springframework.web.multipart.MultipartFile;

public class ProfileForm {

    private String firstName;
    private String lastName;
    private String location;
    private MultipartFile photo;
    private Integer reputation;
    private String photoUri;


    public String getFirstName() {
        return firstName;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public ProfileForm() {
    }

    public ProfileForm(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.location = user.getLocation();
        this.reputation = user.getReputation();
        this.photoUri = user.getPhotoUri();
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }
}
