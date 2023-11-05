package dong.duan.ecommerce.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.adapter.admin.OrderCountAdapter
import dong.duan.ecommerce.databinding.FragmentRevenueBinding
import dong.duan.ecommerce.model.OrderData

class FragmentRevenue : BaseFragment<FragmentRevenueBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRevenueBinding.inflate(layoutInflater)

    var listData = mutableListOf<OrderData>()
    val orderStatusCounts = mutableMapOf<String, Int>().withDefault { 0 }

    override fun initView() {
        val listOrder = AdminActivity.listOrder
        listOrder.forEach { order ->
            val status = order.orderStatus
            orderStatusCounts[status] = orderStatusCounts.getValue(status) + 1
        }
        val maxStatusCount = orderStatusCounts.values.maxOrNull() ?: 0
        val orderCountADT = OrderCountAdapter()
        binding.rcvViewOrder.adapter = orderCountADT
        val statusLabels = listOf("PROCESSING", "WAIT_PROCESS", "REJECT", "RECEIVED", "TRANSPORT")
        statusLabels.forEach { label ->
            val count = orderStatusCounts.getValue(label)
            listData.add(OrderData(label, count, maxStatusCount))
        }
        orderCountADT.setItems(listData)

        var totalOrder=0
        var totalPrice=0f
        var totalSale =0

        listOrder.forEach { item->
            totalOrder+=1
            totalSale+=item.productCount
            totalPrice+= item.productCount*item.productPrice
        }
        binding.txtCountOrder.setText(totalOrder.toString())
        binding.txtCountSale.setText(totalSale.toString())
        binding.txtCountPrice.setText(totalPrice.toString())
    }

}