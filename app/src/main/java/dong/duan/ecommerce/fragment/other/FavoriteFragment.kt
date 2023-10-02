package dong.duan.ecommerce.fragment.other

import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.adapter.user.MyFavoriteAdapter
import dong.duan.ecommerce.adapter.user.OnFavoriteSelected
import dong.duan.ecommerce.databinding.FavoriteFragmentBinding
import dong.duan.ecommerce.fragment.main.HomeFragment
import dong.duan.ecommerce.library.data.MyDatabaseHelper
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.utility.Constant

class FavoriteFragment : BaseFragment<FavoriteFragmentBinding>() {

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FavoriteFragmentBinding.inflate(inflater, container, false)


    override fun initView() {
        getAllProduct {
            initRcv(it)
        }

    }


    fun getAllProduct(calback: (MutableList<Product>) -> Unit) {
        val productList = mutableListOf<Product>()

        val listLoveData = MyDatabaseHelper().readFavorite()
        HomeFragment.listFetchAllData.forEach { product ->
            val isLove = listLoveData.indexOf(listLoveData.find { it == product.id })
            if (isLove != -1) {
                productList.add(product)
            }
        }
        calback(productList)
    }

    var favoriteAdapter: MyFavoriteAdapter? = null

    private fun initRcv(listProduct: MutableList<Product>) {
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }
         favoriteAdapter = MyFavoriteAdapter(requireContext(), object : OnFavoriteSelected {
            override fun onItemSelect(product: Product) {
                replaceFullViewFragment(ProductFragment(product, false), true)
            }

            override fun onDelete(product: Product) {
                MyDatabaseHelper().deleteFavorite(product.id)
                deleteLove(product.id){
                    setData(it)
                }
            }

        })
        binding.rcvFavorite.adapter = favoriteAdapter
        setData(listProduct)
    }

    private fun deleteLove(productID: String,calback: (MutableList<Product>) -> Unit) {
        val productList = mutableListOf<Product>()
        database.getReference(Constant.KEY_LOVE)
            .child(sharedPreferences.getString(Constant.USER_ID).toString())
            .child(productID)
            .removeValue()
            .addOnCompleteListener {
                show_toast("Đã xóa khỏi danh sách yêu thích!")
                val listLoveData = MyDatabaseHelper().readFavorite()
                HomeFragment.listFetchAllData.forEach { product ->
                    val isLove = listLoveData.indexOf(listLoveData.find { it == product.id })
                    if (isLove != -1) {
                        productList.add(product)
                    }
                }
                calback(productList)
            }
    }

    private fun setData(listProduct: MutableList<Product>) {
        favoriteAdapter?.setItems(listProduct)
    }

}