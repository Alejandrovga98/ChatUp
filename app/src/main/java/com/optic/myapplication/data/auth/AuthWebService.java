package com.optic.myapplication.data.auth;

import com.optic.myapplication.models.auth.UserLoginRequest;
import com.optic.myapplication.models.auth.UserLoginResponse;
import com.optic.myapplication.models.auth.UserRegisterRequest;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthWebService {

    @POST("auth/signup")
    Single<Response<String>> registerUser(@Body UserRegisterRequest request);

    @POST("auth/login")
    Single<Response<UserLoginResponse>> loginUser(@Body UserLoginRequest request);

    @POST("auth/forgot-password")
    void forgotPassword(@Query("email") String email);

    @POST("auth/change-password")
    Response<String> changePassword(
            @Query("token") String token,
            @Query("password") String password
    );
}
