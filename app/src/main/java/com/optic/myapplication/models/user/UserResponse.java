package com.optic.myapplication.models.user;

public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String fotoPerfil;
    private String status;

    public UserResponse(String id, String username, String email, String fotoPerfil, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fotoPerfil = fotoPerfil;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
