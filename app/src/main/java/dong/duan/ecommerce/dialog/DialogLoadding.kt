package dong.duan.ecommerce.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.DialogAppLoaddingBinding
import dong.duan.ecommerce.databinding.DialogAppSuccessBinding
import dong.duan.ecommerce.library.base.BaseDialog
import dong.duan.ecommerce.library.rotate

class DialogLoadding (context: Context): BaseDialog(context) {

    init {
        val view= LayoutInflater.from(context).inflate(R.layout.dialog_app_loadding,null)
        setContentView(view)
        val imgae= view.findViewById<ImageView>(R.id.img_loading)
        rotate(imgae,2500)
        setCancelable(false)
    }
}