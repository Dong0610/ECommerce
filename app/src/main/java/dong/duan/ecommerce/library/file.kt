package dong.duan.ecommerce.library

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileUtils<T> {
    fun saveToFile(data: ArrayList<T>, fileName: String, onSaveFile: OnSaveFile) {
        val inf = FileOutputStream(fileName)
        val fin = ObjectOutputStream(inf)
        try {
            fin.writeObject(data)
            fin.close()
            onSaveFile.OnSuccess("Save success")
        } catch (e: Exception) {
            onSaveFile.OnFlaid(e.message.toString())
        }
    }

    fun readFile(fileName: String, onReadFile: OnReadFile<T>) {
        try {
            val out= FileInputStream(fileName)
            val fout= ObjectInputStream(out)
            val data= fout.readObject() as (ArrayList<T>)
            fout.close()
            onReadFile.OnSuccess(data)
        }
        catch (e:Exception){
            onReadFile.OnFlaid(e.message.toString())
        }
    }

    interface OnSaveFile {
        fun OnSuccess(value: Any)
        fun OnFlaid(value: Any)
    }

    interface OnReadFile<T> {
        fun OnSuccess(value: ArrayList<T>)
        fun OnFlaid(value: Any)
    }
}