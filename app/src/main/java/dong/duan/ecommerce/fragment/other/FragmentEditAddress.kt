package dong.duan.ecommerce.fragment.other

import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentEditAddressBinding
import dong.duan.ecommerce.model.Address

class FragmentEditAddress(var address: Address) : BaseFragment<FragmentEditAddressBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditAddressBinding.inflate(layoutInflater)

    override fun initView() {
        binding.icBack2.setOnClickListener {
            closeFragment(this)
        }

        binding.edtFirstNum.setText(address.phoneNunber)
        binding.edtSecondNum.setText(address.phoneNumber2)
        binding.edtRemindName.setText(address.receiverName)
        binding.edtDetailLocation.setText(address.location)
        binding.edtReciveName.setText(address.receiverName)

        focustEvent()

    }

    private fun focustEvent() {
        binding.edtRemindName.setOnFocusChangeListener { view, b ->
            txtFocus(b,null,binding.llRemindName)
        }
        binding.edtReciveName.setOnFocusChangeListener { view, b ->
            txtFocus(b,null,binding.llReciveName)
        }
        binding.edtDetailLocation.setOnFocusChangeListener { view, b ->
            txtFocus(b,null,binding.llDetailLocate)
        }
        binding.edtFirstNum.setOnFocusChangeListener { view, b ->
            txtFocus(b,null,binding.llFirstNum)
        }
        binding.edtSecondNum.setOnFocusChangeListener { view, b ->
            txtFocus(b,null,binding.llSecondNum)
        }
    }
}