package id.my.rafliaditya.login.api

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val status: String,
    val message: String,
    val user: UserData?
)

data class UserData(
    val id: Int,
    val username: String
)

data class ProductItem(
    val id: Long,
    val name: String,
    val category: String,
    val stock: Int,
    val price: Double,
    val image: String
)

data class CommonResponse(
    val status: String,
    val message: String
)
