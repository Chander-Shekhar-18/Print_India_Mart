package com.zaptas.printindiamart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ISearch {
    @GET("searchProduct")
    Call<Searchproductresponsemodel> getplist(
            @Query("query") String order);
}
