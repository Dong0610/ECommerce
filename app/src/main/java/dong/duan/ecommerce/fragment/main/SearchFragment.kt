package dong.duan.ecommerce.fragment.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.adapter.user.HomeProductAdapter
import dong.duan.ecommerce.adapter.user.OnProductSelected
import dong.duan.ecommerce.adapter.user.OnSearchItem
import dong.duan.ecommerce.adapter.user.SearchAdapter
import dong.duan.ecommerce.databinding.FragmentExploxeBinding
import dong.duan.ecommerce.databinding.ItemListCategoryBinding
import dong.duan.ecommerce.fragment.other.ProductFragment
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.library.data.MyDatabaseHelper
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Manufacturer
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.utility.Constant

class SearchFragment(var manufacturer: Manufacturer? = null) :
    BaseFragment<FragmentExploxeBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentExploxeBinding.inflate(layoutInflater, container, false)

    var manafactutarAdapter: GenericAdapter<Manufacturer, ItemListCategoryBinding>? = null
    lateinit var currentSrAdapter: SearchAdapter
    override fun initView() {
        loadData()
        getAllProduct {
            listProductAll = it
        }
        if (manufacturer != null) {
            getAllProduct {
                listProductAll = it
                searchByManufact(manufacturer!!)
            }
        }

        binding.icSearch.setOnClickListener {
            val dataCurrenSreach = binding.edtSearchData.text.toString()
            MyDatabaseHelper().insertCurrent(dataCurrenSreach)
            loadData()
            searchProduct(dataCurrenSreach)
        }
        getManuFacturer {
            manafactutarAdapter =
                GenericAdapter(it, ItemListCategoryBinding::inflate) { itembinding, category, i ->
                    Glide.with(this.requireContext()).load(category.imageUrl)
                        .into(itembinding.imgView)
                    itembinding.txtName.text = category.nameManu
                    itembinding.root.setOnClickListener {
                        searchByManufact(category)
                    }
                }
            binding.rcvManufact.adapter = manafactutarAdapter
        }

        binding.icBack.setOnClickListener {
            binding.llResult.visibility = View.GONE
        }


    }

    private fun searchByManufact(category: Manufacturer) {
        binding.llResult.visibility = View.VISIBLE
        binding.txtResult.setText("Kết quả cho ${category.nameManu}")
        val listResult = mutableListOf<Product>()
        listProductAll.forEach { product: Product ->
            if (product.idManufacturer.trim() == category.idManu.trim()) {
                listResult.add(product)
            }
        }
        setResultToView(listResult)
    }

    private fun searchProduct(key: String) {
        binding.llResult.visibility = View.VISIBLE
        val listResult = mutableListOf<Product>()
        binding.txtResult.setText("Kết quả cho $key")
        val keyLowerCase = key.trim().replace(" ", "").lowercase()

        listProductAll.forEach { product: Product ->
            val productNameLowerCase = product.name.trim().replace(" ", "").lowercase()
            if (productNameLowerCase.contains(keyLowerCase)) {
                listResult.add(product)
            }
        }
        setResultToView(listResult)
    }

    private fun setResultToView(listResult: MutableList<Product>) {
        val productAdapter = HomeProductAdapter(requireContext(), object : OnProductSelected {
            override fun onItemSelect(product: Product) {
                replaceFullViewFragment(ProductFragment(product), true)
            }
        })
        binding.rcvResultSreach.adapter = productAdapter
        productAdapter.setItems(listResult)

    }

    var listProductAll = mutableListOf<Product>()
    fun getAllProduct(calback: (MutableList<Product>) -> Unit) {
        val productList = mutableListOf<Product>()
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


    var listCurrent = ArrayList<String>()
    private fun loadData() {
        currentSrAdapter = SearchAdapter(object : OnSearchItem {
            override fun onSelectItem(value: String) {
                searchProduct(value)
            }
        })
        listCurrent.clear()
        listCurrent = MyDatabaseHelper().readCrSearch()
        binding.rcvCurrent.adapter = currentSrAdapter
        listCurrent.add(0,"Tìm kiếm gần đây")
        currentSrAdapter.setItems(listCurrent)
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
}