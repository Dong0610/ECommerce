package dong.duan.ecommerce.fragment.main

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.adapter.FlashSaleAdapter
import dong.duan.ecommerce.adapter.HomeProductAdapter
import dong.duan.ecommerce.adapter.OnItemSelected
import dong.duan.ecommerce.adapter.OnProductSelected
import dong.duan.ecommerce.adapter.SlideShowAdapter
import dong.duan.ecommerce.databinding.FragmentHomeBinding
import dong.duan.ecommerce.databinding.ItemListCategoryBinding
import dong.duan.ecommerce.fragment.other.ProductFragment
import dong.duan.ecommerce.fragment.other.SlideBannerFragment
import dong.duan.ecommerce.interfaces.FetchAllData
import dong.duan.ecommerce.interfaces.SearchManufactData
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Category
import dong.duan.ecommerce.model.Manufacturer
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.utility.Constant
import java.util.Timer
import kotlin.concurrent.timerTask

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    companion object{
        private var homeSearch : SearchManufactData? =  null
        fun searchByManufact(homeSearch:SearchManufactData) {
          this.homeSearch= homeSearch
        }
        var listFetchAllData = mutableListOf<Product>()
    }

    private var listFragmentBanner = arrayListOf<BaseFragment<*>>()
    private var timer: Timer? = null
    private var timeAutoNextBanner = 5000
    private var handlerNextBanner = Handler(Looper.getMainLooper())
    override fun initView() {
        initBanner()
        loadRecyview()

        getManuFacturer {
            val manufactAdapter = GenericAdapter(
                it,
                ItemListCategoryBinding::inflate
            ) { itembinding, category, i ->
                Glide.with(this@HomeFragment.requireContext()).load(category.imageUrl)
                    .into(itembinding.imgView)
                itembinding.txtName.text = category.nameManu

                itembinding.root.setOnClickListener {
                   homeSearch!!.onHomeSearch(category)
                }
            }
            binding.rcvCategory.adapter = manufactAdapter
        }

        getAllProduct { products ->

            listFlasShale(products)
            listMegaSale(products)
            loadProduct(products)
           listFetchAllData = products
        }
    }



    private fun loadProduct(products: MutableList<Product>) {
        val productAdapter = HomeProductAdapter(requireContext(), object : OnProductSelected {
            override fun onItemSelect(product: Product) {
                replaceFullViewFragment(ProductFragment(product), true)
            }

        })
        binding.rcvListProduct.adapter = productAdapter
        productAdapter.setItems(products)
    }

    private fun listMegaSale(products: MutableList<Product>) {
        val listMegaSale = mutableListOf<Product>()
        products.forEach { item ->
            if (item.saleOff > 5f && item.saleOff <= 35f) {
                listMegaSale.add(item)
            }
        }
        val megaSaleAdapter = FlashSaleAdapter(requireContext(), object : OnItemSelected {
            override fun onItemSelect(product:Product) {
                replaceFullViewFragment(ProductFragment(product), true)
            }
        })
        binding.rcvMegaSale.adapter = megaSaleAdapter
        megaSaleAdapter.setItems(listMegaSale)
    }

    private fun listFlasShale(products: MutableList<Product>) {
        val listFlashSale = mutableListOf<Product>()
        products.forEach { item ->
            if (item.saleOff > 35f) {
                listFlashSale.add(item)
            }
        }
        val flashSaleAdapter = FlashSaleAdapter(requireContext(), object : OnItemSelected {
            override fun onItemSelect(product: Product) {
                replaceFullViewFragment(ProductFragment(product), true)
            }
        })
        binding.rcvFlashsale.adapter = flashSaleAdapter
        flashSaleAdapter.setItems(listFlashSale)
    }

    private fun getManuFacturer(calback: (ArrayList<Manufacturer>) -> Unit) {
        firestore.collection(Constant.SHOP_MANAFACT)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val manufacturers = arrayListOf<Manufacturer>()
                    for (doc in task.result!!) {
                        val idManu = doc.id
                        val nameManu = doc.getString(Constant.SHOP_MANAFACT_NAME) ?: ""
                        val imageUrl = doc.getString(Constant.SHOP_MANAFACT_IMG) ?: ""
                        val manufacturer = Manufacturer(idManu, nameManu, imageUrl)
                        manufacturers.add(manufacturer)
                        if (manufacturers.size == task.result.size()) {
                            calback(manufacturers)
                        }
                    }
                } else {
                    show_toast("Chưa có nhà cung cấp")
                }
            }
    }


    fun getAllProduct(calback: (MutableList<Product>) -> Unit) {
        val productList = mutableListOf<Product>()
        val shopID = sharedPreferences.getString(Constant.SHOP_ID)
        firestore.collection(Constant.KEY_PRODUCT)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val id = document.id
                        val name = document[Constant.PRODUCT_NAME].toString()
                        val price = document[Constant.PRODUCT_PRICE].toString().toFloat()
                        val saleOff = document[Constant.PRODUCT_SALEOFF].toString().toFloat()
                        val idShop = document[Constant.PRODUCT_SHOP_ID].toString()
                        val count = document[Constant.PRODUCT_COUNT].toString().toInt()
                        val imageUrl = document[Constant.PRODUCT_IMG] as? MutableList<Any> ?: null
                        val idUser = document[Constant.PRODUCT_USER_ID].toString()
                        val star = document[Constant.PRODUCT_STAR].toString().toInt()
                        val timeUp = document[Constant.PRODUCT_TIME_UP].toString()
                        val describle = document[Constant.PRODUCT_DESCRIBLE].toString()
                        val manuID = document[Constant.PRODUCT_MANU_ID].toString()
                        val manuName = document[Constant.PRODUCT_MANU_NAME].toString()
                        val isSale = document[Constant.PRODUCT_IS_SALE].toString().toBoolean()
                        val productSizeList = mutableListOf<ProductSize>()
                        val productSizeData =
                            document[Constant.PRODUCT_SIZE] as? List<HashMap<String, Any>>
                                ?: emptyList()
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
                            describle
                        )
                        productList.add(product)

                    }
                    calback(productList)
                } else {
                    show_toast("Error: ${task.exception?.message}")
                }
            }
    }

    private fun loadRecyview() {

    }

    private fun initBanner() {
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
        listFragmentBanner.add(SlideBannerFragment.newInstance(0))
        listFragmentBanner.add(SlideBannerFragment.newInstance(1))
        listFragmentBanner.add(SlideBannerFragment.newInstance(2))
        addControl()
        autoSlide()

    }

}