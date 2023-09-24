package dong.duan.ecommerce.fragment.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.ItemSlideShowMainBinding

class SlideImageFragment : BaseFragment<ItemSlideShowMainBinding>() {
    companion object {
        private const val TAG = "SlideImageFragment"
        private const val POSITION: String = "page_home"
        private const val LIST_URL: String = "list_url"
        fun newInstance(position: Int, listUrl: String): SlideImageFragment {
            val fragment = SlideImageFragment()
            val bundle = Bundle()
            bundle.putInt(POSITION, position)
            bundle.putString(LIST_URL, listUrl)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var urlImage: String
    private var pagePosition = 0
    override fun initView() {
        pagePosition = requireArguments().getInt(POSITION)
        urlImage = requireArguments().getString(LIST_URL)!!
        setUpView()

    }

    fun setUpView() {
        Glide.with(this).load(urlImage).into(binding.imgView)
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ItemSlideShowMainBinding = ItemSlideShowMainBinding.inflate(inflater, container, false)


}