package dong.duan.ecommerce.fragment.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dong.duan.ecommerce.databinding.FragmentNotificationBinding
import dong.duan.ecommerce.databinding.ItemNotificationViewBinding
import dong.duan.ecommerce.fragment.other.ProductFragment
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Notification
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.utility.Constant

class NotificationFragment :BaseFragment<FragmentNotificationBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentNotificationBinding.inflate(layoutInflater,container,false)

    var customnotiAdapter: GenericAdapter<Notification,ItemNotificationViewBinding>? = null

    override fun initView() {
        getAllNoti { it->
            customnotiAdapter= GenericAdapter(it,ItemNotificationViewBinding::inflate){
                itembinding, notification, i ->
                itembinding.txtNoti.text= notification.varue
                itembinding.txtTime.text= notification.time
                itembinding.txtDetail.setOnClickListener {
                    productById(notification.productID){
                        replaceFullViewFragment(ProductFragment(it, false), true)
                    }
                }
            }

            binding.rcvNotification.adapter= customnotiAdapter
        }
    }

    fun productById(prID: String, calback: (Product) -> Unit) {
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
                        val manuID = document[Constant.PRODUCT_MANU_ID].toString()
                        val manuName = document[Constant.PRODUCT_MANU_NAME].toString()
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
                            describle, style,evaluator
                        )
                        if (product.id == prID) {
                            calback(product)
                            break
                        }
                    }
                } else {
                    show_toast("Error: ${task.exception?.message}")
                }
            }
    }



    fun getAllNoti(calback:(MutableList<Notification>)->Unit){
        var listNoti= mutableListOf<Notification>()
        database.getReference(Constant.KEY_NOTIFICATION)
            .child(sharedPreferences.getString(Constant.USER_ID).toString())
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        snapshot.children.forEach { item->
                            val noti = item.value as? HashMap<*, *>
                            val notification = Notification(item.key.toString(),noti!!.get(Constant.NOTI_VALUE).toString(),noti!!.get(Constant.NOTI_TIME).toString(),noti!!.get(Constant.NOTI_PR_ID).toString())
                            listNoti.add(notification)
                        }
                        calback(listNoti)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    show_toast(error.message.toString())
                }
            })
    }


    override fun handlerBackPressed() {
        super.handlerBackPressed()
        this.closeFragment(this)
    }

}