package com.slgunz.root.androidarticle.data.remote;

import com.slgunz.root.androidarticle.data.model.Article;
import com.slgunz.root.androidarticle.data.model.Comment;
import com.slgunz.root.androidarticle.data.model.User;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface ArticleService {

    String BASE_URL = "https://slgunz-article.herokuapp.com/api/v1/";

    @GET("articles/?format=json")
    Single<List<Article>> articles();

    @GET("article/{id}/?format=json")
    Single<Article> article(@Path("id") Integer articleId);

    @GET("user/{id}/?format=json")
    Single<User> user(@Path("id") Integer userId);

    @GET("comments/{id}/?format=json")
    Single<List<Comment>> comments(@Path("id") Integer articleId);
}
