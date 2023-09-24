package dong.duan.ecommerce.fragment.other

import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.R
import dong.duan.ecommerce.adapter.HomeProductAdapter
import dong.duan.ecommerce.adapter.MyFavoriteAdapter
import dong.duan.ecommerce.adapter.OnFavoriteSelected
import dong.duan.ecommerce.adapter.OnProductSelected
import dong.duan.ecommerce.databinding.FavoriteFragmentBinding
import dong.duan.ecommerce.model.Product

class FavoriteFragment :BaseFragment<FavoriteFragmentBinding>() {

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FavoriteFragmentBinding.inflate(inflater,container,false)

    val listProduct= mutableListOf(
        Product("1","FS - Nike Air Max 270 React...",100f,24f,"",100,
          mutableListOf( R.drawable.img_product_flashale_1),"",3),
        Product("2","FS - Nike Air Max 270 React...",100f,24f,"",100,
             mutableListOf(R.drawable.img_product_flashale_2),"",4),
        Product("3","FS - Nike Air Max 270 React...",100f,24f,"",100,
            mutableListOf( R.drawable.img_product_flashale_3),"",5),
        Product("4","FS - Nike Air Max 270 React...",100f,24f,"",100,
            mutableListOf( R.drawable.img_product_flashale_4),"",4),
        Product("5","FS - Nike Air Max 270 React...",100f,24f,"",100,
           mutableListOf(  R.drawable.img_product_flashale_5),"",3),
        Product("6","FS - Nike Air Max 270 React...",100f,24f,"",100,
            mutableListOf( R.drawable.img_product_flashale_6),"",5)
    )


    override fun initView() {
       initRcv()
    }

    private fun initRcv() {
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }
        val favoriteAdapter= MyFavoriteAdapter(requireContext(), object : OnFavoriteSelected {

            override fun onItemSelect(product: Product) {

            }

            override fun onDeletet(product: Product) {

            }

        })
        binding.rcvFavorite.adapter=favoriteAdapter
        favoriteAdapter.setItems(listProduct)
    }

}