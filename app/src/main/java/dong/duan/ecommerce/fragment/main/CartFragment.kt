package dong.duan.ecommerce.fragment.main

import android.annotation.SuppressLint
import android.dongdong.kotlin_library.notification.show_notification
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dong.duan.ecommerce.R
import dong.duan.ecommerce.adapter.CardAdapter
import dong.duan.ecommerce.adapter.OnCardEvent
import dong.duan.ecommerce.databinding.FragmentCardBinding
import dong.duan.ecommerce.databinding.FragmentShipToBinding
import dong.duan.ecommerce.dialog.DialogSuccess
import dong.duan.ecommerce.fragment.other.ProductFragment
import dong.duan.ecommerce.library.data.MyDatabaseHelper
import dong.duan.ecommerce.library.formatTime
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.CardProduct
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.utility.Constant
import dong.duan.ecommerce.utility.OrderStatus
import java.util.Date

class CartFragment : BaseFragment<FragmentCardBinding>() {
    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentCardBinding.inflate(layoutInflater)

    var listProduct = mutableListOf<CardProduct>()

    override fun initView() {
        getCartValue {
            listProduct = it
            initRcv(listProduct)
        }
        onClick()
        if(listProductBuy.size==0){
            setBehaveButton(false)
        }
    }



    @SuppressLint("ResourceAsColor")
    fun setBehaveButton(value:Boolean= false){
        if(value){
            binding.btnCheckOut.isEnabled= true
            binding.btnCheckOut.setTextColor(R.color.white)
            binding.btnCheckOut.setBackgroundResource(R.drawable.bg_btn_account_end)
        }else{
            binding.btnCheckOut.isEnabled= false
            binding.btnCheckOut.setTextColor(R.color.textcolor2)
            binding.btnCheckOut.setBackgroundResource(R.drawable.bg_edt_account_dis)
        }
    }

    val listProductBuy= mutableListOf<CardProduct>()

    private fun initTotal(listProduct: MutableList<CardProduct>) {
        binding.llSumPay.visibility= View.VISIBLE
        binding.itemCount.text = listProduct.size.toString()
        binding.txtShipingCount.text = "0"
        binding.txtShipingVat.text = "0"

        var totalPrice = 0f
        listProduct.forEach { item ->
            totalPrice += item.nunCount * item.price
        }

        binding.txtSumCount.text = totalPrice.toString() + "$"
    }

    private fun onClick() {
        binding.btnCheckOut.setOnClickListener {
            buyProduct(listProductBuy)
        }
    }
    var curentShop = ""
    private fun buyProduct(listProduct: MutableList<CardProduct>) {
        curentShop = listProduct[0].productShopID
        var custom_noti=0
        listProduct.forEach { item ->
            val time= Date()
            custom_noti++
            val hasmap = HashMap<String, Any>()
            hasmap[Constant.ORDER_SHOP_ID] = item.productShopID
            hasmap[Constant.ORDER_USER_ID] = sharedPreferences.getString(Constant.USER_ID).toString()
            hasmap[Constant.ORDER_PR_ID] = item.productID
            hasmap[Constant.ORDER_PR_NAME] = item.productName
            hasmap[Constant.ORDER_PR_COUNT] = item.nunCount
            hasmap[Constant.ORDER_PR_PRICE] = item.price
            hasmap[Constant.ORDRT_PR_IMG]=item.prductImg
            hasmap[Constant.ORDER_STATUS]= OrderStatus.WAIT_PROCESS
            hasmap[Constant.ORDER_TIME] = time
            hasmap[Constant.ORDER_STATUS_TIME]= time
            firestore.collection(Constant.KEY_ORDER)
                .add(hasmap)
                .addOnCompleteListener{
                    val notiHasmap = HashMap<String,Any>()
                    notiHasmap[Constant.NOTI_VALUE] = "Đơn hàng được tạo thành công!"
                    notiHasmap[Constant.NOTI_TIME]="Đơn hàng đã được tạo với mã: ${it.result.id} vào lúc ${formatTime(time)}"
                    notiHasmap[Constant.NOTI_PR_ID]=item.productID

                    database.getReference(Constant.KEY_NOTIFICATION)
                        .child(hasmap.get(Constant.ORDER_USER_ID).toString())
                        .push()
                        .setValue(notiHasmap)
                        .addOnCompleteListener{
                            show_notification("Thông báo",notiHasmap.get(Constant.NOTI_TIME).toString(),R.mipmap.ic_sign_app,custom_noti)
                            database.getReference(Constant.KEY_CART)
                                .child(hasmap.get(Constant.ORDER_USER_ID).toString())
                                .child(item.productID).removeValue()

                            val index =
                                listProduct.indexOf(listProduct.find { it.productID == item.productID })
                            listProduct.removeAt(index)
                            setData(listProduct)

                        }.addOnFailureListener {
                            show_notification("Thông báo","Xảy ra lỗi trong quá trình tạo đơn hàng!",R.mipmap.ic_sign_app,custom_noti)
                        }
                }
                .addOnFailureListener { e->
                    show_toast(e.message.toString() )
                }
        }
        DialogSuccess(this.requireContext(), onBack = {

        }).show()
    }

    var cardProductAdapter: CardAdapter? = null
    private fun initRcv(listProduct: MutableList<CardProduct>) {
        cardProductAdapter = CardAdapter(object : OnCardEvent {
            override fun onLove(product: CardProduct) {
                if(!product.islove){
                    MyDatabaseHelper().insertFavorite(product.productID)
                    setToData(this@CartFragment.listProduct)
                    addToLove(product)
                }
                else{
                    MyDatabaseHelper().deleteFavorite(product.productID)
                    deleteLove(product.productID)
                    setToData(this@CartFragment.listProduct)
                }
            }
            override fun onDelete(product: CardProduct) {
                database.getReference(Constant.KEY_CART)
                    .child(sharedPreferences.getString(Constant.USER_ID).toString())
                    .child(product.productID)
                    .removeValue()

                val index =
                    this@CartFragment.listProduct.indexOf(this@CartFragment.listProduct.find { it.productID == product.productID })
                this@CartFragment.listProduct.removeAt(index)
                setData(this@CartFragment.listProduct)
            }

            override fun onUpdateCount(count: Int, id: String) {
                database.getReference(Constant.KEY_CART)
                    .child(sharedPreferences.getString(Constant.USER_ID).toString())
                    .child(id)
                    .child(Constant.CART_PRODUCT_COUNT)
                    .setValue(count)
            }

            override fun onSelect(idProcunt: String) {
                productById(idProcunt) {
                    replaceFullViewFragment(ProductFragment(it), true)
                }
            }

            override fun onBuy(product: CardProduct) {
                addDataToBuy(product)
            }
        })

        binding.recyclerView.adapter = cardProductAdapter
        setToData(listProduct)
    }

    private fun deleteLove(productID: String) {
        val listPrData= mutableListOf<CardProduct>()
        this.listProduct.forEach {
            listPrData.add(it.apply { islove=false })
        }
        database.getReference(Constant.KEY_LOVE)
            .child(sharedPreferences.getString(Constant.USER_ID).toString())
            .child(productID)
            .removeValue()
            .addOnCompleteListener {
                show_toast("Đã xóa khỏi danh sách yêu thích!")
                val listFavoriteID = MyDatabaseHelper().readFavorite()
                if(listFavoriteID.size!=0){
                    listFavoriteID.forEach{ s ->
                        val productf= listPrData.find { it.productID==s }
                        if (productf!=null){
                            val index= listPrData.indexOf(productf)
                            listPrData.get(index).islove=true
                        }
                    }
                }
                setData(listPrData)
            }
    }

    private fun setToData(listProduct: MutableList<CardProduct>) {
        var listPrData= listProduct
        val listFavoriteID = MyDatabaseHelper().readFavorite()

        if(listFavoriteID.size!=0){
            listFavoriteID.forEach{ s ->
                val productf= listPrData.find { it.productID==s }
                if (productf!=null){
                    val index= listPrData.indexOf(productf)
                    listPrData.get(index).islove=true
                }
            }
        }
        setData(listPrData)
    }



    private fun addToLove(product:CardProduct) {
        val hasMap = HashMap<String, Any>()
        hasMap[Constant.LOVE_US_ID] = sharedPreferences.getString(Constant.USER_ID).toString()
        hasMap[Constant.LOVE_USER_NAME] = sharedPreferences.getString(Constant.USER_NAME).toString()
        hasMap[Constant.LOVE_PRODUCT_ID] = product.productID
        hasMap[Constant.LOVE_PRODUCT_IMG] = product.prductImg
        hasMap[Constant.LOVE_PRODUCT_NAME] = product.productName
        hasMap[Constant.LOVE_PRODUCT_SHOP_ID] = product.productShopID
        hasMap[Constant.LOVE_PRODUCT_PRICE] =product.price
        hasMap[Constant.LOVE_PRODUCT_ISBUY] = true
        hasMap[Constant.LOVE_PRODUCT_COUNT] = 1
        database.getReference(Constant.KEY_LOVE)
            .child(sharedPreferences.getString(Constant.USER_ID).toString())
            .child(product.productID)
            .setValue(hasMap)
            .addOnCompleteListener {
                show_toast("Đã thêm vào danh sách yêu thích!")
            }.addOnFailureListener {
                show_toast(it.message.toString())
            }
    }

    private fun addDataToBuy(product: CardProduct) {
        if(listProductBuy.size==0){
            listProductBuy.add(product)
        }
        else{
            val indexPr= listProductBuy.indexOf(product)
            if(indexPr==-1){
                listProductBuy.add(product)
            }
            else{
                listProductBuy.removeAt(indexPr)
            }
        }
        if(listProductBuy.size==0){
            setBehaveButton(false)
        }
        else{
            setBehaveButton(true)
        }

        initTotal(listProductBuy)
    }


    fun productById(prID: String, calback: (Product) -> Unit) {
        firestore.collection(Constant.KEY_PRODUCT)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val id = document.id
                        val name = document[Constant.PRODUCT_NAME].toString()
                        val price = document[Constant.PRODUCT_PRICE].toString().toFloat()
                        val saleOff = document[Constant.PRODUCT_SALEOFF].toString().toFloat()
                        val idShop = document[Constant.PRODUCT_SHOP_ID].toString()
                        val count = document[Constant.PRODUCT_COUNT].toString().toInt()
                        val imageUrl = document[Constant.PRODUCT_IMG] as? MutableList<Any> ?: null
                        val idUser = document[Constant.PRODUCT_USER_ID].toString()
                        val star = document[Constant.PRODUCT_STAR].toString().toInt()
                        val timeUp = document[Constant.PRODUCT_TIME_UP].toString()
                        val describle = document[Constant.PRODUCT_DESCRIBLE].toString()
                        val manuID = document[Constant.PRODUCT_MANU_ID].toString()
                        val manuName = document[Constant.PRODUCT_MANU_NAME].toString()
                        val isSale = document[Constant.PRODUCT_IS_SALE].toString().toBoolean()
                        val productSizeList = mutableListOf<ProductSize>()
                        val productSizeData =
                            document[Constant.PRODUCT_SIZE] as? List<HashMap<String, Any>>
                                ?: emptyList()
                        for (sizeMap in productSizeData) {
                            val size = sizeMap["size"]?.toString() ?: ""
                            val productSize = ProductSize(size)
                            productSizeList.add(productSize)
                        }

                        val product = Product(
                            id,
                            name,
                            price,
                            saleOff,
                            idShop,
                            count,
                            imageUrl,
                            idUser,
                            star,
                            timeUp,
                            isSale,
                            productSizeList,
                            manuID,
                            manuName,
                            describle
                        )
                        if (product.id == prID) {
                            calback(product)
                            break
                        }
                    }
                } else {
                    show_toast("Error: ${task.exception?.message}")
                }
            }
    }

    private fun setData(listProduct: MutableList<CardProduct>) {
        cardProductAdapter?.setItems(listProduct)
    }

    fun getCartValue(callback: (MutableList<CardProduct>) -> Unit) {
        val userId = sharedPreferences.getString(Constant.USER_ID, "")
        if (userId.isNullOrEmpty()) {
            return
        }
        val cartProducts: MutableList<CardProduct> = mutableListOf()
        database.getReference(Constant.KEY_CART)
            .child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (childSnapshot in dataSnapshot.children) {
                            val cartProduct = childSnapshot.value as? HashMap<*, *>
                            if (cartProduct != null) {
                                val userName = cartProduct[Constant.CART_USER_NAME].toString()
                                val nunCount = cartProduct[Constant.CART_PRODUCT_COUNT] as Long
                                val productId = cartProduct[Constant.CART_PRODUCT_ID].toString()
                                val productShopID =
                                    cartProduct[Constant.CART_PRODUCT_SHOP_ID].toString()
                                val productName = cartProduct[Constant.CART_PRODUCT_NAME].toString()
                                val productImg = cartProduct[Constant.CART_PRODUCT_IMG].toString()
                                val price = cartProduct[Constant.CART_PRODUCT_PRICE] as Long

                                val cardProduct = CardProduct(
                                    false,
                                    userId,
                                    userName,
                                    nunCount.toInt(),
                                    productId,
                                    productShopID,
                                    productName,
                                    productImg,
                                    price.toFloat()
                                )

                                cartProducts.add(cardProduct)
                            }
                        }
                    }
                    callback(cartProducts)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    show_toast(databaseError.message)
                }
            })
    }

}

class PayAllProduct(var sumMoney: Float) : BaseFragment<FragmentShipToBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShipToBinding.inflate(layoutInflater)

    var Value_Start = 1
    override fun initView() {

        caseView(1)

        binding.btnContinue.setOnClickListener {
            Value_Start += 1
            if (Value_Start > 3) {
                DialogSuccess(this.requireContext(),
                    {
                        this.closeFragment(this)
                    }
                ).show()
            } else {
                caseView(Value_Start)
            }
        }

    }

    fun caseView(value: Int) {
        when (value) {
            1 -> {
                binding.rcvListAdress.visibility = View.VISIBLE
                binding.llChoosePayment.visibility = View.GONE
                binding.rcvListCard.visibility = View.GONE
                binding.icAddAdress.visibility = View.VISIBLE
                binding.btnContinue.text = requireContext().getString(R.string.next)
            }

            2 -> {
                binding.rcvListAdress.visibility = View.GONE
                binding.llChoosePayment.visibility = View.VISIBLE
                binding.rcvListCard.visibility = View.GONE
                binding.icAddAdress.visibility = View.GONE
                binding.btnContinue.text = requireContext().getString(R.string.next)
            }

            3 -> {
                binding.rcvListAdress.visibility = View.GONE
                binding.llChoosePayment.visibility = View.GONE
                binding.rcvListCard.visibility = View.VISIBLE
                binding.icAddAdress.visibility = View.VISIBLE
                binding.btnContinue.text = "$$sumMoney"
            }
        }
    }
}












