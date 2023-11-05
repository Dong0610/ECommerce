package dong.duan.ecommerce.admin

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.databinding.FragmentShopInfoBinding
import dong.duan.ecommerce.library.OnPutImageListener
import dong.duan.ecommerce.library.putImgToStorage
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.utility.Constant

class FragmentShopInfo : BaseFragment<FragmentShopInfoBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShopInfoBinding.inflate(layoutInflater)

    override fun initView() {
        binding.txtShopId.setText(sharedPreferences.getString(Constant.SHOP_ID))
        binding.edtShopAddress.setText(sharedPreferences.getString(Constant.SHOP_ADDRESS))
        binding.edtShopPhone.setText(sharedPreferences.getString(Constant.SHOP_PHONE))
        binding.edtShopName.setText(sharedPreferences.getString(Constant.SHOP_NAME))
        Glide.with(binding.root).load(sharedPreferences.getString(Constant.SHOP_IMG_URL).toString())
            .into(binding.imgShop)
        binding.icBack.setOnClickListener{
            closeFragment(this)
        }

        binding.btnSaveName.setOnClickListener {
            val text = binding.edtShopName.text.toString()
            firestore.collection(Constant.KEY_SHOP)
                .document(sharedPreferences.getString(Constant.SHOP_ID).toString())
                .update(Constant.SHOP_NAME, text)
                .addOnCompleteListener { task ->
                    sharedPreferences.putString(Constant.SHOP_NAME, text.toString())
                    show_toast("Thay đổi sẽ được thực hiện trong lần đăng nhập tới!")
                }.addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }
        binding.btnSaveAddress.setOnClickListener {
            val text = binding.edtShopAddress.text.toString()
            firestore.collection(Constant.KEY_SHOP)
                .document(sharedPreferences.getString(Constant.SHOP_ID).toString())
                .update(Constant.SHOP_ADDRESS, text)
                .addOnCompleteListener { task ->
                    sharedPreferences.putString(Constant.SHOP_ADDRESS, text.toString())
                    show_toast("Thay đổi sẽ được thực hiện trong lần đăng nhập tới!")
                }.addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }

        binding.btnSavePhone.setOnClickListener {
            val text = binding.edtShopName.text.toString()
            firestore.collection(Constant.KEY_SHOP)
                .document(sharedPreferences.getString(Constant.SHOP_ID).toString())
                .update(Constant.SHOP_PHONE, text)
                .addOnCompleteListener { task ->
                    sharedPreferences.putString(Constant.SHOP_PHONE, text.toString())
                    show_toast("Thay đổi sẽ được thực hiện trong lần đăng nhập tới!")
                }.addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }

        binding.btnAddImg.setOnClickListener {
            chooseImage()
        }
        binding.btnSaveImg.setOnClickListener {
            if(imageUri!=null){
                putImage(imageUri!!){
                    firestore.collection(Constant.KEY_SHOP)
                        .document(sharedPreferences.getString(Constant.SHOP_ID).toString())
                        .update(Constant.SHOP_IMG_URL, it)
                        .addOnCompleteListener { task ->
                            sharedPreferences.putString(Constant.SHOP_IMG_URL, it)
                            show_toast("Thay đổi sẽ được thực hiện trong lần đăng nhập tới!")
                        }.addOnFailureListener {
                            show_toast(it.message.toString())
                        }
                }
            }
            else{
                show_toast("Ảnh mới chưa được chọn!")
            }
        }

    }

    private fun putImage(resource: Uri, callback: (String) -> Unit) {
        storage.getReference("ProductImage")
            .putImgToStorage(resource, object :
                OnPutImageListener {
                override fun onComplete(url: String) {
                    callback(url)
                }

                override fun onFailure(mess: String) {
                }
            })
    }


    val permissions =
        arrayOf(
            Manifest.permission.CAMERA, if (isAPI33OrHigher()) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, Manifest.permission.POST_NOTIFICATIONS
        )

    val permissionsForUsedApp =
        arrayOf(
            Manifest.permission.CAMERA, if (isAPI33OrHigher()) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            }
        )
    var imageUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FragmentAddProduct.REQUEST_CODE_PICK_IMAGES && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                imageUri = it
                binding.imgShop.setImageURI(it)
            }
        }
    }

    private var permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            val allPermissionGranted = permissionsForUsedApp.all {
                ContextCompat.checkSelfPermission(
                    this.requireContext(),
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }

            if (!allPermissionGranted) {
                goToSetting(this.requireContext())
            }
        }


    fun AllPermissionsGranted(context: Context): Boolean {
        for (permission in permissionsForUsedApp) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun chooseImage() {
        if (!AllPermissionsGranted(this.requireContext())) {
            permissionLauncher.launch(permissions)
        } else {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, FragmentAddProduct.REQUEST_CODE_PICK_IMAGES)
    }

}