package com.optic.myapplication.data.configuration;

import com.optic.myapplication.models.configuration.UpdateUserRequest;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConfigurationWebService {

    @DELETE("/delete/{userId}")
    void deleteUser(@Path("userId") String userId);

    @PUT("/update/{userId}")
    void updateUser(
            @Path("userId") String userId,
            @Body UpdateUserRequest request,
            @Query("fotoPerfil") String fotoPerfil // TODO: Cambiar Multipartfile de backend a String
    );
}
