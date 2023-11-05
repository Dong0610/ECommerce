package dong.duan.ecommerce.model

import java.io.Serializable
import java.util.Date

interface IMoldels : Serializable

enum class AccountType {
    ACCOUNT_USER,
    ACCOUNT_SELLER,
    NULL
}

data class User(
    var userId: String,
    var userName: String,
    var userEmail: String,
    var userPass: String,
    var userImg: String,
    var userBirthday: String,
    var userGender: String,
    var userPhone: String,
    var userAddress: String,
    var accountType: String
) : IMoldels

data class ShopManager(
    var id: String,
    var name: String,
    var urlImg: String,
    var image: String,
    var userID: String,
    var address: String,
    var timeCreate: String
) : IMoldels

data class Product(
    var id: String,
    var name: String,
    var price: Float,
    var saleOff: Float,
    var idShop: String,
    var count: Int,
    var imageUrl: MutableList<Any>?,
    var idUser: String,
    var star: Float,
    var timeUp: String = "",
    var isSale: Boolean = true,
    var porductSize: MutableList<ProductSize>? = null,
    var idManufacturer: String = "",
    var nameManufacturer: String = "",
    var description: String = "",
    var style: String = "CD0113-400",
    var evaluation:Int,
    var countBuy :Int = 0,
    var tax:Float = 0f,
    var transMoney: Float = 0f
) : IMoldels

data class ProductSize(var size: String) : IMoldels


data class CardProduct(
    var islove: Boolean,
    var userID: String,
    var userName: String,
    var nunCount: Int,
    var productID: String,
    var productShopID: String,
    var productName: String,
    var prductImg: String,
    var price: Float,
    var size :Int,
    var tax:Float ,
    var transMoney: Float,
) : IMoldels


data class Order(
    var orderID: String,
    var shopId: String = "",
    var userId: String = "",
    var productId: String = "",
    var productName: String = "",
    var productCount: Int = 0,
    var productPrice: Float = 0.0f,
    var productImg: String = "",
    var orderStatus: String = "",
    var orderTime: Date = Date(),
    var orderStatusTime:Date =Date(),
    var remindName: String = "",
    var receiverName: String = "",
    var location: String = "",
    var phoneNumber: String = "",
    var phoneNumber2: String = "",
    var cancelMess:String="",
    var orderPrSize :Int = 0
) : IMoldels

class Notification(var id: String, var value: String, var time: String,var productID:String, var orderID: String) :
    IMoldels

data class Address constructor(
    var idAddress: String = "", var remindName: String = "",
    var receiverName: String = "",
    var location: String = "", var phoneNumber: String = "", var phoneNumber2: String = ""
) : IMoldels

data class OrderData(var value:String="",var count:Int =0,var maxCount:Int =0):IMoldels

data class CreditCard constructor(
    var idCard: String, var imgCard: Any?,
    var surplus:Float,
    var passCard:String,
    var carName: String, var cardNumber: String,
    var securutyCode: String,
    var holder: String, var endDate: String, var color: Int
) : IMoldels


data class Manufacturer constructor(
    var idManu: String = "",
    var nameManu: String = "",
    var imageUrl: Any = ""
) : IMoldels

data class ManufacturerPut(var idManu: String, var nameManu: String, var imageUrl: Int) : IMoldels


data class ProductReview(
    var reviewId: String = "",
    var reviewUserId: String = "",
    var productID: String = "",
    var reviewImg: ArrayList<Any>? = null,
    var reviewUserName: String = "",
    var reviewUserImg: String = "",
    var reviewStar: Float = 0.0f,
    var reviewComment: String = "",
    var reviewTime: String = ""
) : IMoldels



data class PaymendMethod(var id:String,var name:String,var image:Any)











