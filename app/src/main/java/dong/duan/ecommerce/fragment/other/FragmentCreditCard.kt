package dong.duan.ecommerce.fragment.other

import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentCreditCardBinding
import dong.duan.ecommerce.databinding.ItemListCreditCardBinding
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.model.CreditCard

class FragmentCreditCard : BaseFragment<FragmentCreditCardBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCreditCardBinding.inflate(layoutInflater)

    var adapterCard: GenericAdapter<CreditCard,ItemListCreditCardBinding>?= null
    override fun initView() {

        binding.icAddAdress.setOnClickListener {
            replaceFullViewFragment(FragmentCardManager(null),true)
        }
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }
        binding.rcvListCard.adapter=adapterCard
    }
}