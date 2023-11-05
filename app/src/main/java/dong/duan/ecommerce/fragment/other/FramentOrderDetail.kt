package dong.duan.ecommerce.fragment.other

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentOrderDetailBinding
import dong.duan.ecommerce.fragment.main.CartFragment
import dong.duan.ecommerce.library.formatTime
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Order
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.utility.Constant
import dong.duan.ecommerce.utility.OrderStatus
import dong.duan.ecommerce.utility.colorByStatus
import dong.duan.ecommerce.utility.statusByType

class FramentOrderDetail(var order: Order) : BaseFragment<FragmentOrderDetailBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOrderDetailBinding.inflate(layoutInflater)

    var first_cancel = 0
    override fun initView() {
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }
        if (order != null) {
            orderView(order)
        }
    }

    var product: Product? = null

    @SuppressLint("SetTextI18n")
    fun orderView(order: Order) {
        CartFragment().productById(order.productId) {
            product = it
            binding.txtStatus.text = statusByType(order.orderStatus)
            binding.txtStatus.setTextColor(colorByStatus(order.orderStatus))
            binding.txtIdOrder.text = order.orderID
            binding.txtTimeOrdrer.text = formatTime(order.orderTime)
            binding.txtUsName.text = order.receiverName
            binding.txtUsId.text = order.userId
            binding.txtUserPhone.text = order.phoneNumber
            binding.usAddress.text = order.location
            binding.idProduct.text = it.id
            Glide.with(this.requireContext()).load(order.productImg)
                .into(binding.imgProduct)
            binding.txtOrderCount.text = order.productCount.toString()
            binding.txtPrice.text = order.productPrice.toString() + " $"
            binding.txtTotalPrice.text =
                (order.productCount * order.productPrice).toString() + "$"
            if (order.orderStatus.equals("REJECT") || order.orderStatus.equals("RECEIVED")) {
                cancelOk()
            }
        }


        binding.btnDetail.setOnClickListener {
            addFragment(ProductFragment(product!!, false))
        }

        binding.btnCancelOrder.setOnClickListener {
            if (first_cancel == 0) {
                binding.llCancelBill.visibility = View.VISIBLE
                first_cancel = 1
                binding.txtP3.text = "Xác nhận"
            } else if (first_cancel == 1) {
                val value = binding.txtCancelOrdrer.text.toString()
                if (value.equals("")) {
                    show_toast("Thêm lí do hủy đơn")
                } else {
                    firestore.collection(Constant.KEY_ORDER)
                        .document(order.orderID)
                        .update(Constant.ORDER_STATUS, OrderStatus.REJECT)
                        .addOnCompleteListener {
                            show_toast("Đã hủy đơn hàng")
                            cancelOk()
                            first_cancel = 0

                        }
                }
            }
        }
    }

    fun cancelOk() {
        binding.btnCancelOrder.isEnabled = false
        binding.btnCancelOrder.setStrokeColor(Color.parseColor("#d5d5d5"))
        binding.txtP3.setTextColor(Color.parseColor("#d5d5d5"))
    }

}