package dong.duan.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.databinding.ItemFlashSaleViewBinding
import dong.duan.ecommerce.databinding.ItemListFavoriteViewBinding
import dong.duan.ecommerce.databinding.ItemListProductViewBinding
import dong.duan.ecommerce.model.Product

class MyFavoriteAdapter(var context:Context,var onItemSelected: OnFavoriteSelected):BaseAdapter<Product,ItemListFavoriteViewBinding> (){
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    )= ItemListFavoriteViewBinding.inflate(inflater,parent,false)

    override fun bind(binding: ItemListFavoriteViewBinding, item: Product, position: Int) {
        binding.txtName.setText(item.name)
        Glide.with(context).load(item.imageUrl!!.get(0)).into(binding.imgProduct)
        binding.txtPrice.text = "${item.price}$"
        binding.rating.numStars= item.star
        binding.txtDiscount.text= "${item.price-(item.price*(item.saleOff/100))}$"
        binding.txtSale.text="${item.saleOff}%"
        binding.icdelete.setOnClickListener {
            onItemSelected.onDelete(item)
        }
        binding.root.setOnClickListener {
            onItemSelected.onItemSelect(item)
        }
    }
}
interface OnFavoriteSelected{
    fun onItemSelect(product: Product)
    fun onDelete(product: Product)
}