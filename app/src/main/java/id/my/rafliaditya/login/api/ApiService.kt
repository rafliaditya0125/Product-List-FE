package id.my.rafliaditya.login.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("api/register")
    suspend fun register(@Body request: AuthRequest): Response<AuthResponse>

    @POST("api/login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @GET("api/products")
    suspend fun getProducts(): Response<List<ProductItem>>

    @Multipart
    @POST("api/products")
    suspend fun addProduct(
        @Part("name") name: RequestBody,
        @Part("category") category: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("price") price: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<CommonResponse>

    @Multipart
    @POST("api/products/update")
    suspend fun updateProduct(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("category") category: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("price") price: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<CommonResponse>

    @FormUrlEncoded
    @POST("api/products/delete")
    suspend fun deleteProduct(@Field("id") id: Long): Response<CommonResponse>
}
