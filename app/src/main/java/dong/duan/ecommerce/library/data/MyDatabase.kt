package dong.duan.ecommerce.library.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import dong.duan.ecommerce.library.AppContext

// Step 1: Create a Database Helper Class
class MyDatabaseHelper() :
    SQLiteOpenHelper(AppContext.context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Step 2: Define a Contract Class
    companion object {
        private const val DATABASE_NAME = "my_database.db"
        private const val DATABASE_VERSION = 1

        // Define the table and column names
        const val TABLE_NAME = "my_table"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"


        const val CURRENT_SEARCH = "Current"
        const val ID_CR_SEARCH = "sreach_id"
        const val VALUE_SREACH = "value"

        const val FAVORITE = "favorite"
        const val FAVORITE_ID = "favorite_id"
        const val FAVORITE_PRID = "favorite_pr_id"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create the database table
        val createTableSQL = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT
            )
        """.trimIndent()
        val curretSearch = """
            CREATE TABLE $CURRENT_SEARCH ( $ID_CR_SEARCH INTEGER PRIMARY KEY AUTOINCREMENT,
            $VALUE_SREACH TEXT )
        """.trimIndent()

        val favorite = """
            CREATE TABLE $FAVORITE ( $FAVORITE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $FAVORITE_PRID TEXT )
        """.trimIndent()

        db?.execSQL(favorite)
        db?.execSQL(createTableSQL)
        db?.execSQL(curretSearch)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Handle database schema upgrades here
    }

    fun insertCurrent(current: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(VALUE_SREACH, current)
        }
        db.insert(CURRENT_SEARCH, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun readCrSearch(): ArrayList<String> {
        val data = arrayListOf<String>()
        val db = readableDatabase
        val cussor = db.query(
            CURRENT_SEARCH,
            null, // projection (null means all columns)
            null, // selection (null means no selection criteria)
            null, // selectionArgs (if you have selection criteria with placeholders)
            null, // groupBy
            null, // having
            "$ID_CR_SEARCH DESC", // orderBy (order by ID in descending order)
            "10" // limit (limit to the top 10 rows)
        )

        while (cussor.moveToNext()) {
            data.add(cussor.getString(cussor.getColumnIndex(VALUE_SREACH)).toString())
        }

        return data

    }

    fun insertFavorite(current: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(FAVORITE_PRID, current)
        }
        db.insert(FAVORITE, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun readFavorite(): ArrayList<String> {
        val data = arrayListOf<String>()
        val db = readableDatabase
        val cussor = db.query(
            FAVORITE,
            null, null, null, null, null, null
        )
        while (cussor.moveToNext()) {
            data.add(cussor.getString(cussor.getColumnIndex(FAVORITE_PRID)).toString())
        }
        return data

    }

    fun deleteFavorite(productID: String) {
        val db = writableDatabase
        val whereClause = "$FAVORITE_PRID = ?"
        val whereArgs = arrayOf(productID)
        db.delete(FAVORITE, whereClause, whereArgs)
        db.close()
    }

}
