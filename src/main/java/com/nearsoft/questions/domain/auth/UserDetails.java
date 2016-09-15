package com.nearsoft.questions.domain.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserDetails extends SocialUser {

    private Long id;
    private Role role;
    private SocialMediaService socialSignInProvider;
    private String imageUrl;
    private User user;

    public UserDetails(String username, Collection<? extends GrantedAuthority> authorities) {
        super(username, "", authorities);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public Role getRole() {
        return role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public User getUser() {
        return user;
    }
    
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }


    public SocialMediaService getSocialSignInProvider() {
        return socialSignInProvider;
    }

    public static class Builder {

        private Long id;
        private String username;
        private Role role;
        private User user;
        private SocialMediaService socialSignInProvider;
        private Set<GrantedAuthority> authorities;
        private String imageUrl;

        public Builder() {
            this.authorities = new HashSet<>();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
            this.authorities.add(authority);

            return this;
        }

        public Builder socialSignInProvider(SocialMediaService socialSignInProvider) {
            this.socialSignInProvider = socialSignInProvider;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }


        public UserDetails build() {
            UserDetails details = new UserDetails(username, authorities);

            details.id = id;
            details.role = role;
            details.socialSignInProvider = socialSignInProvider;
            details.imageUrl = imageUrl;
            details.user = user;

            return details;
        }
    }

    @Override
    public String toString() {
        return "UserDetails {" +
                "Name ='" + getFirstName() + ' ' + getLastName() + '\'' +
                ", email='" + getUsername() + '\'' +
                ", role=" + role +
                '}';
    }
}