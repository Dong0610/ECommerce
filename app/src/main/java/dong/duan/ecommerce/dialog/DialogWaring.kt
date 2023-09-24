package dong.duan.ecommerce.dialog

import android.content.Context
import android.view.View
import dong.duan.ecommerce.databinding.DialogAppWaringBinding
import dong.duan.ecommerce.library.AppContext
import dong.duan.ecommerce.library.base.BaseDialog

class DialogWaring(mess:String?=null,context: Context, onBack:()->Unit):BaseDialog(context) {
    init {
        val binding= DialogAppWaringBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
        binding.txtMess.visibility= if( mess!=null && !mess.equals("")) View.VISIBLE else View.GONE
        binding.txtMess.text= mess
        binding.btnBackOrder.setOnClickListener {
            onBack()
            dismiss()
        }
        binding.btnBackOrder2.setOnClickListener {
            dismiss()
        }
    }
}