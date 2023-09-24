package dong.duan.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.databinding.ItemFlashSaleViewBinding
import dong.duan.ecommerce.model.Product

class FlashSaleAdapter(var context:Context,var onItemSelected: OnItemSelected):BaseAdapter<Product,ItemFlashSaleViewBinding> (){
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    )= ItemFlashSaleViewBinding.inflate(inflater,parent,false)

    override fun bind(binding: ItemFlashSaleViewBinding, item: Product, position: Int) {
        binding.txtName.setText(item.name)
        Glide.with(context).load(item.imageUrl!!.get(0)).into(binding.imgProduct)
        binding.txtPrice.text = "${item.price}$"
        binding.txtDiscount.text= "${item.price-(item.price*(item.saleOff/100))}$"
        binding.txtSale.text="${item.saleOff}%"
    }
}
interface OnItemSelected{
    fun onItemSelect()
}