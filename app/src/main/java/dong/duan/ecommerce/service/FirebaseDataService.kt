package dong.duan.ecommerce.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.firestore.FirebaseFirestore
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.utility.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class FirebaseDataService : Service() {
    private val firestore = FirebaseFirestore.getInstance()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        fetchDataFromFirebase()
        getAllProduct {

        }
        return START_NOT_STICKY
    }

    private fun fetchDataFromFirebase() {
        // Fetch data from Firebase Firestore
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val data = firestore.collection("your_collection_name")
                    .document("your_document_id")
                    .get()
                    .await()
            } catch (e: Exception) {
                // Handle any errors that may occur during data fetching
                e.printStackTrace()
                // You can choose to stop the service or retry, depending on your requirements
                stopSelf()
            }
        }


    }


    fun getAllProduct(calback: (MutableList<Product>) -> Unit) {
        val productList = mutableListOf<Product>()

        val shopID = sharedPreferences.getString(Constant.SHOP_ID)
        firestore.collection(Constant.KEY_PRODUCT)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val id = document.id
                        val name = document[Constant.PRODUCT_NAME].toString()
                        val price = document[Constant.PRODUCT_PRICE].toString().toFloat()
                        val saleOff = document[Constant.PRODUCT_SALEOFF].toString().toFloat()
                        val idShop = document[Constant.PRODUCT_SHOP_ID].toString()
                        val count = document[Constant.PRODUCT_COUNT].toString().toInt()
                        val imageUrl = document[Constant.PRODUCT_IMG] as? MutableList<Any> ?: null
                        val idUser = document[Constant.PRODUCT_USER_ID].toString()
                        val star = document[Constant.PRODUCT_STAR].toString().toFloat()
                        val timeUp = document[Constant.PRODUCT_TIME_UP].toString()
                        val describle = document[Constant.PRODUCT_DESCRIBLE].toString()
                        val manuID = document[Constant.PRODUCT_MANU_NAME].toString()
                        val manuName = document[Constant.PRODUCT_TIME_UP].toString()
                        val isSale = document[Constant.PRODUCT_IS_SALE].toString().toBoolean()
                        val productSizeList = mutableListOf<ProductSize>()
                        val productSizeData =
                            document[Constant.PRODUCT_SIZE] as? List<HashMap<String, Any>>
                                ?: emptyList()
                        val style = document[Constant.PRODUCT_STYLE].toString()
                        val evaluator = document[Constant.PRODUCT_EVALUATION].toString().toInt()
                        for (sizeMap in productSizeData) {
                            val size = sizeMap["size"]?.toString() ?: ""
                            val productSize = ProductSize(size)
                            productSizeList.add(productSize)
                        }

                        val product = Product(
                            id,
                            name,
                            price,
                            saleOff,
                            idShop,
                            count,
                            imageUrl,
                            idUser,
                            star,
                            timeUp,
                            isSale,
                            productSizeList,
                            manuID,
                            manuName,
                            describle,
                            style,evaluator
                        )
                        productList.add(product)

                    }
                    calback(productList)
                } else {
                    show_toast("Error: ${task.exception?.message}")
                }
            }

    }

}