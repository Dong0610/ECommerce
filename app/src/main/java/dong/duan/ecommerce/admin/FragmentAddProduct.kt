package dong.duan.ecommerce.admin

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.adapter.admin.OnPopupCalback
import dong.duan.ecommerce.adapter.admin.PopupManufactAdapter
import dong.duan.ecommerce.databinding.FragmentAddProductBinding
import dong.duan.ecommerce.databinding.ItemImageAdminProductBinding
import dong.duan.ecommerce.databinding.ItemListProductSizeBinding
import dong.duan.ecommerce.databinding.PopupSelectManufactBinding
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.library.OnPutImageListener
import dong.duan.ecommerce.library.base.BasePopupLocation
import dong.duan.ecommerce.library.putImgToStorage
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Manufacturer
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.utility.Constant
import java.util.Date

class FragmentAddProduct : BaseFragment<FragmentAddProductBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddProductBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        initData()
        addProduct()
    }

    companion object {
         const val REQUEST_CODE_PICK_IMAGES = 101
    }

    var listImagePR = mutableListOf<Uri?>()


    lateinit var manufacturer: Manufacturer

    private var imageAdapter: GenericAdapter<Uri?, ItemImageAdminProductBinding>? = null

    var popup: BasePopupLocation<PopupSelectManufactBinding>? = null
    var listManufacturer = mutableListOf<Manufacturer>()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initData() {


        getManuFacturer {
            listManufacturer = it
        }

        binding.btnSaveData.setOnClickListener {
            saveProduct()
        }



        binding.icShowManufact.setOnClickListener { view ->
            binding.llNewManufact.post {
                val location = IntArray(2)
                binding.llNewManufact.getLocationOnScreen(location)
                val x = location[0]
                val y = location[1] + binding.llNewManufact.height + 2
                popup = BasePopupLocation(
                    this@FragmentAddProduct.requireContext(),
                    binding.llNewManufact, PopupSelectManufactBinding::inflate,
                    x, y, width = binding.llNewManufact.width
                ) { i, p ->
                    val manufactAdapter = PopupManufactAdapter(object : OnPopupCalback {
                        override fun OnSelect(manufact: Manufacturer) {
                            manufacturer = manufact
                            binding.txtNewManufact.text = manufact.nameManu
                            p.dismiss()
                        }
                    })
                    i.rcvListManufact.adapter = manufactAdapter
                    manufactAdapter.setItems(listManufacturer)
                }
                popup!!.show()
            }
        }

        binding.btnAddSize.setOnClickListener {
            listSize.add(ProductSize(binding.edtProductSize.text.toString()))
            binding.edtProductSize.setText("")
            addSize(listSize)
        }


    }

    val listSize = mutableListOf<ProductSize>()
    var sizeAdapter: GenericAdapter<ProductSize, ItemListProductSizeBinding>? = null

    private fun addSize(listSize: MutableList<ProductSize>) {
        sizeAdapter = GenericAdapter(
            listSize,
            ItemListProductSizeBinding::inflate
        ) { itembinding, item, i ->
            itembinding.txtName.text = item.size
        }
        binding.rcvSize.adapter = sizeAdapter
    }

    private fun getManuFacturer(calback: (ArrayList<Manufacturer>) -> Unit) {
        firestore.collection(Constant.SHOP_MANAFACT)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val manufacturers = arrayListOf<Manufacturer>()
                    for (doc in task.result!!) {
                        val idManu = doc.id
                        val nameManu = doc.getString(Constant.SHOP_MANAFACT_NAME) ?: ""
                        val imageUrl = doc.getString(Constant.SHOP_MANAFACT_IMG) ?: ""
                        val manufacturer = Manufacturer(idManu, nameManu, imageUrl)
                        manufacturers.add(manufacturer)
                        if (manufacturers.size == task.result.size()) {
                            calback(manufacturers)
                        }
                    }
                } else {
                    show_toast("Chưa có nhà cung cấp")
                }
            }
    }


    private fun getImageArrlist(callback: (ArrayList<String>) -> Unit) {
        val listUrl = arrayListOf<String>()
        val totalItems = listImagePR.size - 1
        var itemCount = 0
        if (listImagePR != null) {
            listImagePR.removeAt(0)
            listImagePR.forEach { uri ->
                putImage(uri!!) { imageUrl ->
                    listUrl.add(imageUrl)
                    itemCount++
                    if (itemCount == totalItems) {
                        callback(listUrl)
                    }
                }
            }
        } else {
            show_toast("List image is null")
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

    @SuppressLint("SuspiciousIndentation")
    private fun saveProduct() {
        val hashMap = HashMap<String, Any>()
        loadding.show()
        getImageArrlist { listUrl ->
            hashMap[Constant.PRODUCT_NAME] = binding.edtProductName.text.toString()
            hashMap[Constant.PRODUCT_SIZE] = listSize
            hashMap[Constant.PRODUCT_PRICE] = binding.edtProductPrice.text.toString()
            hashMap[Constant.PRODUCT_SALEOFF] = binding.edtProductDiscount.text.toString()
            hashMap[Constant.PRODUCT_STAR] = 0
            hashMap[Constant.PRODUCT_IMG] = listUrl
            hashMap[Constant.PRODUCT_COUNT] = binding.edtProductCount.text.toString()
            hashMap[Constant.PRODUCT_USER_ID] =
                sharedPreferences.getString(Constant.USER_ID, null).toString()
            hashMap[Constant.PRODUCT_TIME_UP] = Date()
            hashMap[Constant.PRODUCT_SHOP_ID] =
                sharedPreferences.getString(Constant.SHOP_ID).toString()
            hashMap[Constant.PRODUCT_DESCRIBLE] = binding.edtDescrible.text.toString()
            hashMap[Constant.PRODUCT_MANU_ID] = manufacturer.idManu
            hashMap[Constant.PRODUCT_MANU_NAME] = manufacturer.nameManu
            hashMap[Constant.PRODUCT_STYLE] = binding.edtProductStyle.text.toString()
            hashMap[Constant.PRODUCT_IS_SALE] = true
            hashMap[Constant.PRODUCT_EVALUATION] =1
            hashMap[Constant.PRODUCT_TAX]= binding.edtProductTax.text.toString();
            hashMap[Constant.PRODUCT_COUNTBUY]=0
            hashMap[Constant.PRODUCT_TRANSMONEY]= binding.edtProductTransmoney.text.toString()

            firestore.collection(Constant.KEY_PRODUCT)
                .add(hashMap).addOnSuccessListener {
                    loadding.dismiss()
                    show_toast("Thêm sản phẩm thành công")
                }.addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }
    }

    private fun addProduct() {
        listImagePR.add(null)
        loadProduct(listImagePR)

    }

    private fun loadProduct(listString: MutableList<Uri?>) {
        imageAdapter = GenericAdapter(
            listString,
            ItemImageAdminProductBinding::inflate
        ) { itembinding, item, position ->
            if (item == null) {
                itembinding.imageAddImg.visibility = View.VISIBLE
                itembinding.imagePreview.visibility = View.GONE
                itembinding.root.setOnClickListener {
                    chooseImage()
                }
            } else {
                itembinding.imageAddImg.visibility = View.GONE
                itembinding.imagePreview.visibility = View.VISIBLE
                Glide.with(requireContext()).load(item).into(itembinding.imagePreview)
            }

        }
        binding.rcvListImg.adapter = imageAdapter
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGES && resultCode == RESULT_OK) {
            data?.data?.let {
                listImagePR.add(it)
            }
            loadProduct(listImagePR)
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
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGES)
    }

}