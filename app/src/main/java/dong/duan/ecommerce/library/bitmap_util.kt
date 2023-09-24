package dong.duan.ecommerce.library

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun bitmap_from_uri(context: Context, uri: Uri): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun bitmap_to_file(bitmap: Bitmap, activity: Activity): File {
    val file = File(activity.getExternalFilesDir(null), "image.jpg")
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.close()
    return file
}

fun uri_from_bitmap(context: Context, bitmap: Bitmap): Uri? {
    var uri: Uri? = null
    try {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "image.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val resolver = context.contentResolver
        uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            val outputStream = resolver.openOutputStream(uri)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream?.close()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return uri
}

fun uri_from_bitmap(context: Context, bitmap: Bitmap, urical: (Uri?) -> Unit) {
    var uri: Uri? = null
    try {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "image.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val resolver = context.contentResolver
        uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        // Invoke the callback with the generated Uri
        urical(uri)

        val outputStream = resolver.openOutputStream(uri!!)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream?.close()

        // Delete the file associated with the Uri
        resolver.delete(uri, null, null)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun file_extension(uri: Uri): String? {
    val cr: ContentResolver = AppContext.context.getContentResolver()
    val mime = MimeTypeMap.getSingleton()
    return mime.getExtensionFromMimeType(cr.getType(uri))
}

fun current_time(): String {
    val dateFormat = SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault())
    val currentTime = Calendar.getInstance().time
    return dateFormat.format(currentTime)
}

fun save_bitmap_image(bitmap: Bitmap) {
    val fileName = "${current_time()}.jpg" // Specify the desired file name and extension
    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

    try {
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val imageFile = File(storageDir, fileName)
        val fileOutputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.close()
        show_toast("Image saved successfully!")
    } catch (e: Exception) {
        e.printStackTrace()
        show_toast("Failed to save image.")
    }
}


fun save_image(Final_bitmap: Bitmap): File? {
    val pictureFileDir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "Photo Edit"
    )
    if (!pictureFileDir.exists()) {
        val isDirectoryCreated = pictureFileDir.mkdirs()
        if (!isDirectoryCreated) Log.i("TAG", "Can't create directory to save the image")
        return null
    }
    val filename = pictureFileDir.path + File.separator + System.currentTimeMillis() + ".jpg"
    val pictureFile = File(filename)
    try {
        pictureFile.createNewFile()
        val oStream = FileOutputStream(pictureFile)
        Final_bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream)
        oStream.flush()
        oStream.close()
        show_toast("Save Image Successfully..")
    } catch (e: IOException) {
        e.printStackTrace()
        Log.i("TAG", "There was an issue saving the image.")
    }
    scanGallery(AppContext.context, pictureFile.absolutePath)
    return pictureFile
}

fun save_image_photo(bitmap: Bitmap) {
    //Generating a file name
    val filename = "${System.currentTimeMillis()}.jpg"

    //Output stream
    var fos: OutputStream? = null

    //For devices running android >= Q
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        //getting the contentResolver
        AppContext.context.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
    }

    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        show_toast("Saved to Photos")
    }
}

fun scanGallery(cntx: Context, path: String) {
    try {
        MediaScannerConnection.scanFile(
            cntx, arrayOf<String>(path), null
        ) { path, uri ->
            Toast.makeText(
                cntx,
                "Save Image Successfully..",
                Toast.LENGTH_SHORT
            ).show()
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        Log.i("TAG", "There was an issue scanning gallery.")
    }
}

fun url_to_string(imageUrl: String): String {
    try {
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(input)

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}

fun uri_from_drawable(context: Context, resource: Int): Uri? {

    val drawable = ContextCompat.getDrawable(context, resource) ?: return null
    val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: return null
    val cachePath = File(context.cacheDir, File.separator + System.currentTimeMillis() + ".jpg")
    val outputStream = FileOutputStream(cachePath)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.close()
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", cachePath)
}

fun encode_image(bitmap: Bitmap): String {
    val preWith = if(bitmap.width>bitmap.height) bitmap.height/2 else bitmap.width/2
    val preHeight = bitmap.height * preWith / bitmap.width
    val prevBitMap = Bitmap.createScaledBitmap(bitmap, preWith, preHeight, false)
    val byteArrayOutputStream = ByteArrayOutputStream()
    prevBitMap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
    val bytes = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}

fun bitmap_to_string(bitmap: Bitmap?): String {
    val stream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val bytes = stream.toByteArray()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}

fun string_to_bitmap(encodedImage: String?): Bitmap {
    val decodedBytes = Base64.decode(encodedImage!!, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

fun uri_to_string(imageUri: Uri): String? {
    try {
        val inputStream =
            imageUri?.let { AppContext.context.contentResolver.openInputStream(it) }
        val bitmap = BitmapFactory.decodeStream(inputStream)
        return encode_image(bitmap)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return null
    }
}
fun uri_from_id(id: Long): Uri? {
    val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    return ContentUris.withAppendedId(contentUri, id.toLong())
}

fun bitmap_from_id(id: Long): Bitmap {
    val contentResolver: ContentResolver = AppContext.context.contentResolver
    val uri: Uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
    val inputStream = contentResolver.openInputStream(uri)
    return BitmapFactory.decodeStream(inputStream)
}


fun bitmap_from_id(id: Int, context: Context): Bitmap {
    return BitmapFactory.decodeResource(context.resources, id)
}