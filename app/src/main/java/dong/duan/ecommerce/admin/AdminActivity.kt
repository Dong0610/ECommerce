package dong.duan.ecommerce.admin

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dong.duan.ecommerce.R
import dong.duan.ecommerce.activity.AddDataActivity
import dong.duan.ecommerce.activity.SplashActivity
import dong.duan.ecommerce.databinding.ActivitySalerBinding
import dong.duan.ecommerce.interfaces.OnAdminData
import dong.duan.ecommerce.interfaces.OnUpdateProductCount
import dong.duan.ecommerce.library.OnPutImageListener
import dong.duan.ecommerce.library.base.BaseActivity
import dong.duan.ecommerce.library.log
import dong.duan.ecommerce.library.putImgToStorage
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.library.uri_from_drawable
import dong.duan.ecommerce.model.Manufacturer
import dong.duan.ecommerce.model.Order
import dong.duan.ecommerce.utility.Constant
import dong.duan.ecommerce.utility.InitData
import java.util.Date

class AdminActivity : BaseActivity<ActivitySalerBinding>(), OnUpdateProductCount {
    override fun getViewBinding() = ActivitySalerBinding.inflate(layoutInflater)

    companion object{
      var listOrder = mutableListOf<Order>()
    }

    fun getOrder(callback: (MutableList<Order>) -> Unit) {
        firestore.collection(Constant.KEY_ORDER)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    val listData = mutableListOf<Order>()
                    task.result.forEach { item ->
                        val order = Order(item.id).apply {
                            shopId = item.getString(Constant.ORDER_SHOP_ID) ?: ""
                            userId = sharedPreferences.getString(Constant.USER_ID, "").toString()
                            productId = item.getString(Constant.ORDER_PR_ID) ?: ""
                            productName = item.getString(Constant.ORDER_PR_NAME) ?: ""
                            productCount = item.getLong(Constant.ORDER_PR_COUNT)?.toInt() ?: 0
                            productPrice =
                                item.getDouble(Constant.ORDER_PR_PRICE)?.toFloat() ?: 0.0f
                            productImg = item.getString(Constant.ORDRT_PR_IMG) ?: ""
                            orderStatus = item.getString(Constant.ORDER_STATUS) ?: ""
                            orderTime = (item.getDate(Constant.ORDER_TIME)!! as? Date)!!
                            orderStatusTime =
                                (item.getDate(Constant.ORDER_STATUS_TIME)!! as? Date)!!
                            item.getString(Constant.ODR_ADR_REMIND_NAME).toString()
                            receiverName = item.getString(Constant.ODR_ADR_US_NAME).toString()
                            location = item.getString(Constant.ODR_ADR_ADDRESS).toString()
                            phoneNumber = item.getString(Constant.ODR_ADR_F_PHONE).toString()
                            phoneNumber2 = item.getString(Constant.ODR_ADR_S_PHONE).toString()
                            cancelMess = item.getString(Constant.ORDER_CANCEL_VALUE).toString()
                            orderPrSize = item.get(Constant.ORDER_PR_SIZE).toString().toInt()
                        }
                        listData.add(order)
                    }
                    callback(listData)
                }
            }
            .addOnFailureListener { exception ->
                show_toast(exception.message.toString())
            }

    }

    override fun createView() {
        if (sharedPreferences.getBollean(Constant.KEY_SHOP_INIT, false) == false) {
            startActivity(Intent(this, InitShopDataActivity::class.java).apply {
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
            finish()
        }
        binding.txtShopName.setOnClickListener {
            startActivity(Intent(this, AddDataActivity::class.java))
        }
        loadData()

        getOrder {
            listOrder= it
        }
        openFragment(FragmentAdminHome())
        binding.bottomNavigation.setOnItemSelectedListener(OnItemSelectedBottomBar)


        binding.imgShop.setOnClickListener {
            putManafactural()
        }

        initProductCount()

    }

    private fun initProductCount() {

    }

    private fun putManafactural() {
        putShopToFirebase {
            it.forEach { item ->
                val hasmap = HashMap<String, String>()
                hasmap[Constant.SHOP_MANAFACT_NAME] = item.nameManu
                hasmap[Constant.SHOP_MANAFACT_IMG] = item.imageUrl
                firestore.collection(Constant.SHOP_MANAFACT).document(item.idManu)
                    .set(hasmap)
                    .addOnSuccessListener { taskId ->
                        log("putShop", "\nshopID: ${taskId}")
                    }
                    .addOnFailureListener {
                        log("putShop", "\nshopID: ${it.message}")
                    }
            }
        }
    }


    private fun putShopToFirebase(callback: (ArrayList<Manufacturer>) -> Unit) {
        val listManfactuar = arrayListOf<Manufacturer>()
        val totalItems = InitData.listPutData.size
        var itemCount = 0

        InitData.listPutData.forEach { item ->
            putImage(item.imageUrl) { imageUrl ->
                listManfactuar.add(Manufacturer(item.idManu, item.nameManu, imageUrl))
                itemCount++
                if (itemCount == totalItems) {
                    callback(listManfactuar)
                }
            }
        }
    }


    private fun putImage(resource: Int, callback: (String) -> Unit) {
        storage.getReference("Manufacturer")
            .putImgToStorage(
                uri_from_drawable(this@AdminActivity, resource)!!,
                object : OnPutImageListener {
                    override fun onComplete(url: String) {
                        callback(url)
                    }

                    override fun onFailure(mess: String) {
                    }
                })
    }


    private fun loadData() {
        Glide.with(this.applicationContext).load(sharedPreferences.getString(Constant.SHOP_IMG_URL))
            .into(binding.imgShop)
        binding.txtShopName.text = sharedPreferences.getString(Constant.SHOP_NAME)
        binding.txtSumCount.text =
            "Sản phẩm: " + sharedPreferences.getString(Constant.SHOP_COUTN_PR)


    }

    fun openFragment(fragment: Fragment) {
        binding.frameView.visibility = View.VISIBLE
        replaceFragment(fragment, R.id.frame_view, true)
    }

    private val OnItemSelectedBottomBar =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.icon_admin_home -> {
                    openFragment(FragmentAdminHome())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_order_bill -> {
                    openFragment(AdminOrderFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_add_sp -> {
                    openFragment(FragmentAddProduct())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_revenue -> {
                    openFragment(FragmentRevenue())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_account -> {
//                    openFragment(FragmentAccount())
                    // SignOut()
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        }

    private fun SignOut() {
        sharedPreferences.clear()
        sharedPreferences.putBollean(Constant.KEY_SHOP_INIT, false)
        sharedPreferences.putBollean(Constant.KEY_IS_SIGN_IN, false)
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

    override fun onValue(count: Int) {
        binding.txtSumCount.text = "Sản phẩm: ${count}"
    }


}