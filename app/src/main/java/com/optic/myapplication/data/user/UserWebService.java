package com.optic.myapplication.data.user;

import com.optic.myapplication.models.user.UserResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface UserWebService {
    @GET("user/users")
    Single<Response<List<UserResponse>>> getAllUsers(
            @Header("Authorization")
            String userToken
    );

    @GET("user/search")
    Single<Response<List<UserResponse>>> searchUsers(
            @Header("Authorization")
            String userToken,
            @Query("query")
            String searchText
    );
}
