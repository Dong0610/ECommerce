package dong.duan.ecommerce.admin

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.adapter.admin.AdminOrderAdapter
import dong.duan.ecommerce.databinding.FragmentOrderAdminBinding
import dong.duan.ecommerce.databinding.ItemTableOrderViewBinding
import dong.duan.ecommerce.databinding.PopupSortOrderBinding
import dong.duan.ecommerce.library.base.BasePopupLocation
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Order
import dong.duan.ecommerce.utility.Constant
import dong.duan.ecommerce.utility.OrderStatus
import dong.duan.ecommerce.utility.colorByStatus
import dong.duan.ecommerce.utility.statusByType


class AdminOrderFragment : BaseFragment<FragmentOrderAdminBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOrderAdminBinding.inflate(layoutInflater)

    var adminOrderAdapter: AdminOrderAdapter? = null
    override fun initView() {
        adminOrderAdapter = AdminOrderAdapter()


        getOrder {
            setDataToView(it)

        }

        onClick()
    }

    private fun onClick() {
        binding.imgSortData.setOnClickListener {
            it.post {
                val location = IntArray(2)
                it.getLocationOnScreen(location)
                val xCoordinate = location[0] + it.width+5
                val yCoordinate = location[1]
                BasePopupLocation(
                    this@AdminOrderFragment.requireContext(),
                    it,
                    PopupSortOrderBinding::inflate,
                       xCoordinate,yCoordinate
                    ) { popupView,po ->
                    popupView.all.setOnClickListener {
                        binding.txtValueSr.text= "Tất cả"
                        binding.imgSortData.setColorFilter(Color.parseColor("#d5d5d5"))
                        setProcess("ALL")
                        po.dismiss()
                    }
                    popupView.waitingPr.setOnClickListener {
                        binding.txtValueSr.text= statusByType("WAIT_PROCESS")
                        binding.imgSortData.setColorFilter(colorByStatus("WAIT_PROCESS"))
                        setProcess("WAIT_PROCESS")
                        po.dismiss()
                    }
                    popupView.transport.setOnClickListener {
                        binding.txtValueSr.text= statusByType("TRANSPORT")
                        binding.imgSortData.setColorFilter(colorByStatus("TRANSPORT"))
                        setProcess("TRANSPORT")
                        po.dismiss()
                    }
                    popupView.reject.setOnClickListener {
                        binding.txtValueSr.text= statusByType("REJECT")
                        binding.imgSortData.setColorFilter(colorByStatus("REJECT"))
                        setProcess("REJECT")
                        po.dismiss()
                    }
                    popupView.receive.setOnClickListener {
                        binding.txtValueSr.text= statusByType("RECEVIED")
                        binding.imgSortData.setColorFilter(Color.parseColor("#00FF19"))
                        setProcess("RECEVIED")
                        po.dismiss()
                    }
                }.show()
            }
        }
    }

    private fun setProcess(s: String) {

    }


    @SuppressLint("SetTextI18n")
    fun setDataToView(orderItem: MutableList<Order>) {
        orderItem.forEachIndexed { index, order ->
            val newRowBinding = ItemTableOrderViewBinding.inflate(layoutInflater)
            newRowBinding.txtStt.text = (index + 1).toString()
            newRowBinding.txtIdOrder.text = order.orderID
            newRowBinding.txtProductId.text = order.productId
            newRowBinding.txtPrName.text = order.productName
            newRowBinding.txtUserId.text = order.userId
            newRowBinding.txtUserName.text = order.receiverName
            newRowBinding.txtUserPhone.text = order.phoneNumber
            newRowBinding.txtUserAddress.text = order.location
            newRowBinding.txtPrCount.text = order.productCount.toString()
            newRowBinding.txtPrPrice.text = order.productPrice.toString() + " $"
            newRowBinding.txtStatus.text = order.orderStatus

            newRowBinding.llOrder.setStrokeColor(colorByStatus(order.orderStatus))

            newRowBinding.root.setOnClickListener {
                show_toast(index.toString())
            }
            binding.tableLayout.addView(newRowBinding.root)
        }
    }

    fun getOrder(calback: (MutableList<Order>) -> Unit) {
        firestore.collection(Constant.KEY_ORDER)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    val listData = mutableListOf<Order>()
                    task.result.forEach { item ->
                        val order = Order(item.id).apply {
                            shopId = item.getString(Constant.ORDER_SHOP_ID) ?: ""
                            userId = sharedPreferences.getString(Constant.USER_ID, "").toString()
                            productId = item.getString(Constant.ORDER_PR_ID) ?: ""
                            productName = item.getString(Constant.ORDER_PR_NAME) ?: ""
                            productCount = item.getLong(Constant.ORDER_PR_COUNT)?.toInt() ?: 0
                            productPrice =
                                item.getDouble(Constant.ORDER_PR_PRICE)?.toFloat() ?: 0.0f
                            productImg = item.getString(Constant.ORDRT_PR_IMG) ?: ""
                            orderStatus = item.getString(Constant.ORDER_STATUS)
                                ?: "" // Assuming OrderStatus is an enum or a constant
                            orderTime = item.getDate(Constant.ORDER_TIME).toString() ?: ""
                            orderStatusTime =
                                item.getDate(Constant.ORDER_STATUS_TIME).toString() ?: ""
                            remindName =
                                item.getString(Constant.ODR_ADR_REMIND_NAME).toString() ?: ""
                            receiverName = item.getString(Constant.ODR_ADR_US_NAME).toString() ?: ""
                            location = item.getString(Constant.ODR_ADR_ADDRESS).toString() ?: ""
                            phoneNumber = item.getString(Constant.ODR_ADR_F_PHONE).toString() ?: ""
                            phoneNumber2 = item.getString(Constant.ODR_ADR_S_PHONE).toString() ?: ""

                        }
                        listData.add(order)
                    }
                    calback(listData)
                }
            }
            .addOnFailureListener { exception ->
                show_toast(exception.message.toString())
            }

    }
}