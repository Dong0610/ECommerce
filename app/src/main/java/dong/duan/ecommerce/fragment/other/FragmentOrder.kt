package dong.duan.ecommerce.fragment.other

import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.adapter.user.ListOrderAdapter
import dong.duan.ecommerce.databinding.FragmentYourOrderBinding
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Order
import dong.duan.ecommerce.utility.Constant
import java.util.Date

class FragmentOrder : BaseFragment<FragmentYourOrderBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentYourOrderBinding.inflate(layoutInflater)


    var order_adapter: ListOrderAdapter?= null
    override fun initView() {
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }

        order_adapter= ListOrderAdapter(requireContext()){
            replaceFullViewFragment(FramentOrderDetail(it), true)
        }
        binding.rcvListOrder.adapter=order_adapter

        getOrder { item->
            order_adapter!!.setItems(item)
        }
    }
    fun getOrder(calback:(MutableList<Order>)->Unit){
        firestore.collection(Constant.KEY_ORDER)
            .whereEqualTo(Constant.ORDER_USER_ID, sharedPreferences.getString(Constant.USER_ID, "").toString())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    val listData = mutableListOf<Order>() // Initialize the list
                    task.result.forEach { item ->
                        val order = Order(item.id).apply {
                            shopId = item.getString(Constant.ORDER_SHOP_ID) ?: ""
                            userId = sharedPreferences.getString(Constant.USER_ID, "").toString()
                            productId = item.getString(Constant.ORDER_PR_ID) ?: ""
                            productName = item.getString(Constant.ORDER_PR_NAME) ?: ""
                            productCount = item.getLong(Constant.ORDER_PR_COUNT)?.toInt() ?: 0
                            productPrice = item.getDouble(Constant.ORDER_PR_PRICE)?.toFloat() ?: 0.0f
                            productImg = item.getString(Constant.ORDRT_PR_IMG) ?: ""
                            orderStatus = item.getString(Constant.ORDER_STATUS) ?:""
                            orderTime = (item.getDate(Constant.ORDER_TIME)!! as? Date)!!
                            orderStatusTime =(item.getDate(Constant.ORDER_STATUS_TIME)!! as? Date)!!
                            remindName = item.getString(Constant.ODR_ADR_REMIND_NAME).toString() ?:""
                            receiverName = item.getString(Constant.ODR_ADR_US_NAME).toString() ?:""
                            location = item.getString(Constant.ODR_ADR_ADDRESS).toString() ?:""
                            phoneNumber = item.getString(Constant.ODR_ADR_F_PHONE).toString() ?:""
                            phoneNumber2 = item.getString(Constant.ODR_ADR_S_PHONE).toString() ?:""
                            cancelMess= item.getString(Constant.ORDER_CANCEL_VALUE).toString()
                        }
                        if(order.userId.equals(sharedPreferences.getString(Constant.USER_ID, "").toString())){
                            listData.add(order)
                        }

                    }
                    calback(listData)
                }
            }
            .addOnFailureListener { exception ->
                show_toast(exception.message.toString())
            }

    }
}