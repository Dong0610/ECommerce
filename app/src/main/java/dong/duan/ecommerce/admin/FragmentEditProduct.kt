package dong.duan.ecommerce.admin

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.FragmentEditProductBinding
import dong.duan.ecommerce.databinding.ItemListImageCommentBinding
import dong.duan.ecommerce.databinding.ItemListImgEditBinding
import dong.duan.ecommerce.databinding.ItemListProductSizeBinding
import dong.duan.ecommerce.dialog.DialogWaring
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.library.OnPutImageListener
import dong.duan.ecommerce.library.putImgToStorage
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.utility.Constant

class FragmentEditProduct(var product: Product) : BaseFragment<FragmentEditProductBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditProductBinding.inflate(layoutInflater)


    companion object{
        private var updateValue:OnUpdateValue?=null
        fun updateProduct(onUpdateValue: OnUpdateValue){
            this.updateValue=onUpdateValue
        }
    }
    override fun initView() {
        eitProduct(product)

        binding.icBack.setOnClickListener {
            updateValue?.OnUpdate(product)
            closeFragment(this@FragmentEditProduct)
        }

        onClickEvent()
    }

    private fun onClickEvent() {
        binding.btnSaveName.setOnClickListener {
            firestore.collection(Constant.KEY_PRODUCT)
                .document(product.id)
                .update(Constant.PRODUCT_NAME, binding.edtPrName.text.toString())
                .addOnCompleteListener {
                    product.name=binding.edtPrName.text.toString()
                    show_toast("Sửa thông tin thành công")
                }
                .addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }

        binding.btnSavePrice.setOnClickListener {
            firestore.collection(Constant.KEY_PRODUCT)
                .document(product.id)
                .update(Constant.PRODUCT_PRICE, binding.prPrice.text.toString())
                .addOnCompleteListener {
                    product.price= binding.prPrice.text.toString().toFloat()
                    show_toast("Sửa thông tin thành công")
                }
                .addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }

        binding.btnSaveSale.setOnClickListener {
            firestore.collection(Constant.KEY_PRODUCT)
                .document(product.id)
                .update(Constant.PRODUCT_SALEOFF, binding.edtPrSale.text.toString())
                .addOnCompleteListener {
                    product.saleOff =  binding.edtPrSale.text.toString().toFloat()
                    show_toast("Sửa thông tin thành công")
                }
                .addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }

        binding.btnSaveCount.setOnClickListener {
            firestore.collection(Constant.KEY_PRODUCT)
                .document(product.id)
                .update(Constant.PRODUCT_COUNT, binding.prCount.text.toString())
                .addOnCompleteListener {
                    product.count=binding.prCount.text.toString().toInt()
                    show_toast("Sửa thông tin thành công")
                }
                .addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }

        binding.btnSaveStyle.setOnClickListener {
            firestore.collection(Constant.KEY_PRODUCT)
                .document(product.id)
                .update(Constant.PRODUCT_STYLE, binding.edtPrStyle.text.toString())
                .addOnCompleteListener {
                    product.style=binding.edtPrStyle.text.toString()
                    show_toast("Sửa thông tin thành công")
                }
                .addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }

        binding.btnSaveDescr.setOnClickListener {
            firestore.collection(Constant.KEY_PRODUCT)
                .document(product.id)
                .update(Constant.PRODUCT_DESCRIBLE, binding.prDescription.text.toString())
                .addOnCompleteListener {
                    product.description= binding.prDescription.text.toString()
                    show_toast("Sửa thông tin thành công")
                }
                .addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }

        binding.btnAddSize.setOnClickListener {
            product.porductSize!!.add(ProductSize(binding.edtSize.text.toString()))
            setSize(product.porductSize!!)
        }

        binding.btnRemoveSize.setOnClickListener {
            val size = binding.edtSize.text.toString()
            val index = product.porductSize?.indexOf(ProductSize(size))
            product.porductSize?.removeAt(index!!)
            setSize(product.porductSize!!)
        }

        binding.btnSaveSize.setOnClickListener {
            firestore.collection(Constant.KEY_PRODUCT)
                .document(product.id)
                .update(Constant.PRODUCT_SIZE, product.porductSize)
                .addOnCompleteListener {
                    updateValue?.OnUpdate(product)
                    show_toast("Sửa thông tin thành công")
                }
                .addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }
    }

    interface OnUpdateValue{
        fun OnUpdate(product: Product)
    }

    private fun eitProduct(product: Product) {
        binding.edtPrName.setText(product.name)
        binding.txtPrId.text = product.id
        binding.prCount.setText(product.count.toInt().toString())
        binding.prPrice.setText(product.price.toString())
        binding.edtPrStyle.setText(product.style)
        binding.prDescription.setText(product.description)
        binding.edtPrSale.setText(product.saleOff.toString())
        binding.edtSize.setText(product.porductSize?.get(0)?.size ?: "")
        setSize(product.porductSize!!)
        loadImage(product.imageUrl!!)


        binding.btnRemoveImg.setOnClickListener {
            DialogWaring("Bạn có chắc muốn xóa ảnh này",this.requireContext(),{
                product.imageUrl!!.removeAt(currentPostion)
                loadImage(product.imageUrl!!)
            }).show()
        }

        binding.btnAddImg.setOnClickListener {
            chooseImage()
        }

        binding.btnSaveImg.setOnClickListener {
            firestore.collection(Constant.KEY_PRODUCT)
                .document(product.id)
                .update(Constant.PRODUCT_IMG, product.imageUrl)
                .addOnCompleteListener {
                    updateValue?.OnUpdate(product)
                    show_toast("Sửa thông tin thành công")
                }
                .addOnFailureListener {
                    show_toast(it.message.toString())
                }
        }

        binding.txtP1.setOnClickListener {
            DialogWaring("Bạn có chắc ngưng bán sản phẩm này?",this.requireContext(),{
                firestore.collection(Constant.KEY_PRODUCT)
                    .document(product.id)
                    .update(Constant.PRODUCT_IS_SALE,false)
                    .addOnCompleteListener {
                        updateValue?.OnUpdate(product)
                        show_toast("Sửa thông tin thành công")
                    }
                    .addOnFailureListener {
                        show_toast(it.message.toString())
                    }
            }).show()
        }

    }

    var currentImage = mutableListOf<String>()

    private var imageAdapter: GenericAdapter<Any, ItemListImgEditBinding>? = null
    var currentPostion = -1
    private fun loadImage(listString: MutableList<Any>) {

        imageAdapter = GenericAdapter(
            listString,
            ItemListImgEditBinding::inflate
        ) { itembinding, item, position ->
            Glide.with(requireContext()).load(item).into(itembinding.imagePreview)
            if (position != currentPostion) {
                itembinding.root.setStrokeColor(R.color.appcolor)
            } else {
                itembinding.root.setStrokeColor(R.color.gainsboro)
            }
            itembinding.root.setOnClickListener {
                imageSelect(position)
            }
        }
        binding.rcvImgSize.adapter = imageAdapter
    }

    private fun imageSelect(position: Int) {
        imageAdapter!!.notifyItemChanged(currentPostion)
        currentPostion= position
        imageAdapter!!.notifyItemChanged(position)
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
        if (requestCode == FragmentAddProduct.REQUEST_CODE_PICK_IMAGES && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                putImage(it){url->
                    product.imageUrl!!.add(url)
                }
            }
            loadImage(product.imageUrl!!)
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


    val listSize = mutableListOf<ProductSize>()
    var sizeAdapter: GenericAdapter<ProductSize, ItemListProductSizeBinding>? = null

    private fun setSize(listSize: MutableList<ProductSize>) {
        sizeAdapter = GenericAdapter(
            listSize,
            ItemListProductSizeBinding::inflate
        ) { itembinding, item, i ->
            itembinding.txtName.text = item.size
            itembinding.root.setOnClickListener {
                binding.edtSize.setText(item.size)
            }
        }
        binding.rcvSize.adapter = sizeAdapter
    }

}