package dong.duan.ecommerce.fragment.other

import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.adapter.user.AllCommentAdapter
import dong.duan.ecommerce.adapter.user.FilterComment
import dong.duan.ecommerce.adapter.user.FilterCommentAdapter
import dong.duan.ecommerce.adapter.user.OnFilterCommentSelect
import dong.duan.ecommerce.databinding.FragmentReviewProductBinding
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductReview
import dong.duan.ecommerce.utility.Constant

class ReviewProductFragment(var product: Product):BaseFragment<FragmentReviewProductBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )=FragmentReviewProductBinding.inflate(layoutInflater,container,false)


    var listComment= mutableListOf<ProductReview>()

    override fun initView() {

        binding.icBack.setOnClickListener {
            closeFragment(this@ReviewProductFragment)
        }

        getAllComment {
            listComment=it
            initRcv(it)
        }
    }


    fun getAllComment(calback: (MutableList<ProductReview>)->Unit){
        val listData= mutableListOf<ProductReview>()
        firestore.collection(Constant.KEY_REVIEW)
            .get()
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    task.result.documents.forEach { doc->
                        val review= ProductReview().apply {
                            reviewId= doc.id
                            reviewComment= doc.getString(Constant.REVIEW_COMMERNT) ?:""
                            productID
                            reviewImg= doc[Constant.REVIEW_IMG] as? ArrayList<Any> ?: null
                            reviewUserId =doc[Constant.REVIEW_USER_ID].toString()
                            reviewUserName = doc[Constant.REVIEW_USER_NAME].toString()
                            reviewUserImg= doc[Constant.REVIEW_USER_IMG].toString()
                            reviewStar = doc[Constant.REVIEW_STAR].toString().toFloat()
                            reviewComment = doc[Constant.REVIEW_COMMERNT].toString()
                            reviewTime = (doc[Constant.REVIEW_TIME]).toString()
                        }
                        listData.add(review)
                    }
                    calback(listData)
                }
            }.addOnFailureListener {
                show_toast(it.message.toString())
            }
    }


    private fun initRcv(reviews: MutableList<ProductReview>) {
        binding.rcvColection.adapter= FilterCommentAdapter(object : OnFilterCommentSelect {
            override fun onSelect(filterComment: FilterComment) {

            }
        })

        val allcommentAdapter= AllCommentAdapter(requireContext())
        binding.rcvListComment.adapter=allcommentAdapter
        allcommentAdapter.setItems(reviews)

        binding.btnWiteComment.setOnClickListener {
            addFragment(FragmentWriteReview(product = product))
        }

    }
}