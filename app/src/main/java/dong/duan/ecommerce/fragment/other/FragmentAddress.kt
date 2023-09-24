package dong.duan.ecommerce.fragment.other

import android.view.LayoutInflater
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentAdressBinding
import dong.duan.ecommerce.databinding.ItemShiptoAdressViewBinding
import dong.duan.ecommerce.dialog.DialogWaring
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.model.Address
import dong.duan.ecommerce.utility.InitData

class FragmentAddress : BaseFragment<FragmentAdressBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )=FragmentAdressBinding.inflate(layoutInflater)

    var listadapter: GenericAdapter<Address,ItemShiptoAdressViewBinding>?= null
    override fun initView() {
        listadapter= GenericAdapter(InitData.listAddresses,ItemShiptoAdressViewBinding::inflate){
            itembinding, adress, i ->
            itembinding.icDelete.setOnClickListener {
                DialogWaring("Are you sure wanna delete address",requireContext(),{
                    deleteAdress(adress)
                }).show()
            }
            itembinding.txtAdrDetail.text=adress.location
            itembinding.txtNumPhone.text=adress.phoneNunber
            itembinding.btnEditAddress.setOnClickListener {
                updateAddress(adress)
            }
        }
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }
        binding.rcvListAdress.adapter=listadapter
    }

    private fun updateAddress(address: Address) {
        replaceFullViewFragment(FragmentEditAddress(address),true)
    }

    private fun deleteAdress(address: Address) {

    }
}