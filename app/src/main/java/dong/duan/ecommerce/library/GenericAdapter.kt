package dong.duan.ecommerce.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericAdapter<T, VB : ViewBinding>(
    private val itemList: List<T>,
    private val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val binder: (VB, T, Int) -> Unit
) : RecyclerView.Adapter<GenericAdapter<T, VB>.GenericViewHolder<VB>>() {

    inner class GenericViewHolder<VB : ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = bindingInflater.invoke(inflater, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    var position=RecyclerView.NO_POSITION
    override fun onBindViewHolder(holder: GenericViewHolder<VB>, position: Int) {
        val item = itemList[position]
        binder.invoke(holder.binding, item, position)
    }
    fun setItem(position: Int){
        notifyItemChanged(this.position)
        this.position=position
        notifyItemChanged(position)
    }
}

