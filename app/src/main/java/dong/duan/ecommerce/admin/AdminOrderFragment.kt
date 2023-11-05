package dong.duan.ecommerce.admin

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentOrderAdminBinding
import dong.duan.ecommerce.databinding.ItemTableOrderViewBinding
import dong.duan.ecommerce.databinding.PopupSortOrderBinding
import dong.duan.ecommerce.fragment.main.CartFragment
import dong.duan.ecommerce.fragment.other.ProductFragment
import dong.duan.ecommerce.library.base.BasePopupLocation
import dong.duan.ecommerce.library.formatTime
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Order
import dong.duan.ecommerce.utility.Constant
import dong.duan.ecommerce.utility.OrderStatus
import dong.duan.ecommerce.utility.colorByStatus
import dong.duan.ecommerce.utility.statusByType
import java.util.Date


class AdminOrderFragment : BaseFragment<FragmentOrderAdminBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOrderAdminBinding.inflate(layoutInflater)

    var listOrderData = mutableListOf<Order>()
    override fun initView() {

        getOrder {
            listOrderData = it
            setDataToView(it)
        }

        onClick()
    }

    private fun onClick() {
        binding.imgSortData.setOnClickListener {
            it.post {
                val location = IntArray(2)
                it.getLocationOnScreen(location)
                val xCoordinate = location[0] + it.width + 5
                val yCoordinate = location[1]
                BasePopupLocation(
                    this@AdminOrderFragment.requireContext(),
                    it,
                    PopupSortOrderBinding::inflate,
                    xCoordinate, yCoordinate
                ) { popupView, po ->
                    val setStatus = { status: String, color: Int ->
                        binding.txtValueSr.text = statusByType(status)
                        binding.imgSortData.setColorFilter(colorByStatus(status))
                        setProcess(status)
                        po.dismiss()
                    }

                    popupView.all.setOnClickListener {
                        binding.txtValueSr.text = "Tất cả"
                        binding.imgSortData.setColorFilter(Color.parseColor("#d5d5d5"))
                        setProcess("ALL")
                        po.dismiss()
                    }
                    popupView.waitingPr.setOnClickListener {
                        setStatus(
                            "WAIT_PROCESS",
                            colorByStatus("WAIT_PROCESS")
                        )
                    }
                    popupView.process.setOnClickListener {
                        setStatus(
                            "PROCESSING",
                            colorByStatus("PROCESSING")
                        )
                    }
                    popupView.transport.setOnClickListener {
                        setStatus(
                            "TRANSPORT",
                            colorByStatus("TRANSPORT")
                        )
                    }
                    popupView.reject.setOnClickListener {
                        setStatus(
                            "REJECT",
                            colorByStatus("REJECT")
                        )
                    }
                    popupView.receive.setOnClickListener {
                        setStatus(
                            "RECEIVED",
                            Color.parseColor("#FF01D301")
                        )
                    }
                }.show()
            }
        }
    }

    private fun setProcess(s: String) {
        val listSortData = mutableListOf<Order>()
        if (s.equals("ALL")) {
            setDataToView(this.listOrderData)
        } else {
            listOrderData.forEach {
                if (it.orderStatus.equals(s)) {
                    listSortData.add(it)
                }
            }
            setDataToView(listSortData)
        }
    }


    @SuppressLint("SetTextI18n")
    fun setDataToView(orderItem: MutableList<Order>) {
        val tableLayout = binding.tableLayout
        val childCount = tableLayout.childCount
        if (childCount > 1) {
            tableLayout.removeViews(1, childCount - 1)
        }
        orderItem.forEachIndexed { index, order ->
            val newRowBinding = ItemTableOrderViewBinding.inflate(layoutInflater)
            newRowBinding.txtStt.text = (index + 1).toString()
            newRowBinding.txtIdOrder.text = order.orderID
            newRowBinding.txtProductId.text = order.productId
            newRowBinding.txtPrName.text = order.productName
            newRowBinding.txtPrSize.text= order.orderPrSize.toString()
            newRowBinding.txtUserId.text = order.userId
            newRowBinding.txtUserName.text = order.receiverName
            newRowBinding.txtUserPhone.text = order.phoneNumber
            newRowBinding.txtUserAddress.text = order.location
            newRowBinding.txtPrCount.text = order.productCount.toString()
            newRowBinding.txtPrPrice.text = order.productPrice.toString() + " $"
            newRowBinding.txtStatus.text = order.orderStatus
            newRowBinding.llOrder.setStrokeColor(colorByStatus(order.orderStatus))
            newRowBinding.root.setOnClickListener {
                orderView(order)
            }
            binding.tableLayout.addView(newRowBinding.root)
        }
        binding.txtSumOrder.text = orderItem.size.toString() + " đơn hàng"
    }

    var first_cancel = 0
    @SuppressLint("SetTextI18n")
    private fun orderView(order: Order) {
        fun received() {
            binding.include.btnCancelOrder.isEnabled = false
            binding.include.btnTranspotOrder.isEnabled = false
            binding.include.btnProcessOrder.isEnabled = false
            binding.include.btnCancelOrder.setStrokeColor(Color.parseColor("#d5d5d5"))
            binding.include.btnTranspotOrder.setStrokeColor(Color.parseColor("#d5d5d5"))
            binding.include.btnProcessOrder.setStrokeColor(Color.parseColor("#d5d5d5"))
            binding.include.txtP1.setTextColor(Color.parseColor("#d5d5d5"))
            binding.include.txtP2.setTextColor(Color.parseColor("#d5d5d5"))
            binding.include.txtP3.setTextColor(Color.parseColor("#d5d5d5"))
        }

        fun processOther() {
            binding.include.btnCancelOrder.isEnabled = true
            binding.include.btnTranspotOrder.isEnabled = true
            binding.include.btnProcessOrder.isEnabled = true
            binding.include.btnCancelOrder.setStrokeColor(Color.parseColor("#FF0000"))
            binding.include.btnTranspotOrder.setStrokeColor(Color.parseColor("#8A2BE2"))
            binding.include.btnProcessOrder.setStrokeColor(Color.parseColor("#03A9F4"))
            binding.include.txtP1.setTextColor(Color.parseColor("#223263"))
            binding.include.txtP2.setTextColor(Color.parseColor("#223263"))
            binding.include.txtP3.setTextColor(Color.parseColor("#223263"))
        }
        binding.progressLoad.show(true)
        CartFragment().productById(order.productId) {

            val product = it
            Handler(Looper.myLooper()!!)
                .postDelayed({
                    binding.progressLoad.show(false)
                    binding.include.txtStatus.text = statusByType(order.orderStatus)
                    binding.include.txtStatus.setTextColor(colorByStatus(order.orderStatus))
                    binding.include.txtIdOrder.text = order.orderID
                    binding.include.txtTimeOrdrer.text = formatTime(order.orderTime)
                    binding.include.txtUsName.text = order.receiverName
                    binding.include.txtUsId.text = order.userId
                    binding.include.txtUserPhone.text = order.phoneNumber
                    binding.include.usAddress.text = order.location
                    binding.include.idProduct.text = it.id
                    Glide.with(this@AdminOrderFragment.requireContext()).load(order.productImg)
                        .into(binding.include.imgProduct)
                    binding.include.txtOrderCount.text = order.productCount.toString()
                    binding.include.txtProductCount.text = it.count.toString()
                    binding.include.txtPrice.text = order.productPrice.toString() + " $"
                    binding.include.txtTotalPrice.text =
                        (order.productCount * order.productPrice).toString() + "$"
                }, 1000L)

            binding.include.btnDetail.setOnClickListener {
                addFragment(ProductFragment(product, false))
            }

            if (order.orderStatus.equals("RECEIVED")) {
                received()
            }
            else{
                processOther()
            }

            binding.include.btnProcessOrder.setOnClickListener {
                firestore.collection(Constant.KEY_ORDER)
                    .document(order.orderID)
                    .update(Constant.ORDER_STATUS, OrderStatus.PROCESSING)
                    .addOnCompleteListener {
                        show_toast("Chuyển trạng thái thành công")
                        orderView(order)
                        setDataToView(listOrderData)
                    }

            }
            binding.include.btnTranspotOrder.setOnClickListener {
                firestore.collection(Constant.KEY_ORDER)
                    .document(order.orderID)
                    .update(Constant.ORDER_STATUS, OrderStatus.TRANSPORT)
                    .addOnCompleteListener {
                        show_toast("Chuyển trạng thái thành công")
                        orderView(order)
                        setDataToView(listOrderData)
                    }
            }
            binding.include.btnCancelOrder.setOnClickListener {
                if (first_cancel == 0) {
                    binding.include.llCancelBill.visibility = View.VISIBLE
                    first_cancel = 1
                    binding.include.txtP3.text = "Xác nhận"
                } else if (first_cancel == 1) {
                    val value = binding.include.txtCancelOrdrer.text.toString()
                    if (value.equals("")) {
                        show_toast("Thêm lí do hủy đơn")
                    } else {
                        firestore.collection(Constant.KEY_ORDER)
                            .document(order.orderID)
                            .update(Constant.ORDER_STATUS, OrderStatus.REJECT)
                            .addOnCompleteListener {
                                show_toast("Đã hủy đơn hàng")
                                received()
                                first_cancel = 0
                                setDataToView(listOrderData)

                            }
                    }
                }
            }
        }

    }

    fun ProgressBar.show(show: Boolean) {
        visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
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
                            orderStatus = item.getString(Constant.ORDER_STATUS) ?: ""
                            orderTime = (item.getDate(Constant.ORDER_TIME)!! as? Date)!!
                            orderStatusTime =
                                (item.getDate(Constant.ORDER_STATUS_TIME)!! as? Date)!!
                            item.getString(Constant.ODR_ADR_REMIND_NAME).toString()
                            receiverName = item.getString(Constant.ODR_ADR_US_NAME).toString()
                            location = item.getString(Constant.ODR_ADR_ADDRESS).toString()
                            phoneNumber = item.getString(Constant.ODR_ADR_F_PHONE).toString()
                            phoneNumber2 = item.getString(Constant.ODR_ADR_S_PHONE).toString()
                            cancelMess = item.getString(Constant.ORDER_CANCEL_VALUE).toString()
                            orderPrSize = item.get(Constant.ORDER_PR_SIZE).toString().toInt()
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