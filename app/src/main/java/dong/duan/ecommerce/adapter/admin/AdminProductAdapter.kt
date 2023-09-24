package dong.duan.ecommerce.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.ItemAdminProductBinding
import dong.duan.ecommerce.databinding.ItemListCardViewBinding
import dong.duan.ecommerce.model.CardProduct
import dong.duan.ecommerce.model.Product

class AdminProductAdapter(var onCardEvent: OnCardAdminEvent) :BaseAdapter<Product,ItemAdminProductBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    )= ItemAdminProductBinding.inflate(inflater,parent,false)

    override fun bind(binding: ItemAdminProductBinding, item: Product, position: Int) {
        binding.txtName.setText(item.name)
        Glide.with(binding.root).load(item.imageUrl!!.get(0)).into(binding.imgProduct)
        binding.txtPrice.text= item.price.toString()
        binding.txtSale.text= item.saleOff.toString()+"%"
        binding.txtTarget.text= (item.price - (item.price*item.saleOff/100)).toString()
        binding.icPlus.setOnClickListener {
            item.count = item.count+1
            binding.txtNumCount.setText(item.count.toString())
            var titlePrice=item.count*(item.price -(item.price*(item.saleOff/100)))
            binding.txtPrice.setText("${titlePrice}")
            onCardEvent.onUpdateCount(titlePrice)
        }
        binding.root.setOnClickListener {
            onCardEvent.onDetail(product = item)
        }
        binding.icDiscount.setOnClickListener {
            item.count = item.count-1
            if(item.count<1){
                item.count=1
            }
            binding.txtNumCount.setText(item.count.toString())
            var titlePrice=item.count*(item.price -(item.price*(item.saleOff/100)))
            binding.txtPrice.setText("${titlePrice}")
            onCardEvent.onUpdateCount(titlePrice)
        }
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
    fun onUpdateCount(price:Float)
    fun onDetail(product: Product)
}