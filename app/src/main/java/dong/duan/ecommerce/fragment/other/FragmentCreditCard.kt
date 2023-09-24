package dong.duan.ecommerce.fragment.other

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentCreditCardBinding
import dong.duan.ecommerce.databinding.ItemListCreditCardBinding
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.model.CreditCard
import dong.duan.ecommerce.utility.InitData

class FragmentCreditCard : BaseFragment<FragmentCreditCardBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCreditCardBinding.inflate(layoutInflater)

    var adapterCard: GenericAdapter<CreditCard,ItemListCreditCardBinding>?= null
    override fun initView() {
        adapterCard = GenericAdapter(InitData.listcrediCard,ItemListCreditCardBinding::inflate){
            itembinding, creditCard, i ->
            itembinding.llColor.setBackgroundColor(creditCard.color)
            itembinding.txtCardName.text= creditCard.carName
            itembinding.txtNumCard.text= creditCard.cardNumber
            Glide.with(requireContext()).load(creditCard.imgCard).into(itembinding.imgBankingApp)
            itembinding.txtEndate.text=creditCard.endDate
            itembinding.txtHolder.text= creditCard.holder
            itembinding.root.setOnClickListener {
                replaceFullViewFragment(FragmentCardManager(creditCard),true)
            }

        }
        binding.icAddAdress.setOnClickListener {
            replaceFullViewFragment(FragmentCardManager(null),true)
        }
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }
        binding.rcvListCard.adapter=adapterCard
    }
}