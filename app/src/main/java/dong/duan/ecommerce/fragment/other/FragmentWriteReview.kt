package dong.duan.ecommerce.fragment.other

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.egame.backgrounderaser.aigenerator.base.BaseFragment
import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.FragmentWriteReviewBinding
import dong.duan.ecommerce.databinding.ItemListImageCommentBinding
import dong.duan.ecommerce.library.GenericAdapter
import dong.duan.ecommerce.library.OnPutImageListener
import dong.duan.ecommerce.library.putImgToStorage
import dong.duan.ecommerce.library.sharedPreferences
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.model.Manufacturer
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.utility.Constant
import java.util.Date

class FragmentWriteReview(var product: Product) : BaseFragment<FragmentWriteReviewBinding>() {
    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWriteReviewBinding.inflate(layoutInflater)

    var listImagePR = mutableListOf<Uri?>()


    lateinit var manufacturer: Manufacturer

    private var imageAdapter: GenericAdapter<Uri?, ItemListImageCommentBinding>? = null

    override fun initView() {
        binding.icBack.setOnClickListener {
            closeFragment(this)
        }
        listImagePR.add(null)
        loadProduct(listImagePR)

        binding.btnSaveReview.setOnClickListener {
            saveProduct()
        }

        binding.rating.setOnRatingBarChangeListener { _, rating, _ ->
            val sumRatting = product.star*product.evaluation
            val rattingValue = (sumRatting+rating)/(product.evaluation+1)
            val hasmap = HashMap<String,Any>()

            hasmap[Constant.PRODUCT_EVALUATION]=  (product.evaluation+1)
            hasmap[Constant.PRODUCT_STAR] = rattingValue

            product.evaluation= product.evaluation+1
            product.star= rattingValue
            firestore.collection(Constant.KEY_PRODUCT)
                .document(product.id)
                .update(hasmap)
                .addOnFailureListener {
                    show_toast("Đã đánh giá sản phẩm")
                }
                .addOnFailureListener {
                    show_toast(it.message.toString())
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

    private fun getImageArrlist(callback: (ArrayList<String>) -> Unit) {
        val listUrl = arrayListOf<String>()
        val totalItems = listImagePR.size - 1
        var itemCount = 0
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
    }

    @SuppressLint("SuspiciousIndentation")
    private fun saveProduct() {
        val hashMap = HashMap<String, Any>()
        loadding.show()
        if (listImagePR.size == 1) {
            hashMap[Constant.REVIEW_COMMERNT] = binding.edtComment.text.toString()
            hashMap[Constant.REVIEW_TIME] = Date()
            hashMap[Constant.REVIEW_STAR] = binding.rating.rating
            hashMap[Constant.REVIEW_PR_ID] =product.id
            hashMap[Constant.REVIEW_USER_ID] =
                sharedPreferences.getString(Constant.USER_ID, "").toString()
            hashMap[Constant.REVIEW_USER_NAME] =
                sharedPreferences.getString(Constant.USER_NAME, "").toString()
            hashMap[Constant.REVIEW_USER_IMG] =
                sharedPreferences.getString(Constant.USER_IMG, "").toString()
            hashMap[Constant.REVIEW_IMG] = ArrayList<String>()
            firestore.collection(Constant.KEY_REVIEW)
                .add(hashMap).addOnSuccessListener {
                    Handler(Looper.myLooper()!!).postDelayed(
                        {
                            loadding.dismiss()
                            show_toast("Đã lưu lại đáng giá!")
                            closeFragment(this@FragmentWriteReview)
                        }, 1500L
                    )

                }.addOnFailureListener {
                    Handler(Looper.myLooper()!!).postDelayed(
                        {    show_toast(it.message.toString())
                            loadding.dismiss()
                            closeFragment(this@FragmentWriteReview)
                        }, 1500L
                    )
                }
        } else {
            getImageArrlist { listUrl ->
                hashMap[Constant.REVIEW_COMMERNT] = binding.edtComment.text.toString()
                hashMap[Constant.REVIEW_TIME] = Date()
                hashMap[Constant.REVIEW_STAR] = binding.rating.rating
                hashMap[Constant.REVIEW_PR_ID] =product.id
                hashMap[Constant.REVIEW_USER_ID] =
                    sharedPreferences.getString(Constant.USER_ID, "").toString()
                hashMap[Constant.REVIEW_USER_NAME] =
                    sharedPreferences.getString(Constant.USER_NAME, "").toString()
                hashMap[Constant.REVIEW_USER_IMG] =
                    sharedPreferences.getString(Constant.USER_IMG, "").toString()
                hashMap[Constant.REVIEW_IMG] = listUrl
                firestore.collection(Constant.KEY_REVIEW)
                    .add(hashMap).addOnSuccessListener {
                        Handler(Looper.myLooper()!!).postDelayed(
                            {                                loadding.dismiss()
                                show_toast("Đã lưu lại đáng giá!")
                                closeFragment(this@FragmentWriteReview)
                            }, 1500L
                        )
                    }.addOnFailureListener {
                        loadding.dismiss()
                        show_toast(it.message.toString())
                        Handler(Looper.myLooper()!!).postDelayed(
                            {
                                closeFragment(this@FragmentWriteReview)
                            }, 1500L
                        )

                    }
            }
        }

        commentSucces?.OnSuccess()
    }




    private fun loadProduct(listString: MutableList<Uri?>) {
        imageAdapter = GenericAdapter(
            listString,
            ItemListImageCommentBinding::inflate
        ) { itembinding, item, position ->
            if (item == null) {
                itembinding.root.setOnClickListener {
                    chooseImage()
                }
                Glide.with(requireContext()).load(R.drawable.img_add_image)
                    .into(itembinding.imagePreview)
            } else {
                Glide.with(requireContext()).load(item).into(itembinding.imagePreview)
            }

        }
        binding.rcvListImg.adapter = imageAdapter


    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGES = 101
        private  var commentSucces:OnCommentSuccess ?=null
        fun onCommentSuccess(cmss:OnCommentSuccess){
            this.commentSucces= cmss;
        }
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
        if (requestCode == REQUEST_CODE_PICK_IMAGES && resultCode == Activity.RESULT_OK) {
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

interface  OnCommentSuccess{
    fun OnSuccess()
}