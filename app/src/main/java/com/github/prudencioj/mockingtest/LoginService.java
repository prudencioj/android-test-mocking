package com.github.prudencioj.mockingtest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by joaoprudencio on 11/05/16.
 */
public interface LoginService {
    @GET("login.php")
    Call<ResponseBody> login();
}
