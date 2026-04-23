package id.my.rafliaditya.login.data

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import id.my.rafliaditya.login.BuildConfig
import id.my.rafliaditya.login.api.AuthRequest
import id.my.rafliaditya.login.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class UserRepository(private val context: Context) {
    private val apiService = RetrofitClient.instance

    suspend fun registerUser(user: User): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = apiService.register(AuthRequest(user.username, user.password))
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun login(username: String, password: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = apiService.login(AuthRequest(username, password))
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getProducts(): List<Product> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                response.body()?.map {
                    val imageUrl = it.image.let { img ->
                        if (img.startsWith("http")) img 
                        else "${BuildConfig.BASE_URL}storage/${img.removePrefix("/")}"
                    }
                    Product(
                        id = it.id,
                        name = it.name,
                        image = imageUrl,
                        category = it.category,
                        stock = it.stock,
                        price = it.price
                    )
                } ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addProduct(product: Product): Boolean = withContext(Dispatchers.IO) {
        try {
            val name = product.name.toRequestBody(MultipartBody.FORM)
            val category = product.category.toRequestBody(MultipartBody.FORM)
            val stock = product.stock.toString().toRequestBody(MultipartBody.FORM)
            val price = product.price.toString().toRequestBody(MultipartBody.FORM)
            
            val imagePart = prepareFilePart("image", product.image) ?: return@withContext false
            
            val response = apiService.addProduct(name, category, stock, price, imagePart)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateProduct(product: Product): Boolean = withContext(Dispatchers.IO) {
        try {
            val id = product.id.toString().toRequestBody(MultipartBody.FORM)
            val name = product.name.toRequestBody(MultipartBody.FORM)
            val category = product.category.toRequestBody(MultipartBody.FORM)
            val stock = product.stock.toString().toRequestBody(MultipartBody.FORM)
            val price = product.price.toString().toRequestBody(MultipartBody.FORM)
            
            val imagePart = if (product.image.isNotEmpty() && product.image.startsWith("content://")) {
                prepareFilePart("image", product.image)
            } else {
                null
            }
            
            val response = apiService.updateProduct(id, name, category, stock, price, imagePart)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteProduct(id: Long): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteProduct(id)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    private fun prepareFilePart(partName: String, fileUriString: String): MultipartBody.Part? {
        return try {
            val uri = Uri.parse(fileUriString)
            val contentResolver = context.contentResolver
            
            // Mencoba mendapatkan nama file asli
            var fileName = "image.jpg"
            contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1 && cursor.moveToFirst()) {
                    fileName = cursor.getString(nameIndex)
                }
            }

            val inputStream = contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes() ?: return null
            val requestFile = bytes.toRequestBody("image/*".toMediaTypeOrNull(), 0, bytes.size)
            MultipartBody.Part.createFormData(partName, fileName, requestFile)
        } catch (e: Exception) {
            null
        }
    }
}
