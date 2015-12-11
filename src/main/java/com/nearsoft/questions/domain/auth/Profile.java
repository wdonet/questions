package com.nearsoft.questions.domain.auth;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    @Id
    @GeneratedValue(generator = "generator")
    private Long id;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private User user;

    @Column(name = "photo_uri")
    private String photoUri;

    private Integer reputation;

    private String location;

    public Profile() {
    }

    public Profile(User user) {
        this.reputation = 0;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public Integer getReputation() {
        return reputation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
