package dong.duan.ecommerce.fragment.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dong.duan.ecommerce.databinding.FragmentNotificationBinding
import dong.duan.ecommerce.databinding.ItemNotificationViewBinding
import dong.duan.ecommerce.fragment.other.FramentOrderDetail
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Notification
import dong.duan.ecommerce.model.Order
import dong.duan.ecommerce.utility.Constant
import java.util.Date

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNotificationBinding.inflate(layoutInflater, container, false)

    var customnotiAdapter: GenericAdapter<Notification, ItemNotificationViewBinding>? = null

    override fun initView() {
        getAllNoti { it ->
            customnotiAdapter = GenericAdapter(
                it,
                ItemNotificationViewBinding::inflate
            ) { itembinding, notification, i ->
                itembinding.txtNoti.text = notification.value
                itembinding.txtTime.text = notification.time
                itembinding.txtDetail.setOnClickListener {
                    getOrderById(notification.orderID) {
                        replaceFullViewFragment(FramentOrderDetail(it), true)
                    }
                }
            }

            binding.rcvNotification.adapter = customnotiAdapter
        }
    }


    fun getOrderById(orderID: String, callback: (Order) -> Unit) {
        firestore.collection(Constant.KEY_ORDER)
            .document(orderID)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.exists()) {

                        val order = Order(orderID).apply {
                            shopId = document.getString(Constant.ORDER_SHOP_ID) ?: ""
                            userId = sharedPreferences.getString(Constant.USER_ID, "") ?: ""
                            productId = document.getString(Constant.ORDER_PR_ID) ?: ""
                            productName = document.getString(Constant.ORDER_PR_NAME) ?: ""
                            productCount = document.getLong(Constant.ORDER_PR_COUNT)?.toInt() ?: 0
                            productPrice = document.getDouble(Constant.ORDER_PR_PRICE)?.toFloat() ?: 0.0f
                            productImg = document.getString(Constant.ORDRT_PR_IMG) ?: ""
                            orderStatus = document.getString(Constant.ORDER_STATUS) ?: ""
                            orderTime = document.getDate(Constant.ORDER_TIME) as Date
                            orderStatusTime = document.getDate(Constant.ORDER_STATUS_TIME) as Date
                            receiverName = document.getString(Constant.ODR_ADR_US_NAME) ?: ""
                            location = document.getString(Constant.ODR_ADR_ADDRESS) ?: ""
                            phoneNumber = document.getString(Constant.ODR_ADR_F_PHONE) ?: ""
                            phoneNumber2 = document.getString(Constant.ODR_ADR_S_PHONE) ?: ""
                            cancelMess = document.getString(Constant.ORDER_CANCEL_VALUE) ?: ""
                            orderPrSize = document.get(Constant.ORDER_PR_SIZE)?.toString()!!.toIntOrNull() ?: 0
                        }
                        callback(order)
                    } else {
                        show_toast("Order with ID $orderID not found.")
                    }
                } else {
                    show_toast("Failed to fetch order: ${task.exception?.message}")
                }
            }
            .addOnFailureListener { exception ->
                show_toast("Failed to fetch order: ${exception.message}")
            }
    }


    fun getAllNoti(calback: (MutableList<Notification>) -> Unit) {
        var listNoti = mutableListOf<Notification>()
        database.getReference(Constant.KEY_NOTIFICATION)
            .child(sharedPreferences.getString(Constant.USER_ID).toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        snapshot.children.forEach { item ->
                            val noti = item.value as? HashMap<*, *>
                            val notification = Notification(
                                item.key.toString(),
                                noti!!.get(Constant.NOTI_VALUE).toString(),
                                noti!!.get(Constant.NOTI_TIME).toString(),
                                noti!!.get(Constant.NOTI_PR_ID).toString(),
                                noti!!.get(Constant.NOTI_OD_ID).toString()
                            )
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