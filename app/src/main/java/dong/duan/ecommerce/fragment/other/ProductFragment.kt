package dong.duan.ecommerce.fragment.other

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.R
import dong.duan.ecommerce.adapter.AllCommentAdapter
import dong.duan.ecommerce.adapter.SlideShowAdapter
import dong.duan.ecommerce.databinding.FragmentProductDetailBinding
import dong.duan.ecommerce.databinding.ItemListProductSizeBinding
import dong.duan.ecommerce.dialog.DialogSuccess
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductReview
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.utility.Constant
import java.util.Timer
import kotlin.concurrent.timerTask

class ProductFragment(val product: Product) : BaseFragment<FragmentProductDetailBinding>() {
    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentProductDetailBinding.inflate(layoutInflater, container, false)

    override fun initView() {

        if (sharedPreferences.getString(Constant.ACCOUNT_TYPE).toString().equals("1")) {
            binding.icLove.visibility = View.GONE
        }

        loadPorduct(product)
        initRcv()
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }
        binding.txtSeeMore.setOnClickListener {
            replaceFullViewFragment(ReviewProductFragment(product = product), true)
        }
        binding.btnAddCard.setOnClickListener {
            addToCart()
        }
    }

    private fun addToCart() {
        val hasMap = HashMap<String, Any>()
        hasMap[Constant.CART_US_ID] = sharedPreferences.getString(Constant.USER_ID).toString()
        hasMap[Constant.CART_USER_NAME] = sharedPreferences.getString(Constant.USER_NAME).toString()
        hasMap[Constant.CART_PRODUCT_ID] = product.id
        hasMap[Constant.CART_PRODUCT_IMG] = product.imageUrl!!.get(0)
        hasMap[Constant.CART_PRODUCT_NAME] = product.name
        hasMap[Constant.CART_PRODUCT_SHOP_ID] = product.idShop
        hasMap[Constant.CART_PRODUCT_PRICE] =
            product.price - (product.price * product.saleOff / 100)
        hasMap[Constant.CART_PRODUCT_ISBUY] = true
        hasMap[Constant.CART_PRODUCT_COUNT] = 1
        database.getReference(Constant.KEY_CART)
            .child(sharedPreferences.getString(Constant.USER_ID).toString())
            .child(product.id)
            .setValue(hasMap)
            .addOnCompleteListener {
                DialogSuccess(this.requireContext(), {

                }).show()
            }.addOnFailureListener {
                show_toast(it.message.toString())
            }
    }

    private fun loadPorduct(product: Product) {
        binding.txtPrice.text = product.price.toString() + "$"
        binding.txtName.text = product.name
        binding.rating.numStars = 3
        binding.txtStyle.text = product.style
        binding.txtIntroduce.text = product.description
        initBanner()
    }

    private fun initBanner() {

        var listFragmentBanner = arrayListOf<BaseFragment<*>>()
        var timer: Timer? = null
        var timeAutoNextBanner = 5000
        var handlerNextBanner = Handler(Looper.getMainLooper())

        fun addControl() {
            val adapter = SlideShowAdapter(
                childFragmentManager, listFragmentBanner, requireContext()
            )
            binding.viewPager.adapter = adapter
            binding.dot.attachTo(binding.viewPager)

            binding.viewPager.offscreenPageLimit = 3
        }

        var currentPage = 0
        fun autoSlide() {
            binding.viewPager.setCurrentItem(0, true)
            if (timer == null) {
                timer = Timer()
            }
            timer?.schedule(timerTask {
                handlerNextBanner.post {

                    val count = listFragmentBanner.size
                    currentPage = (currentPage + 1) % count
                    if (currentPage == 0) {
                        currentPage = 1
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.viewPager.setCurrentItem(currentPage, false)
                        }, 200)
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.viewPager.setCurrentItem(currentPage, true)
                        }, 200)
                    }
                }
            }, timeAutoNextBanner.toLong(), timeAutoNextBanner.toLong())
        }

        product.imageUrl!!.forEachIndexed { index, any ->
            listFragmentBanner.add(SlideImageFragment.newInstance(index, any.toString()))
        }

        addControl()
        autoSlide()

    }


    var sizeAdapter: GenericAdapter<ProductSize, ItemListProductSizeBinding>? = null
    var currentSize = 0

    @SuppressLint("NotifyDataSetChanged")
    private fun initRcv() {
        val listProductSize = mutableListOf<ProductSize>()
        product.porductSize!!.forEach { productSize ->
            listProductSize.add(ProductSize(productSize.size))
        }
        sizeAdapter = GenericAdapter(
            listProductSize,
            ItemListProductSizeBinding::inflate
        ) { itembinding, item, i ->
            itembinding.txtName.text = item.size
            itembinding.root.setBackgroundResource(
                if (currentSize == i) {
                    R.drawable.bg_item_model_category
                } else {
                    R.drawable.bg_item_unselect_category
                }
            )
            itembinding.root.setOnClickListener {
                currentSize = i
                sizeAdapter?.notifyDataSetChanged()
            }
        }
        binding.rcvSize.adapter = sizeAdapter

        getAllComment {
            initRcv(it)
        }

    }


    fun getAllComment(calback: (MutableList<ProductReview>) -> Unit) {
        val listData = mutableListOf<ProductReview>()
        firestore.collection(Constant.KEY_REVIEW)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.documents.filterIndexed { index, doc->
                        if (index <= 3) {
                        val review = ProductReview().apply {
                            reviewId = doc.id
                            reviewComment = doc.getString(Constant.REVIEW_COMMERNT) ?: ""
                            productID
                            reviewImg = doc[Constant.REVIEW_IMG] as? ArrayList<Any> ?: null
                            reviewUserId = doc[Constant.REVIEW_USER_ID].toString()
                            reviewUserName = doc[Constant.REVIEW_USER_NAME].toString()
                            reviewUserImg = doc[Constant.REVIEW_USER_IMG].toString()
                            reviewStar = doc[Constant.REVIEW_STAR].toString().toFloat()
                            reviewComment = doc[Constant.REVIEW_COMMERNT].toString()
                            reviewTime = (doc[Constant.REVIEW_TIME]).toString()
                        }
                        listData.add(review)
                    } else {
                        return@filterIndexed false
                    }
                    true
                }
                calback(listData)
                    calback(listData)
                }
            }.addOnFailureListener {
                show_toast(it.message.toString())
            }
    }


    private fun initRcv(reviews: MutableList<ProductReview>) {
        val allcommentAdapter = AllCommentAdapter(requireContext())
        binding.rcvComment.adapter = allcommentAdapter
        allcommentAdapter.setItems(reviews)
    }
}