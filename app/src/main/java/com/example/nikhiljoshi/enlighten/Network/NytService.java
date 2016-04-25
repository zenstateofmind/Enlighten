package com.example.nikhiljoshi.enlighten.Network;

import com.example.nikhiljoshi.enlighten.Network.GSON.BookList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by nikhiljoshi on 4/25/16.
 */
public interface NytService {

    //Books API
    @GET("/svc/books/v2/lists/{date}/combined-print-and-e-book-nonfiction")
    Call<List<BookList>> getBookLists(@Path("date") String date);

    //Most Popular API


    //Top Stories API
}
