package id.my.rafliaditya.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import id.my.rafliaditya.login.data.Product
import id.my.rafliaditya.login.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _products.value = repository.getProducts()
        }
    }

    fun addProduct(name: String, image: String, category: String, stock: Int, price: Double) {
        viewModelScope.launch {
            repository.addProduct(Product(name = name, image = image, category = category, stock = stock, price = price))
            loadProducts()
        }
    }

    fun updateProduct(id: Long, name: String, image: String, category: String, stock: Int, price: Double) {
        viewModelScope.launch {
            repository.updateProduct(Product(id = id, name = name, image = image, category = category, stock = stock, price = price))
            loadProducts()
        }
    }

    fun deleteProduct(id: Long) {
        viewModelScope.launch {
            repository.deleteProduct(id)
            loadProducts()
        }
    }
}