package dong.duan.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.ItemListImageCommentBinding
import dong.duan.ecommerce.databinding.ItemListReviewProductBinding
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.model.ProductReview

class AllCommentAdapter(var context: Context) :
    BaseAdapter<ProductReview, ItemListReviewProductBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ) = ItemListReviewProductBinding.inflate(inflater, parent, false)

    override fun bind(binding: ItemListReviewProductBinding, item: ProductReview, position: Int) {
        binding.txtName.text = item.reviewUserName
        if (item.reviewUserImg.equals("")) {
            Glide.with(context).load(R.drawable.user_img).into(binding.imgUsers)
        } else {
            Glide.with(context).load(item.reviewUserImg).into(binding.imgUsers)
        }
        binding.rating.rating = item.reviewStar
        binding.txtTime.text = item.reviewTime
        binding.contentCm.text = item.reviewComment
        if (item.reviewImg!!.size > 0) {
            binding.listImg.visibility = View.VISIBLE
            val adapter = GenericAdapter(
                item.reviewImg!!,
                ItemListImageCommentBinding::inflate
            ) { itembinding, s, i ->
                Glide.with(context).load(s).into(itembinding.imagePreview)
            }
            binding.listImg.adapter = adapter
        } else {
            binding.listImg.visibility = View.VISIBLE
        }
    }
}
