package dong.duan.ecommerce.adapter.user

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.databinding.ItemListOrderBinding
import dong.duan.ecommerce.library.formatTime
import dong.duan.ecommerce.model.Order
import dong.duan.ecommerce.utility.colorByStatus
import dong.duan.ecommerce.utility.formatToCurrency
import dong.duan.ecommerce.utility.statusByType

class ListOrderAdapter(context: Context,var detail: (Order)->Unit) : BaseAdapter<Order,ItemListOrderBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    )= ItemListOrderBinding.inflate(inflater,parent,false)
    @SuppressLint("SetTextI18n")
    override fun bind(binding: ItemListOrderBinding, item: Order, position: Int) {
        binding.txtIdOrder.text=item.orderID
        binding.txtTime.text= formatTime(item.orderTime)
        binding.txtTotalPrice.text= formatToCurrency(item.productCount*item.productPrice)
        binding.txtStatus.text= statusByType(item.orderStatus)
        binding.txtShipingCount.text= "${item.productCount} sản phẩm"
        var isDetail= true
        binding.txtUpDateTime.text=formatTime( item.orderStatusTime)
        binding.root.setStrokeColor(colorByStatus(item.orderStatus))

        binding.icDetail.setOnClickListener {
            if (isDetail) {
                binding.txtUpDateTime.visibility = View.VISIBLE
                binding.icDetail.rotation = 90f
                isDetail = false
            } else {
                binding.txtUpDateTime.visibility = View.GONE
                binding.icDetail.rotation = 0f
                isDetail = true
            }
        }
        binding.txtDetail.setOnClickListener {
            detail(item)
        }

    }

}