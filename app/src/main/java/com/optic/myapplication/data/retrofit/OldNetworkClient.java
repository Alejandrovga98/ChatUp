package com.optic.myapplication.data.retrofit;

/*
public class NetworkClient {

    private static Retrofit retrofit = null;
    private static Scarlet scarlet = null;

    public NetworkClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://chatup-backend-i6fa.onrender.com/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        scarlet = new Scarlet.Builder()
                .webSocketFactory(client.)
                .addMessageAdapterFactory(new GsonMessageAdapter.Factory(new Gson()))
                .addStreamAdapterFactory()
                .build();
    }

    public static Retrofit getClient(Context context) {
        return retrofit;
    }
}*/
