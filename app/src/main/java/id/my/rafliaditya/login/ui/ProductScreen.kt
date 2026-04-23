package id.my.rafliaditya.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.my.rafliaditya.login.data.Product
import id.my.rafliaditya.login.viewmodel.ProductViewModel

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.navigation.NavController
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(viewModel: ProductViewModel, navController: NavController) {
    val products by viewModel.products.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text("Product List") },
                actions = {
                    TextButton(onClick = { 
                        navController.navigate("login") {
                            popUpTo("product_list") { inclusive = true }
                        }
                    }) {
                        Text("Logout", color = MaterialTheme.colorScheme.error)
                    }
                }
            ) 
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedProduct = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onEdit = {
                        selectedProduct = product
                        showDialog = true
                    },
                    onDelete = { viewModel.deleteProduct(product.id) }
                )
            }
        }

        if (showDialog) {
            ProductDialog(
                product = selectedProduct,
                onDismiss = { showDialog = false },
                onConfirm = { name, image, category, stock, price ->
                    if (selectedProduct == null) {
                        viewModel.addProduct(name, image, category, stock, price)
                    } else {
                        viewModel.updateProduct(selectedProduct!!.id, name, image, category, stock, price)
                    }
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun ProductItem(product: Product, onEdit: () -> Unit, onDelete: () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_gallery),
                error = androidx.compose.ui.res.painterResource(android.R.drawable.stat_notify_error)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleLarge)
                Text(text = "Category: ${product.category}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Stock: ${product.stock} | Price: $${product.price}", style = MaterialTheme.typography.bodySmall)
            }
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(text = { Text("Edit") }, onClick = {
                        showMenu = false
                        onEdit()
                    })
                    DropdownMenuItem(text = { Text("Delete") }, onClick = {
                        showMenu = false
                        onDelete()
                    })
                }
            }
        }
    }
}

@Composable
fun ProductDialog(
    product: Product?,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, Int, Double) -> Unit
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var imageUri by remember { mutableStateOf(product?.image ?: "") }
    var category by remember { mutableStateOf(product?.category ?: "") }
    var stock by remember { mutableStateOf(product?.stock?.toString() ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri = it.toString() }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (product == null) "Add Product" else "Edit Product") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri.isNotEmpty()) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Button(onClick = { launcher.launch("image/*") }) {
                            Text("Pick Image")
                        }
                    }
                }
                if (imageUri.isNotEmpty()) {
                    TextButton(onClick = { launcher.launch("image/*") }) {
                        Text("Change Image")
                    }
                }
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                TextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth())
                TextField(
                    value = stock, 
                    onValueChange = { stock = it }, 
                    label = { Text("Stock") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = price, 
                    onValueChange = { price = it }, 
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(name, imageUri, category, stock.toIntOrNull() ?: 0, price.toDoubleOrNull() ?: 0.0)
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
