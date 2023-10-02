package dong.duan.ecommerce.adapter.admin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.databinding.ItemAdminProductBinding
import dong.duan.ecommerce.model.Product

class AdminProductAdapter(var onCardEvent: OnCardAdminEvent) :BaseAdapter<Product,ItemAdminProductBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    )= ItemAdminProductBinding.inflate(inflater,parent,false)

    @SuppressLint("SetTextI18n")
    override fun bind(binding: ItemAdminProductBinding, item: Product, position: Int) {
        binding.txtName.setText(item.name)
        Glide.with(binding.root).load(item.imageUrl!!.get(0)).into(binding.imgProduct)
        binding.txtPrice.text= item.price.toString()
        binding.txtSale.text= item.saleOff.toString()+"%"
        binding.txtNumCount.setText(item.count.toString())
        binding.txtTarget.text= (item.price - (item.price*item.saleOff/100)).toString()
        binding.icEdit.setOnClickListener {
            onCardEvent.editProduct(item)
        }
        binding.icdelete.setOnClickListener {
            onCardEvent.onDelete(item)
        }


    }
}

interface OnCardAdminEvent{
    fun editProduct(product: Product)
    fun onDelete(product: Product)
    fun onDetail(product: Product)
}