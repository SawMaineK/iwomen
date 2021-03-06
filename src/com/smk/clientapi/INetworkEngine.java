package com.smk.clientapi;

import java.util.List;
import com.google.gson.JsonObject;
import com.smk.model.AccessToken;
import com.smk.model.Author;
import com.smk.model.Category;
import com.smk.model.Comment;
import com.smk.model.Post;
import com.smk.model.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.MultipartTypedOutput;

public interface INetworkEngine {
	
	@FormUrlEncoded
	@POST("/oauth/access_token")
	void getAccessToken(
			@Field("grant_type") String grant_type,
			@Field("client_id") String client_id,
			@Field("client_secret") String client_secret,
			Callback<AccessToken> callback);
	
	@FormUrlEncoded
	@POST("/api-v1/auth/register")
	void postUser(
			@Field("access_token") String access_token,
			@Field("username") String username,
			@Field("email") String email,
			@Field("password") String password,
			@Field("phone") String phone,
			@Field("first_name") String first_name,
			@Field("last_name") String last_name,
			@Field("address") String address,
			@Field("photo") String photo,
			@Field("role") String role,
			Callback<User> callback);
	
	@FormUrlEncoded
	@POST("/api-v1/auth/edit/{id}")
	void editUser(
			@Field("access_token") String access_token,
			@Path("id") Integer id,
			@Field("username") String username,
			@Field("email") String email,
			@Field("password") String password,
			@Field("phone") String phone,
			@Field("first_name") String first_name,
			@Field("last_name") String last_name,
			@Field("address") String address,
			@Field("photo") String photo,
			@Field("role") String role,
			Callback<User> callback);
	
	@FormUrlEncoded
	@POST("/api-v1/auth/login")
	void postLogin(
			@Field("access_token") String access_token,
			@Field("username") String username,
			@Field("email") String email,
			@Field("password") String password,
			Callback<User> callback);
	
	@POST("/api-v1/auth/photo")
	void uploadUserPhoto(
			@Body MultipartTypedOutput attachments,
			Callback<String> callback);
	
	
	@FormUrlEncoded
	@POST("/api-v1/post")
	void createPost(
			@Field("access_token") String access_token,
			@Field("title") String title,
			@Field("title_mm") String title_mm,
			@Field("category_id") Integer category_id,
			@Field("author_id") Integer author_id,
			@Field("user_id") Integer user_id,
			@Field("contents") String contents,
			@Field("photos") String photos,
			Callback<Post> callback);
	
	@GET("/api-v1/post")
	void getPost(
			@Query("access_token") String access_token,
			@Query("offset") Integer offset,
			@Query("limit") Integer limit,
			Callback<List<Post>> callback);
	
	
	@GET("/api-v1/category")
	void getCategory(
			@Query("access_token") String access_token,
			Callback<List<Category>> callback);
	
	@FormUrlEncoded
	@POST("/api-v1/category")
	void postCategory(
			@Field("access_token") String access_token,
			@Field("name") String name,
			Callback<Category> callback);
	
	@GET("/api-v1/author")
	void getAuthor(
			@Query("access_token") String access_token,
			Callback<List<Author>> callback);
	
	@FormUrlEncoded
	@POST("/api-v1/author")
	void postAuthor(
			@Field("access_token") String access_token,
			@Field("name") String name,
			@Field("about") String about,
			@Field("about_mm") String about_mm,
			@Field("image") String image,
			Callback<Author> callback);
	
	@POST("/api-v1/author/photo")
	void uploadAuthorPhoto(
			@Body MultipartTypedOutput attachments,
			Callback<String> callback);
	
	@FormUrlEncoded
	@POST("/api-v1/comment")
	void postComment(
			@Field("access_token") String access_token,
			@Field("user_id") String user_id,
			@Field("post_id") String post_id,
			@Field("comment") String comment,
			Callback<Comment> callback);
	
	@GET("/api-v1/comment/{id}")
	void getComment(
			@Query("access_token") String access_token,
			@Path("id") Integer id,
			Callback<List<Comment>> callback);
	
	@FormUrlEncoded
	@POST("/api-v1/like")
	void postLike(
			@Field("access_token") String access_token,
			@Field("user_id") String user_id,
			@Field("post_id") String post_id,
			Callback<JsonObject> callback);
	
	@GET("/api-v1/like/{id}")
	void getLike(
			@Query("access_token") String access_token,
			@Path("id") Integer id,
			Callback<Integer> callback);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
