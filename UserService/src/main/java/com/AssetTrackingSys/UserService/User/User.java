package com.AssetTrackingSys.UserService.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @JsonIgnore
    private Integer role_id;

    @Transient
    private String role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public static enum Roles{
        ADMIN(1),
        ASSET_MANAGER(2),
        STAFF(3);

        final Integer id;

        Roles(Integer id){ this.id = id; }

        public Integer getId(){ return this.id; }

        public static String getRoleFromId(Integer id) {
            for (Roles role : Roles.values()) {
                if (role.getId().equals(id)) {
                    return role.toString();
                }
            }
            return "Unknown Role";
        }

        public static Integer getIdFromRole(String name){
            name = name.toUpperCase();

            for (Roles role : Roles.values()) {
                if (role.toString().equals(name)) {
                    return role.getId();
                }
            }
            return null;
        }

    };


//    public static void main(String[] main){
//        System.out.println(User.Roles.getIdFromRole("asset_manager"));
//    }


    public User(Long id, String name, String password, Integer role_id){
        setId(id);
        setName(name);
        setPassword(password);
        setRole_id(role_id);
        setRole(User.Roles.getRoleFromId(role_id));
    }

    public User(Long id, String name, Integer role_id){
        setId(id);
        setName(name);
        setRole_id(role_id);
        setRole(User.Roles.getRoleFromId(role_id));
    }

    public User(String name, Integer role_id){
        setName(name);
        setRole_id(role_id);
        setRole(User.Roles.getRoleFromId(role_id));
    }

    public User(){};


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Integer getRole_id() {
        return role_id;
    }

    public String getRole() {
        if(this.role_id != null && this.role == null) this.role = User.Roles.getRoleFromId(this.role_id);


        return role;
    }


    public String getPassword() {
        return password;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role.toUpperCase();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role_id=" + role_id +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
