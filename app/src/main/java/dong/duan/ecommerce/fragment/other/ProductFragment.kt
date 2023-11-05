package dong.duan.ecommerce.fragment.other

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.R
import dong.duan.ecommerce.adapter.user.AllCommentAdapter
import dong.duan.ecommerce.adapter.user.SlideShowAdapter
import dong.duan.ecommerce.databinding.FragmentProductDetailBinding
import dong.duan.ecommerce.databinding.ItemListProductSizeBinding
import dong.duan.ecommerce.dialog.DialogSuccess
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.library.data.MyDatabaseHelper
import dong.duan.ecommerce.library.formatTime
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductReview
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.utility.Constant
import dong.duan.ecommerce.utility.formatToCurrency
import java.util.Date
import java.util.Timer
import kotlin.concurrent.timerTask

class ProductFragment(val product: Product, var isAdmin: Boolean = false) :
    BaseFragment<FragmentProductDetailBinding>() {
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
            if (sizeSelect.equals("0") || sizeSelect.equals("")) {
                show_toast("Chưa chọn size!")
            } else {
                addToCart()
            }

        }
        if (isAdmin) {
            binding.btnAddCard.visibility = View.GONE
            binding.icLove.visibility = View.GONE
        }

        FragmentWriteReview.onCommentSuccess(object : OnCommentSuccess {
            override fun OnSuccess() {
                reloadProduct { loadPorduct(it) }
            }
        })
    }

    fun reloadProduct(calback: (Product) -> Unit) {
        firestore.collection(Constant.KEY_PRODUCT)
            .document(product.id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    val id = document.id
                    val name = document[Constant.PRODUCT_NAME].toString()
                    val price = document[Constant.PRODUCT_PRICE].toString().toFloat()
                    val saleOff = document[Constant.PRODUCT_SALEOFF].toString().toFloat()
                    val idShop = document[Constant.PRODUCT_SHOP_ID].toString()
                    val count = document[Constant.PRODUCT_COUNT].toString().toInt()
                    val imageUrl = document[Constant.PRODUCT_IMG] as? MutableList<Any> ?: null
                    val idUser = document[Constant.PRODUCT_USER_ID].toString()
                    val star = document[Constant.PRODUCT_STAR].toString().toFloat()
                    val timeUp = document[Constant.PRODUCT_TIME_UP].toString()
                    val describle = document[Constant.PRODUCT_DESCRIBLE].toString()
                    val manuID = document[Constant.PRODUCT_MANU_ID].toString()
                    val manuName = document[Constant.PRODUCT_MANU_NAME].toString()
                    val isSale = document[Constant.PRODUCT_IS_SALE].toString().toBoolean()
                    val productSizeList = mutableListOf<ProductSize>()
                    val productSizeData =
                        document[Constant.PRODUCT_SIZE] as? List<HashMap<String, Any>>
                            ?: emptyList()
                    val style = document[Constant.PRODUCT_STYLE].toString()
                    val evaluator = document[Constant.PRODUCT_EVALUATION].toString().toInt()
                    for (sizeMap in productSizeData) {
                        val size = sizeMap["size"]?.toString() ?: ""
                        val productSize = ProductSize(size)
                        productSizeList.add(productSize)
                    }
                    val product = Product(
                        id,
                        name,
                        price,
                        saleOff,
                        idShop,
                        count,
                        imageUrl,
                        idUser,
                        star,
                        timeUp,
                        isSale,
                        productSizeList,
                        manuID,
                        manuName,
                        describle, style, evaluator
                    )
                    calback(product)
                }
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
        hasMap[Constant.CART_PRODUCT_SIZE] = this.sizeSelect
        hasMap[Constant.CART_PRODUCT_ISBUY] = true
        hasMap[Constant.CART_PRODUCT_COUNT] = 1
        hasMap[Constant.CART_PRODUCT_TAX]= product.tax
        hasMap[Constant.CART_PRODUCT_TRANSMN]= product.transMoney
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
        val listLove = MyDatabaseHelper().readFavorite()

        val isLove = listLove.find { it == product.id }
        binding.icLove.setImageResource(if (isLove != null) R.drawable.ic_love_card else R.drawable.ic_love_app)
        binding.txtPrice.text = formatToCurrency(product.price)
        binding.txtName.text = product.name
        binding.rating.rating = product.star
        binding.txtStyle.text = product.style

        binding.txtIntroduce.text = product.description
        binding.txtStyle.text = product.style
        binding.icLove.setOnClickListener {
            if (isLove == null) {
                MyDatabaseHelper().insertFavorite(product.id)
                addToLove(product)
                binding.icLove.setImageResource(R.drawable.ic_love_card)
            } else {
                MyDatabaseHelper().deleteFavorite(product.id)
                deleteLove(product.id)
                binding.icLove.setImageResource(R.drawable.ic_love_app)
            }
            loadPorduct(product)
        }
        initBanner()
    }


    private fun deleteLove(productID: String) {
        database.getReference(Constant.KEY_LOVE)
            .child(sharedPreferences.getString(Constant.USER_ID).toString())
            .child(productID)
            .removeValue()
            .addOnCompleteListener {
                show_toast("Đã xóa khỏi danh sách yêu thích!")
            }
    }

    var sizeSelect = ""

    private fun addToLove(product: Product) {
        val hasMap = HashMap<String, Any>()
        hasMap[Constant.LOVE_US_ID] = sharedPreferences.getString(Constant.USER_ID).toString()
        hasMap[Constant.LOVE_USER_NAME] = sharedPreferences.getString(Constant.USER_NAME).toString()
        hasMap[Constant.LOVE_PRODUCT_ID] = product.id
        hasMap[Constant.LOVE_PRODUCT_IMG] = product.imageUrl!!.get(0).toString()
        hasMap[Constant.LOVE_PRODUCT_NAME] = product.name
        hasMap[Constant.LOVE_PRODUCT_SHOP_ID] = product.idShop
        hasMap[Constant.LOVE_PRODUCT_PRICE] = product.price
        hasMap[Constant.LOVE_PRODUCT_ISBUY] = true
        hasMap[Constant.LOVE_PRODUCT_COUNT] = 1
        database.getReference(Constant.KEY_LOVE)
            .child(sharedPreferences.getString(Constant.USER_ID).toString())
            .child(product.id)
            .setValue(hasMap)
            .addOnCompleteListener {
                show_toast("Đã thêm vào danh sách yêu thích!")
            }.addOnFailureListener {
                show_toast(it.message.toString())
            }
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
                sizeAdapter?.notifyItemChanged(currentSize)
                sizeAdapter?.notifyItemChanged(i)
                sizeSelect = item.size
                currentSize = i
            }
        }
        sizeAdapter!!.setItem(-1);
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
                    task.result.documents.filterIndexed { index, doc ->
                        if (index <= 3) {
                            val review = ProductReview().apply {
                                reviewId = doc.id
                                reviewComment = doc.getString(Constant.REVIEW_COMMERNT) ?: ""
                                productID = doc.getString(Constant.REVIEW_PR_ID) ?: ""
                                reviewImg = doc[Constant.REVIEW_IMG] as? ArrayList<Any> ?: null
                                reviewUserId = doc[Constant.REVIEW_USER_ID].toString()
                                reviewUserName = doc[Constant.REVIEW_USER_NAME].toString()
                                reviewUserImg = doc[Constant.REVIEW_USER_IMG].toString()
                                reviewStar = doc[Constant.REVIEW_STAR].toString().toFloat()
                                reviewComment = doc[Constant.REVIEW_COMMERNT].toString()
                                reviewTime = formatTime(doc.getDate(Constant.REVIEW_TIME) as Date)
                            }
                            if (review.productID.equals(product.id)) {
                                listData.add(review)
                            }
                        } else {
                            return@filterIndexed false
                        }
                        true
                    }
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