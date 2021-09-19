package com.example.fleix.API_DATA;

import com.example.fleix.Class.OrderDetails;
import com.example.fleix.Class.OrderPost;
import com.example.fleix.Class.Portfolio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JSONPlaceHolder {

    @POST("customer")
    Call<Portfolio> postPortfolio(@Body Portfolio portfolio);

    @GET("customer")
    Call<List<Portfolio>> getPortfolioData();

    @POST("order")
    Call<OrderPost> postOrder(@Body OrderPost orderPost);

    @GET("order")
    Call<List<OrderDetails>> getOrderData();

//    @PATCH("order/{id}")
//    Call<OrderDetails> update(@Path("id") int id, @Body OrderPost orderPost);

    @PATCH("order/{id}")
    Call<OrderDetails> update(@Path("id") int id,@Body OrderPost orderPost);

    @DELETE("order/{id}")
    Call<Void> delete(@Path("id") int id);
}
