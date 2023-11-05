package dong.duan.ecommerce.fragment.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dong.duan.ecommerce.adapter.AddressAdapter
import dong.duan.ecommerce.adapter.OnAddressSelect
import dong.duan.ecommerce.databinding.FragmentAdressBinding
import dong.duan.ecommerce.databinding.ItemShiptoAdressViewBinding
import dong.duan.ecommerce.dialog.DialogWaring
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Address
import dong.duan.ecommerce.utility.Constant
import dong.duan.ecommerce.utility.InitData

class FragmentAddress : BaseFragment<FragmentAdressBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAdressBinding.inflate(layoutInflater)
    var listadapter: AddressAdapter? = null
    override fun initView() {
       getData {
           initRcv(it!!)
       }
        binding.icAddAdress2.setOnClickListener {
            replaceFullViewFragment(FragmentEditAddress(null), true)
        }
    }
    fun getData(callback: (MutableList<Address>?) -> Unit) {
        val listAddress = mutableListOf<Address>()
        database.getReference(Constant.KEY_ADDRESS)
            .child(sharedPreferences.getString(Constant.USER_ID).toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        snapshot.children.forEach {
                            val data = it.value as? HashMap<*, *>
                            val address = Address().apply {
                                idAddress = it.key.toString()
                                remindName = data!!.get(Constant.ADR_REMIND_NAME).toString() ?: ""
                                receiverName = data.get(Constant.ADR_US_NAME).toString() ?: ""
                                location = data.get(Constant.ADR_ADDRESS).toString() ?: ""
                                phoneNumber = data.get(Constant.ADR_F_PHONE).toString() ?: ""
                                phoneNumber2 = data.get(Constant.ADR_S_PHONE).toString() ?: ""
                            }
                            listAddress.add(address)
                        }
                        callback(listAddress)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }
            })
    }

    private fun initRcv(listAddresses:MutableList<Address>) {
        listadapter = AddressAdapter(this.requireContext(),true, object :OnAddressSelect{
            override fun onSelected(address: Address, i: Int) {

            }
            override fun onEditData(address: Address) {
                updateAddress(address)
            }
            override fun onDelete(address: Address) {
                deleteAdress(address)
            }
        })
        listadapter?.setItems(listAddresses);
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }
        binding.rcvListAdress.adapter = listadapter
    }
    private fun updateAddress(address: Address) {
        replaceFullViewFragment(FragmentEditAddress(address), true)
    }
    private fun deleteAdress(address: Address) {
        database.getReference(Constant.KEY_ADDRESS)
            .child(sharedPreferences.getString(Constant.USER_ID).toString())
            .child(address.idAddress).removeValue().addOnCompleteListener {
                show_toast("Đã xóa thành công!")
            }
    }
}