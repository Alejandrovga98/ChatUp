package com.optic.myapplication.models.configuration;

public class UpdateUserRequest {
    public UpdateUserRequest(String username, String status, String fotoPerfil) {
        this.username = username;
        this.status = status;
        this.fotoPerfil = fotoPerfil;
    }

    String username;
    String status;
    String fotoPerfil;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
