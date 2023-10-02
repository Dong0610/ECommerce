package dong.duan.ecommerce.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.databinding.ItemListOrderViewBinding
import dong.duan.ecommerce.model.OrderData
import dong.duan.ecommerce.utility.colorByStatus
import dong.duan.ecommerce.utility.statusByType

class OrderCountAdapter : BaseAdapter<OrderData, ItemListOrderViewBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = ItemListOrderViewBinding.inflate(inflater, parent, false)

    override fun bind(binding: ItemListOrderViewBinding, item: OrderData, position: Int) {
        binding.llParent.post {
            val width = binding.llParent.width
            val score = item.count.toFloat() / item.maxCount.toFloat()
            if (item.count != 0) {
                val newW = width.toFloat() * score
                val layoutParams = binding.viewColor.layoutParams
                layoutParams.width = newW.toInt()
                binding.viewColor.layoutParams = layoutParams
            }

            binding.viewColor.setGradientColors(
                intArrayOf(
                    colorByStatus(item.value),
                    colorByStatus(item.value)
                )
            )
            binding.txtType.setText(statusByType(item.value))
            binding.txtNumCount.setText(item.count.toString())
        }
    }
}