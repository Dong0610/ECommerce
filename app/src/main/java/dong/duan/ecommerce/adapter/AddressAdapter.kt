package dong.duan.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobiai.base.basecode.adapter.BaseAdapter
import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.ItemShiptoAdressViewBinding
import dong.duan.ecommerce.dialog.DialogWaring
import dong.duan.ecommerce.fragment.other.FragmentEditAddress
import dong.duan.ecommerce.model.Address

class AddressAdapter (var context:Context,var type:Boolean =false,var onAddressSelect: OnAddressSelect):BaseAdapter<Address,ItemShiptoAdressViewBinding>() {
    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    )=ItemShiptoAdressViewBinding .inflate(inflater,parent,false)

    override fun bind(itembinding: ItemShiptoAdressViewBinding, adress: Address, position: Int) {
        itembinding.icDelete.setOnClickListener {
            DialogWaring("Bạn có chắc muốn xóa địa chỉ này không!", context) {
                onAddressSelect.onDelete(adress)
            }.show()
        }
        if(type){
            itembinding.icDelete.visibility= View.VISIBLE
        }
        else{
            itembinding.icDelete.visibility= View.GONE
        }
        if(position==currentPosition){
            itembinding.root.setBackgroundResource(R.drawable.bg_edt_account_end)
        }
        else{
            itembinding.root.setBackgroundResource(R.drawable.bg_edt_account_dis)
        }
        itembinding.txtAdrName.text= adress.remindName
        itembinding.txtAdrDetail.text = adress.location
        itembinding.txtNumPhone.text = adress.phoneNumber
        itembinding.btnEditAddress.setOnClickListener {
            onAddressSelect.onEditData(adress);
        }
        itembinding.root.setOnClickListener {
            onAddressSelect.onSelected(adress,position)
        }
    }

    fun  setItem(position:Int){
        notifyItemChanged(position)
        notifyItemChanged(currentPosition)
        currentPosition=position;
    }

}

interface  OnAddressSelect{
    fun onSelected(address: Address,i:Int)
    fun onEditData(address: Address)
    fun onDelete(address: Address)
}