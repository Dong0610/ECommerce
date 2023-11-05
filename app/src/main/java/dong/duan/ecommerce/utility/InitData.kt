package dong.duan.ecommerce.utility

import dong.duan.ecommerce.R
import dong.duan.ecommerce.model.ManufacturerPut
import dong.duan.ecommerce.model.PaymendMethod

object InitData {

    val listPayment = mutableListOf(
        PaymendMethod("pay_medthod_1","Airpay",R.drawable.ic_pay_airpay),
        PaymendMethod("pay_medthod_2","Momo",R.drawable.ic_pay_momo),
        PaymendMethod("pay_medthod_3","Paypal",R.drawable.ic_paypal),
        PaymendMethod("pay_medthod_4","Shoppe Pay",R.drawable.ic_pay_shoppe),
        PaymendMethod("pay_medthod_5","VnPay",R.drawable.ic_pay_vnpay),
        PaymendMethod("pay_medthod_6","ZaloPay",R.drawable.ic_pay_zalo_pay)
    )

    val listPutData = mutableListOf(
        ManufacturerPut("manufacture_id_01","Nike",R.drawable.shop_img_nike),
        ManufacturerPut("manufacture_id_02","Converse",R.drawable.shop_img_converse),
        ManufacturerPut("manufacture_id_03","Dior",R.drawable.shop_img_dior),
        ManufacturerPut("manufacture_id_04","Domba",R.drawable.shop_img_domba),
        ManufacturerPut("manufacture_id_05","Fila",R.drawable.shop_img_fila),
        ManufacturerPut("manufacture_id_06","Gucci",R.drawable.shop_img_gucci),
        ManufacturerPut("manufacture_id_07","MLB",R.drawable.shop_img_mlb),
        ManufacturerPut("manufacture_id_08","New Balance",R.drawable.shop_img_new_balance),
        ManufacturerPut("manufacture_id_09","Puma",R.drawable.shop_img_pumas),
        ManufacturerPut("manufacture_id_10","Vans",R.drawable.shop_img_vans),
    )

}