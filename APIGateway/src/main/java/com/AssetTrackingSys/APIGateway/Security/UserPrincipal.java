package com.AssetTrackingSys.APIGateway.Security;

public class UserPrincipal {
    private final Integer id;
    private final String username;

    // constructor
    public UserPrincipal(Integer userId, String username) {
        this.id = userId;
        this.username = username;
    }

    // getters
    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString(){
        return "{ id: " + id + ", username: " + username + " }";
    }

}
