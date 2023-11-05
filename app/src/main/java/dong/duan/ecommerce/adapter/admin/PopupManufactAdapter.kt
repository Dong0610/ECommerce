package dong.duan.ecommerce.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.databinding.ItemManafactViewBinding
import dong.duan.ecommerce.model.Manufacturer

class PopupManufactAdapter (var onPopupCalback: OnPopupCalback) :
    BaseAdapter<Manufacturer, ItemManafactViewBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    )= ItemManafactViewBinding.inflate(inflater,parent,false)

    override fun bind(binding: ItemManafactViewBinding, item: Manufacturer, position: Int) {
        binding.root.setOnClickListener {
            onPopupCalback.OnSelect(item)
        }
        Glide.with(binding.root).load(item.imageUrl).into(binding.imgView)
        binding.txtView.text= item.nameManu
    }
}

interface OnPopupCalback{
    fun OnSelect(manufact:Manufacturer)
}