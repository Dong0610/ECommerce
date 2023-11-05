package dong.duan.ecommerce.fragment.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.ItemSlideShowMainBinding

class SlideBannerFragment : BaseFragment<ItemSlideShowMainBinding>() {
    companion object {
        private const val TAG = "FragmentItemPageAI"
        private const val POSITION_PAGE_HOME_AI: String = "page_home_pos"
        fun newInstance(position: Int): SlideBannerFragment {
            val fragment = SlideBannerFragment()
            val bundle = Bundle()
            bundle.putInt(POSITION_PAGE_HOME_AI, position)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var pagePosition = 0

    override fun initView() {
        pagePosition = requireArguments().getInt(POSITION_PAGE_HOME_AI)
        setUpView()

    }

    private fun setUpView(){
        when(pagePosition){
            0 -> {
                binding.imgView.setImageResource( R.drawable.img_flash_sale)

            }
            1 -> {
                binding.imgView.setImageResource( R.drawable.img_slide_1)


            }
            2 -> {
                binding.imgView.setImageResource( R.drawable.img_slide_2)
            }
        }
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemSlideShowMainBinding  = ItemSlideShowMainBinding.inflate(inflater, container, false)


}