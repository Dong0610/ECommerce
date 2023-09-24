package dong.duan.ecommerce.dialog

import android.content.Context
import dong.duan.ecommerce.databinding.DialogAppSuccessBinding
import dong.duan.ecommerce.library.base.BaseDialog

class DialogSuccess(context: Context,onBack:()->Unit):BaseDialog(context) {

    init {
        val binding= DialogAppSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
        binding.btnBackOrder.setOnClickListener {
            onBack()
            dismiss()
        }
    }
}