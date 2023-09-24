package dong.duan.ecommerce.library

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


fun StorageReference.putImgToStorage(uri: Uri, onPutImage: OnPutImageListener) {
    val fileRef: StorageReference = child(
        System.currentTimeMillis().toString() + "." + file_extension(uri!!).toString()
    )
    fileRef.putFile(uri!!).addOnSuccessListener {
        fileRef.downloadUrl.addOnSuccessListener { uri ->
            onPutImage.onComplete(uri.toString())
        }.addOnFailureListener { ex ->
            onPutImage.onFailure(ex.message.toString())
        }
    }
}

interface OnPutImageListener{
    fun onComplete(url:String)
    fun onFailure(mess:String)
}