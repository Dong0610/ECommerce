package dong.duan.ecommerce.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.databinding.ItemSreachViewBinding

class SearchAdapter(var onSearch:OnSearchItem):BaseAdapter<String,ItemSreachViewBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    )=ItemSreachViewBinding.inflate(inflater,parent,false)

    override fun bind(binding: ItemSreachViewBinding, item: String, position: Int) {
        binding.txtView.text=item
       binding.root.setOnClickListener {
           onSearch.onSelectItem(item)
       }
    }
}
interface OnSearchItem{
    fun onSelectItem(value:String)
}