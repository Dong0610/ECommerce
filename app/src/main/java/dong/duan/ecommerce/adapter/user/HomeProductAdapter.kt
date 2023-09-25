package dong.duan.ecommerce.adapter.user

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.databinding.ItemListProductViewBinding
import dong.duan.ecommerce.model.Product

class HomeProductAdapter(var context: Context, var onItemSelected: OnProductSelected) :
    BaseAdapter<Product, ItemListProductViewBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = ItemListProductViewBinding.inflate(inflater, parent, false)

    @SuppressLint("SetTextI18n")
    override fun bind(binding: ItemListProductViewBinding, item: Product, position: Int) {
        binding.txtName.setText(item.name)
        Glide.with(context).load(item.imageUrl!!.get(0)).into(binding.imgProduct)
        binding.txtPrice.text = "${item.price}$"
        binding.rating.numStars = item.star
        binding.txtDiscount.text = "${item.price - (item.price * (item.saleOff / 100))}$"
        binding.txtSale.text = "${item.saleOff}%"
        binding.root.setOnClickListener {
            onItemSelected.onItemSelect(item)
        }
    }
}

interface OnProductSelected {
    fun onItemSelect(product: Product)
}