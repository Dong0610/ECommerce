package dong.duan.ecommerce.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.ItemListColectionCommentBinding

@Suppress("DEPRECATION")
class FilterCommentAdapter(var onFilterCommentSelect: OnFilterCommentSelect):RecyclerView.Adapter<FilterCommentAdapter.FilterCommentViewHolder>() {
    val listFilterCm= mutableListOf(
        FilterComment(null,"All Review"),
        FilterComment(1,"1"),
        FilterComment(2,"2"),
        FilterComment(3,"3"),
        FilterComment(4,"4"),
        FilterComment(5,"5")
    )
    var currentSlected=0
  inner class FilterCommentViewHolder(var binding: ItemListColectionCommentBinding):RecyclerView.ViewHolder(binding.root){
      @SuppressLint("NotifyDataSetChanged", "ResourceAsColor")
      fun setData(filterComment: FilterComment){
          if(currentSlected==position){

              binding.root.setBackgroundResource(R.drawable.bg_item_comment_select)
          }
          else{
              binding.root.setBackgroundResource(R.drawable.bg_item_comment_unselect)
          }
          if(filterComment.id==null){
              binding.icStar.visibility=View.GONE
          }
          else{
              binding.icStar.visibility=View.VISIBLE
          }
          binding.txtStar.text=filterComment.name
          binding.root.setOnClickListener {
              currentSlected=position
              notifyDataSetChanged()
              onFilterCommentSelect.onSelect(filterComment)
          }
      }
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterCommentViewHolder {
      return FilterCommentViewHolder(ItemListColectionCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
      return  listFilterCm.size
    }

    override fun onBindViewHolder(holder: FilterCommentViewHolder, position: Int) {
        holder.setData(listFilterCm.get(position))
    }
}
data class FilterComment(var id:Int?,var name:String)
interface OnFilterCommentSelect{
    fun onSelect(filterComment: FilterComment)
}