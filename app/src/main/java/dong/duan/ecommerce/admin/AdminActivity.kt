package dong.duan.ecommerce.admin

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dong.duan.ecommerce.R
import dong.duan.ecommerce.activity.AddDataActivity
import dong.duan.ecommerce.databinding.ActivitySalerBinding
import dong.duan.ecommerce.interfaces.OnUpdateProductCount
import dong.duan.ecommerce.library.base.BaseActivity
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Order
import dong.duan.ecommerce.utility.Constant
import java.util.Date

class AdminActivity : BaseActivity<ActivitySalerBinding>(), OnUpdateProductCount {
    override fun getViewBinding() = ActivitySalerBinding.inflate(layoutInflater)

    companion object {
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
            listOrder = it
        }
        openFragment(FragmentAdminHome(),true)
        binding.bottomNavigation.setOnItemSelectedListener(OnItemSelectedBottomBar)


    }





    private fun loadData() {
        Glide.with(this.applicationContext).load(sharedPreferences.getString(Constant.SHOP_IMG_URL))
            .into(binding.imgShop)
        binding.txtShopName.text = sharedPreferences.getString(Constant.SHOP_NAME)
        binding.txtSumCount.text =
            "Sản phẩm: " + sharedPreferences.getString(Constant.SHOP_COUTN_PR)


    }

    fun openFragment(fragment: Fragment, value: Boolean = false) {
        if (value) {
            binding.frameView1.visibility = View.VISIBLE
            binding.frameView2.visibility = View.GONE
            replaceFragment(fragment, R.id.frame_view1, true)
        } else {
            binding.frameView1.visibility = View.GONE
            binding.frameView2.visibility = View.VISIBLE
            replaceFragment(fragment, R.id.frame_view2, true)
        }
    }

    private val OnItemSelectedBottomBar =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.icon_admin_home -> {
                    openFragment(FragmentAdminHome(),true)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_order_bill -> {
                    openFragment(AdminOrderFragment(),true)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_add_sp -> {
                    openFragment(FragmentAddProduct(),true)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_revenue -> {
                    openFragment(FragmentRevenue(),true)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_account -> {
                    openFragment(AdminInfoFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        }


    override fun onValue(count: Int) {
        binding.txtSumCount.text = "Sản phẩm: ${count}"
    }


}