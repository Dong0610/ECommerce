package dong.duan.ecommerce.fragment.other

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentCardManagerBinding
import dong.duan.ecommerce.model.CreditCard


class FragmentCardManager(var creditCard:CreditCard?) :BaseFragment<FragmentCardManagerBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCardManagerBinding.inflate(layoutInflater)

    override fun initView() {
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }
        if(creditCard!=null){
            binding.edtCardHolder.setText(creditCard!!.holder)
            binding.edtBankName.setText(creditCard!!.carName)
            binding.edtCardNum.setText(creditCard!!.cardNumber)
            binding.edtExpericeDate.setText(creditCard!!.endDate)
            binding.edtSecurityCode.setText(creditCard!!.securutyCode)
            Glide.with(requireContext()).load(creditCard!!.imgCard).into(binding.imgCard)
            binding.edtCardHolder.setText(creditCard!!.holder)
        }

        initFocus()
    }

    private fun initFocus() {
        binding.edtBankName.setOnFocusChangeListener { view, b ->
           txtFocus(b,null, binding.llBankName)
        }
        binding.edtCardHolder.setOnFocusChangeListener { view, b ->
            txtFocus(b,null, binding.llCardHolder)
        }
        binding.edtExpericeDate.setOnFocusChangeListener { view, b ->
            txtFocus(b,null, binding.llExpericeDate)
        }
        binding.edtSecurityCode.setOnFocusChangeListener { view, b ->
            txtFocus(b,null, binding.llSecurityCode)
        }
        binding.edtCardNum.setOnFocusChangeListener { view, b ->
            txtFocus(b,null, binding.llCardNum)
        }
    }
}