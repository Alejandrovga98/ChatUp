package com.optic.myapplication.data.chat;

import com.optic.myapplication.models.chat.ChatDetailResponse;
import com.optic.myapplication.models.chat.ChatResponse;
import com.optic.myapplication.models.chat.CreateChatRequest;
import com.optic.myapplication.models.chat.CreateChatResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChatWebService {

    @GET("chat/previews")
    public Single<Response<List<ChatResponse>>> getChats(
            @Header("Authorization")
            String userToken,
            @Query("userId")
            String userId);
    @POST("chat/newChat")
    Single<Response<CreateChatResponse>> createChat(
            @Header("Authorization")
            String userToken,
            @Body
            CreateChatRequest request);

    @GET("chat/messages/{chatId}")
    Single<Response<List<ChatDetailResponse>>> getChatMessages(
            @Header("Authorization")
            String userToken,
            @Path("chatId")
            String chatId);

    @GET("chat/findByName")
    public Single<Response<List<ChatResponse>>> getChatsByName(
            @Header("Authorization")
            String userToken,
            @Query("text")
            String text);
}
