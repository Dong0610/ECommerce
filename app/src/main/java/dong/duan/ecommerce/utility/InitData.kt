package dong.duan.ecommerce.utility

import dong.duan.ecommerce.R
import dong.duan.ecommerce.library.AppContext
import dong.duan.ecommerce.library.Colors
import dong.duan.ecommerce.model.Address
import dong.duan.ecommerce.model.CardProduct
import dong.duan.ecommerce.model.Category
import dong.duan.ecommerce.model.CreditCard
import dong.duan.ecommerce.model.ManufacturerPut
import dong.duan.ecommerce.model.Order
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductColor
import dong.duan.ecommerce.model.ProductReview
import dong.duan.ecommerce.model.ProductSize

object InitData {
    val listProductColor = mutableListOf(
        ProductColor("#FF0000"),
        ProductColor("#0000FF"),
        ProductColor("#FF8C00"),
        ProductColor("#9400D3"),
        ProductColor("#1E90FF"),
        ProductColor("#008000")
    )
    val listProductSize = mutableListOf(
        ProductSize("37"),
        ProductSize("42"),
        ProductSize("41"),
        ProductSize("40"),
        ProductSize("39"),
        ProductSize("38"),
    )


    val listProductFile= mutableListOf<Product>()
    val listManStyle
        get() = mutableListOf(
            Category(
                "exman1",
                AppContext.context.resources.getString(R.string.exman1),
                R.drawable.ex_man_1
            ),
            Category(
                "exman2",
                AppContext.context.resources.getString(R.string.exman2),
                R.drawable.ex_man_2
            ),
            Category(
                "exman3",
                AppContext.context.resources.getString(R.string.exman3),
                R.drawable.ex_man_3
            ),
            Category(
                "exman4",
                AppContext.context.resources.getString(R.string.exman4),
                R.drawable.ex_man_4
            ),
            Category(
                "exman5",
                AppContext.context.resources.getString(R.string.exman5),
                R.drawable.ex_man_5
            ),
            Category(
                "exman6",
                AppContext.context.resources.getString(R.string.exman6),
                R.drawable.ex_man_6
            ),
            Category(
                "exman1",
                AppContext.context.resources.getString(R.string.exman1),
                R.drawable.ex_man_1
            )

        )

    val listProduct = mutableListOf(
        Product(
            "1",
            "FS - Nike Air Max 270 React...",
            100f,
            24f,
            "",
            100,
           mutableListOf( R.drawable.img_product_flashale_1),
            "",
            3
        ),
        Product(
            "2",
            "FS - Nike Air Max 270 React...",
            100f,
            24f,
            "",
            100,
            mutableListOf( R.drawable.img_product_flashale_2),
            "",
            4
        ),
        Product(
            "3",
            "FS - Nike Air Max 270 React...",
            100f,
            24f,
            "",
            100,
            mutableListOf( R.drawable.img_product_flashale_3),
            "",
            5
        ),
        Product(
            "4",
            "FS - Nike Air Max 270 React...",
            100f,
            24f,
            "",
            100,
            mutableListOf( R.drawable.img_product_flashale_4),
            "",
            4
        ),
        Product(
            "5",
            "FS - Nike Air Max 270 React...",
            100f,
            24f,
            "",
            100,
            mutableListOf( R.drawable.img_product_flashale_5),
            "",
            3
        ),
        Product(
            "6",
            "FS - Nike Air Max 270 React...",
            100f,
            24f,
            "",
            100,
            mutableListOf( R.drawable.img_product_flashale_6),
            "",
            5
        )
    )

    fun listProductCard(): MutableList<CardProduct> {
        val liscardProduct = mutableListOf<CardProduct>()
//        listProduct.forEach { p ->
//            liscardProduct.add(CardProduct(true, 1, p))
//        }
        return liscardProduct
    }

    val listwomenStyle
        get() = mutableListOf(
            Category(
                "exwomen1",
                AppContext.context.resources.getString(R.string.exwoment1),
                R.drawable.ex_women_1
            ),
            Category(
                "exwomen2",
                AppContext.context.resources.getString(R.string.exwoment2),
                R.drawable.ex_women_2
            ),
            Category(
                "exwomen3",
                AppContext.context.resources.getString(R.string.exwoment3),
                R.drawable.ex_women_3
            ),
            Category(
                "exwomen4",
                AppContext.context.resources.getString(R.string.exwoment4),
                R.drawable.ex_women_4
            ),
            Category(
                "exwomen5",
                AppContext.context.resources.getString(R.string.exwoment5),
                R.drawable.ex_women_5
            ),
            Category(
                "exwomen6",
                AppContext.context.resources.getString(R.string.exwoment6),
                R.drawable.ex_women_6
            ),
            Category(
                "exwomen7",
                AppContext.context.resources.getString(R.string.exwoment7),
                R.drawable.ex_women_7
            ),
        )

    val listCurrentSeach = mutableListOf<String>(
        "Nike Air Max 270 React ENG",
        "Nike Air Vapormax 360",
        "Nike Air Max 270 React ENG",
        "Nike Air Max 270 React ENG",
        "Nike Air VaporMax Flyknit 3",
        "Nike Air Max 97 Utility"
    )

//    val listOrder = mutableListOf(
//        Order("1", "11/02/2023 12:10:34 ", "UserID", 2, "Shipping", 200f),
//        Order("2", "11/03/2023 12:10:34 ", "UserID", 5, "Receive", 80f),
//        Order("3", "11/04/2023 12:10:34 ", "UserID", 3, "Shipping", 40f),
//        Order("4", "11/05/2023 12:10:34 ", "UserID", 1, "Shipping", 34f),
//        Order("5", "11/06/2023 12:10:34 ", "UserID", 4, "Shipping", 60f)
//    )

    val listAddresses = mutableListOf(
        Address("1", "Dong", "Bui Van Dong", "Thach thanh thanh hoa", "1212121212"),
        Address("1", "Dong", "Bui Van Dong", "Thach thanh thanh hoa", "1212121212"),
        Address("1", "Dong", "Bui Van Dong", "Thach thanh thanh hoa,", "1212121212"),
        Address("1", "Dong", "Bui Van Dong", "Thach thanh thanh hoa", "1212121212"),
        Address("1", "Dong", "Bui Van Dong", "Thach thanh thanh hoa", "1212121212")
    )

    val listcrediCard = mutableListOf(
        CreditCard("1", null, "Vietcombank", "1010733242", "1940","1934", "12/28", Colors.GREEN_YELLOW),
        CreditCard("1", null, "Vietcombank", "1010733242", "1948","1934", "12/28", Colors.PURPLE)
    )

    val listPutData = mutableListOf(
        ManufacturerPut("manufacture_id_01","Nike",R.drawable.shop_img_nike),
        ManufacturerPut("manufacture_id_02","Converse",R.drawable.shop_img_converse),
        ManufacturerPut("manufacture_id_03","Dior",R.drawable.shop_img_dior),
        ManufacturerPut("manufacture_id_04","Domba",R.drawable.shop_img_domba),
        ManufacturerPut("manufacture_id_05","Fila",R.drawable.shop_img_fila),
        ManufacturerPut("manufacture_id_06","Gucci",R.drawable.shop_img_gucci),
        ManufacturerPut("manufacture_id_07","MLB",R.drawable.shop_img_mlb),
        ManufacturerPut("manufacture_id_08","New Balance",R.drawable.shop_img_new_balance),
        ManufacturerPut("manufacture_id_09","Puma",R.drawable.shop_img_pumas),
        ManufacturerPut("manufacture_id_10","Vans",R.drawable.shop_img_vans),
    )

}