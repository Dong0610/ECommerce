package dong.duan.ecommerce.admin

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import dong.duan.ecommerce.databinding.ActivityInitShopBinding
import dong.duan.ecommerce.library.OnPutImageListener
import dong.duan.ecommerce.library.base.BaseActivity
import dong.duan.ecommerce.library.putImgToStorage
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.library.uri_to_string
import dong.duan.ecommerce.utility.Constant

class InitShopDataActivity : BaseActivity<ActivityInitShopBinding>() {
    override fun getViewBinding() = ActivityInitShopBinding.inflate(layoutInflater)
    private lateinit var shop_name: String
    private lateinit var address: String
    private lateinit var phone_num: String

    companion object {
        private const val REQUEST_CODE_PICK_IMAGES = 101
    }

    fun get_data() {
        shop_name = binding.edtShopName.text.toString()
        address = binding.edtShopAddress.text.toString()
        phone_num = binding.edtShopPhone.text.toString()
    }


    var stringImg: String? = ""
    var uriImg: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGES && resultCode == RESULT_OK) {
            data?.data?.let {
                uriImg = it
                loadImage(uriImg!!, binding.imgShop)
                stringImg = uri_to_string(uriImg!!)
            }
        }
    }

    override fun createView() {
        binding.imgShop.setOnClickListener {
            if (!AllPermissionsGranted(this)) {
                permissionLauncher.launch(permissions)
            } else {
                openImagePicker()
            }
        }
        binding.btnSaveData.setOnClickListener {
            saveData()
        }
        binding.edtUserID.text = sharedPreferences.getString(Constant.USER_ID)
        binding.edtUserName.text = sharedPreferences.getString(Constant.USER_NAME)

    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGES)
    }

    private fun saveData() {
        loadding.show()
        get_data()
        val hasmap = HashMap<String, Any>()
        val reference = storage.getReference("Img_Shop")
        reference.putImgToStorage(uriImg!!, object : OnPutImageListener {
            override fun onComplete(url: String) {
                hasmap[Constant.SHOP_NAME] = shop_name
                hasmap[Constant.SHOP_ADDRESS] = address
                hasmap[Constant.SHOP_PHONE] = phone_num
                hasmap[Constant.SHOP_IMG_URL] = url
                hasmap[Constant.SHOP_IMG] = stringImg!!
                hasmap[Constant.SHOP_US_ID] =
                    sharedPreferences.getString(Constant.USER_ID).toString()
                hasmap[Constant.SHOP_COUTN_PR] = "0"
                firestore.collection(Constant.KEY_SHOP)
                    .add(hasmap)
                    .addOnSuccessListener { task ->
                        sharedPreferences.edit {
                            putBoolean(Constant.KEY_SHOP_INIT, true)
                            putString(Constant.SHOP_ID, task.id)
                            putString(Constant.SHOP_NAME,shop_name)
                            putString(Constant.SHOP_ADDRESS, address)
                            putString(Constant.USER_ADDRESS, address)
                            putString(Constant.USER_PHONE, phone_num)
                            putString(Constant.SHOP_IMG_URL, url)
                            putString(Constant.SHOP_IMG, stringImg!!)
                            putString(Constant.SHOP_PHONE, phone_num)
                            putString(
                                Constant.SHOP_US_ID,
                                hasmap.get(Constant.SHOP_US_ID).toString()
                            )
                            putString(Constant.SHOP_COUTN_PR, "0")
                            startActivity(
                                Intent(
                                    this@InitShopDataActivity,
                                    AdminActivity::class.java
                                ).apply {
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                })
                            finish()
                        }
                    }
                    .addOnFailureListener { e ->
                        show_toast(e.message.toString())
                    }
                loadding.dismiss()
            }

            override fun onFailure(mess: String) {
                show_toast(mess)
            }
        })

    }
}