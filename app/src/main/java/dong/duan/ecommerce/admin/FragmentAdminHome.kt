package dong.duan.ecommerce.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.adapter.admin.AdminProductAdapter
import dong.duan.ecommerce.adapter.admin.OnCardAdminEvent
import dong.duan.ecommerce.databinding.FragmentHomeAdminBinding
import dong.duan.ecommerce.fragment.other.ProductFragment
import dong.duan.ecommerce.interfaces.OnUpdateProductCount
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.utility.Constant

class FragmentAdminHome : BaseFragment<FragmentHomeAdminBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeAdminBinding.inflate(layoutInflater)


    private var onUpdateProductCount: OnUpdateProductCount? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnUpdateProductCount) {
            onUpdateProductCount = context
        } else {
            throw IllegalArgumentException("The activity must implement OnUpdateProductCount")
        }
    }

    private fun updateValue(newValue: Int) {
        if (onUpdateProductCount != null) {
            onUpdateProductCount!!.onValue(newValue)
        }
    }


    lateinit var productAdapter: AdminProductAdapter

    var listProduct= mutableListOf<Product>()

    override fun initView() {
        productAdapter = AdminProductAdapter(object : OnCardAdminEvent {
            override fun editProduct(product: Product) {
                addFragment(FragmentEditProduct(product))
            }

            override fun onDelete(product: Product) {
                firestore.collection(Constant.KEY_PRODUCT)
                    .document(product.id)
                    .update(Constant.PRODUCT_IS_SALE,false)
                    .addOnCompleteListener {
                        show_toast("Đã ngưng bán sản phẩm")
                    }
                    .addOnFailureListener { e->
                        show_toast(e.message.toString())
                    }
            }
            override fun onDetail(product: Product) {
                addFragment(ProductFragment(product,true))
            }
        })


        FragmentEditProduct.updateProduct(object :FragmentEditProduct.OnUpdateValue{
            override fun OnUpdate(product: Product) {
                val index= listProduct.indexOf(listProduct.find { it.id==product.id })
                listProduct.set(index,product)
                productAdapter.setItems(listProduct)
            }
        })

        getAllProduct { list ->
            listProduct= list
            binding.rcvListSp.adapter = productAdapter
            productAdapter.setItems(listProduct)
            updateValue(list.size)
            binding.rcvListSp
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
                        val star = document[Constant.PRODUCT_STAR].toString().toFloat()
                        val timeUp = document[Constant.PRODUCT_TIME_UP].toString()
                        val describle = document[Constant.PRODUCT_DESCRIBLE].toString()
                        val manuID = document[Constant.PRODUCT_MANU_NAME].toString()
                        val manuName = document[Constant.PRODUCT_TIME_UP].toString()
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
                            describle,
                            style,
                            evaluator
                        )
                        if (idShop.equals(shopID)) {
                            productList.add(product)
                        }
                    }
                    calback(productList)
                } else {
                    show_toast("Error: ${task.exception?.message}")
                }
            }

    }
}