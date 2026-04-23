package id.my.rafliaditya.login.data

data class User(
    val id: Long = 0,
    val username: String,
    val password: String,
    val email: String = ""
)

data class Product(
    val id: Long = 0,
    val name: String,
    val image: String,
    val category: String,
    val stock: Int,
    val price: Double
)
