package com.nearsoft.questions.controller.form.auth;

import com.nearsoft.questions.domain.auth.User;
import org.springframework.web.multipart.MultipartFile;

public class ProfileForm {

    private String firstName;
    private String lastName;
    private String location;
    private MultipartFile photo;


    public String getFirstName() {
        return firstName;
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

    public ProfileForm() {
    }

    public ProfileForm(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.location = user.getLocation();
    }

}
