package dong.duan.ecommerce.adapter.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.ItemListCardViewBinding
import dong.duan.ecommerce.model.CardProduct
import dong.duan.ecommerce.utility.CheckBoxImageView
import dong.duan.ecommerce.utility.formatToCurrency

class CardAdapter(var onCardEvent: OnCardEvent) :BaseAdapter<CardProduct,ItemListCardViewBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    )= ItemListCardViewBinding.inflate(inflater,parent,false)
    override fun bind(binding: ItemListCardViewBinding, item: CardProduct, position: Int) {

        binding.iclove.setImageResource(if(item.islove) R.drawable.ic_love_card else R.drawable.ic_love_app)
        binding.txtName.setText(item.productName)
        binding.txtPrice.text= formatToCurrency(item.price)
        Glide.with(binding.root).load(item.prductImg).into(binding.imgProduct)
        binding.icPlus.setOnClickListener {
            item.nunCount = item.nunCount+1
            binding.txtNumCount.setText(item.nunCount.toString())
            val titlePrice=item.nunCount*(item.price)
            binding.txtPrice.setText(formatToCurrency( titlePrice))
            onCardEvent.onUpdateCount(item.nunCount,item.productID)
        }
        binding.icDiscount.setOnClickListener {
            item.nunCount = item.nunCount-1
            if(item.nunCount<1){
                item.nunCount=1
            }
            binding.txtNumCount.setText(item.nunCount.toString())
            val titlePrice=item.nunCount*item.price
            binding.txtPrice.setText(formatToCurrency( titlePrice))
            onCardEvent.onUpdateCount(item.nunCount,item.productID)
        }
        binding.iclove.setOnClickListener {
            onCardEvent.onLove(item)
        }
        binding.icdelete.setOnClickListener {
            onCardEvent.onDelete(item)
        }
        binding.root.setOnClickListener {
            onCardEvent.onSelect(item.productID)
        }
        binding.checkboxBuy.setOnCheckedChangeListener(object :CheckBoxImageView.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: View?, isChecked: Boolean) {
                onCardEvent.onBuy(item)
            }
        })
    }
}

interface OnCardEvent{
    fun onLove(product: CardProduct)
    fun onDelete(product: CardProduct)
    fun onUpdateCount(count:Int,id:String)
    fun onSelect(idProcunt:String)
    fun onBuy(product: CardProduct)
}