package dong.duan.ecommerce.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.databinding.ItemTableOrderViewBinding

import dong.duan.ecommerce.model.Order

class AdminOrderAdapter :BaseAdapter<Order,ItemTableOrderViewBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = ItemTableOrderViewBinding.inflate(inflater,parent,false)

    override fun bind(binding: ItemTableOrderViewBinding, item: Order, position: Int) {
    }
}