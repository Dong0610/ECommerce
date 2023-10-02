package dong.duan.ecommerce.activity

import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.ActivityAddDataBinding
import dong.duan.ecommerce.library.base.BaseActivity
import dong.duan.ecommerce.library.show_toast
import dong.duan.ecommerce.library.url_to_string
import dong.duan.ecommerce.model.ManufacturerPut
import dong.duan.ecommerce.model.Product
import dong.duan.ecommerce.model.ProductSize
import dong.duan.ecommerce.model.ShopManager
import dong.duan.ecommerce.model.User
import dong.duan.ecommerce.utility.Constant
import dong.duan.ecommerce.utility.InitData
import java.util.Date

class AddDataActivity : BaseActivity<ActivityAddDataBinding>() {
    override fun getViewBinding() = ActivityAddDataBinding.inflate(layoutInflater)

    override fun createView() {
        binding.addUser.setOnClickListener {
            addUsers()
        }

        binding.addShop.setOnClickListener {
            addShop()
        }

        binding.addProduct.setOnClickListener {
            addProduct()
        }
    }

    private fun addProduct() {


        val listPutData = mutableListOf(
            ManufacturerPut("manufacture_id_01", "Nike", R.drawable.shop_img_nike),
            ManufacturerPut("manufacture_id_02", "Converse", R.drawable.shop_img_converse),
            ManufacturerPut("manufacture_id_03", "Dior", R.drawable.shop_img_dior),
            ManufacturerPut("manufacture_id_04", "Domba", R.drawable.shop_img_domba),
            ManufacturerPut("manufacture_id_05", "Fila", R.drawable.shop_img_fila),
            ManufacturerPut("manufacture_id_06", "Gucci", R.drawable.shop_img_gucci),
            ManufacturerPut("manufacture_id_07", "MLB", R.drawable.shop_img_mlb),
            ManufacturerPut("manufacture_id_08", "New Balance", R.drawable.shop_img_new_balance),
            ManufacturerPut("manufacture_id_09", "Puma", R.drawable.shop_img_pumas),
            ManufacturerPut("manufacture_id_10", "Vans", R.drawable.shop_img_vans),
        )

//        val listProduct = mutableListOf(
//            Product(
//                "HJwm1eFo4vu95mwtcrua",
//                "Giày Nike Nam",
//                100f,
//                5f,
//                listShop.get(1).id,
//                10,
//                mutableListOf(
//                    "https://supersports.com.vn/cdn/shop/products/DH2987-001-1_900x@2x.jpg?v=1663757542",
//                    "https://supersports.com.vn/cdn/shop/products/DH2987-001-6_900x@2x.jpg?v=1663757542"
//                ),
//                listShop.get(1).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(0).idManu,
//                InitData.listPutData.get(0).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrub",
//                "Giày Nike Nữ",
//                100f,
//                5f,
//                listShop.get(1).id,
//                10,
//                mutableListOf(
//                    "https://product.hstatic.net/1000366086/product/dd1096-600_0_448acea668564b9aa2b7a878e092280a_master.jpg",
//                    "https://product.hstatic.net/1000366086/product/dd1096-600_3_d057defb12af4256b10606490f8dc49e_master.jpg",
//                    "https://product.hstatic.net/1000366086/product/dd1096-600_4_2f8534c210d84737bb559d7b65bc4e73_master.jpg"
//                ),
//                listShop.get(1).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(0).idManu,
//                InitData.listPutData.get(0).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcruc",
//                "Giày Converse Nam",
//                100f,
//                5f,
//                listShop.get(1).id,
//                10,
//                mutableListOf(
//                    "https://supersports.com.vn/cdn/shop/products/M9166C-1_900x@2x.jpg?v=1668400719",
//                    "https://supersports.com.vn/cdn/shop/products/M9166C-3_900x@2x.jpg?v=1668400720"
//                ),
//                listShop.get(1).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(1).idManu,
//                InitData.listPutData.get(1).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrud",
//                "Giày Converse Nữ",
//                100f,
//                5f,
//                listShop.get(1).id,
//                10,
//                mutableListOf(
//                    "https://product.hstatic.net/1000284478/product/0000_black_563508c_1_c8f81e06b98d4486ac8905fa5fa45354.jpg",
//                    "https://product.hstatic.net/1000284478/product/0000_black_563508c_1_c8f81e06b98d4486ac8905fa5fa45354.jpg",
//                    "https://product.hstatic.net/1000284478/product/0000_black_563508c_1_c8f81e06b98d4486ac8905fa5fa45354_large.jpg"
//                ),
//                listShop.get(1).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(1).idManu,
//                InitData.listPutData.get(1).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrue",
//                "Giày Dior Nam",
//                100f,
//                5f,
//                listShop.get(2).id,
//                10,
//                mutableListOf(
//                    "https://cdn.vuahanghieu.com/unsafe/500x0/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/07/giay-gucci-ace-embroidered-sneaker-white-leather-with-bee-mau-trang-size-39-62e39dfe418c9-29072022154446.jpg",
//                    "https://cdn.vuahanghieu.com/unsafe/0x500/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2019/10/giay-gucci-men-s-ace-embroidered-sneaker-white-leather-with-bee-5da4465bc071f-14102019165643.jpg"
//                ),
//                listShop.get(2).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(2).idManu,
//                InitData.listPutData.get(2).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcruf",
//                "Giày Dior Nữ",
//                100f,
//                5f,
//                listShop.get(2).id,
//                10,
//                mutableListOf(
//                    "https://images.hardlyeverwornit.com/plp_thumbnail_jpg/products/144836/Screenshot-2023-08-04-at-15-48-46-64cd0fdd71b3f.png",
//                    "https://images.hardlyeverwornit.com/pdp_gallery_list_tablet/products/144836/HARDLY-EVER-WORN-IT139429-64ccdcc331b43.jpg"
//                ),
//                listShop.get(2).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(2).idManu,
//                InitData.listPutData.get(2).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrug",
//                "Giày Domba Nam",
//                100f,
//                5f,
//                listShop.get(2).id,
//                10,
//                mutableListOf(
//                    "https://product.hstatic.net/200000581855/product/giay_domba_got_den_h-9111__5__0cfb3eb629124920ba32d1b158056556_grande.jpg",
//                    "https://product.hstatic.net/200000581855/product/giay_domba_got_den_h-9111__1__f87381d86fb84d2e9962989e25f2e5bd_grande.jpg"
//                ),
//                listShop.get(2).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(3).idManu,
//                InitData.listPutData.get(3).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcruh",
//                "Giày Domba Nữ",
//                100f,
//                5f,
//                listShop.get(2).id,
//                10,
//                mutableListOf(
//                    "https://authentic-shoes.com/wp-content/uploads/2023/04/1898197_l_5d29beba94464bf4943fbad53d96e919-768x465.jpg",
//                    "https://authentic-shoes.com/wp-content/uploads/2023/04/5d089061638c6-giay-domba-high-point-white-black-h-9111-03_c4c60bc7177441a4ba5fcdd2ef51a57e.jpg"
//                ),
//                listShop.get(2).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(3).idManu,
//                InitData.listPutData.get(3).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrui",
//                "Giày Fila Nữ",
//                100f,
//                5f,
//                listShop.get(3).id,
//                10,
//                mutableListOf(
//                    "https://supersports.com.vn/cdn/shop/products/1RM02486F-100-1_900x@2x.jpg?v=1679567305",
//                    "https://supersports.com.vn/cdn/shop/products/1RM02486F-100-6_900x@2x.jpg?v=1679567305"
//                ),
//                listShop.get(3).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(4).idManu,
//                InitData.listPutData.get(4).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcruk",
//                "Giày Fila Nam",
//                100f,
//                5f,
//                listShop.get(3).id,
//                10,
//                mutableListOf(
//                    "https://cdn.shopify.com/s/files/1/0456/5070/6581/t/138/assets/9_9FILAvi_png?v=1693911326",
//                    "https://cdn.shopify.com/s/files/1/0456/5070/6581/t/138/assets/9_9FILAvi_png?v=1693911326"
//                ),
//                listShop.get(3).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(4).idManu,
//                InitData.listPutData.get(4).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrul",
//                "Giày Gucci Nam",
//                100f,
//                5f,
//                listShop.get(3).id,
//                10,
//                mutableListOf(
//                    "https://cdn.chiaki.vn/unsafe/0x960/left/top/smart/filters:quality(75)/https://chiaki.vn/upload/product/2023/01/giay-gucci-ace-gg-supreme-bees-548950-9n050-8465-63d385db7d594-27012023150547.jpg",
//                    "https://cdn.chiaki.vn/unsafe/0x960/left/top/smart/filters:quality(75)/https://chiaki.vn/upload/product/2023/01/giay-gucci-ace-gg-supreme-bees-548950-9n050-8465-63d385db4392b-27012023150547.jpg"
//                ),
//                listShop.get(3).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(5).idManu,
//                InitData.listPutData.get(5).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrum",
//                "Giày Gucci Nữ",
//                100f,
//                5f,
//                listShop.get(3).id,
//                10,
//                mutableListOf(
//                    "https://d3vfig6e0r0snz.cloudfront.net/rcYjnYuenaTH5vyDF/images/products/2c8479b979d548ec0c6f4e0da6d956e3.webp",
//                    "https://d3vfig6e0r0snz.cloudfront.net/rcYjnYuenaTH5vyDF/images/products/2c42104e29d7f114a97f0117c9a97bdb.webp"
//                ),
//                listShop.get(3).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(5).idManu,
//                InitData.listPutData.get(5).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcruo",
//                "Giày MLB Nữ",
//                100f,
//                5f,
//                listShop.get(4).id,
//                10,
//                mutableListOf(
//                    "https://cdn.vuahanghieu.com/unsafe/500x0/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/09/632922598969c-20092022091553.jpg",
//                    "https://cdn.vuahanghieu.com/unsafe/0x500/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2022/09/632922597e1b8-20092022091553.jpg"
//                ),
//                listShop.get(4).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(6).idManu,
//                InitData.listPutData.get(6).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrup",
//                "Giày MLB Nam",
//                100f,
//                5f,
//                listShop.get(4).id,
//                10,
//                mutableListOf(
//                    "https://cdn.vuahanghieu.com/unsafe/0x500/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2023/08/giay-the-thao-mlb-chunky-boston-red-sox-3asxclh3n-43grs-mau-xam-trang-64eebe01032ef-30082023105649.jpg",
//                    "https://cdn.vuahanghieu.com/unsafe/0x500/left/top/smart/filters:quality(90)/https://admin.vuahanghieu.com/upload/product/2023/08/giay-the-thao-mlb-chunky-boston-red-sox-3asxclh3n-43grs-mau-xam-trang-64eebe01032ef-30082023105649.jpg"
//                ),
//                listShop.get(4).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(6).idManu,
//                InitData.listPutData.get(6).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcruq",
//                "Giày New Balance Nam",
//                100f,
//                5f,
//                listShop.get(4).id,
//                10,
//                mutableListOf(
//                    "https://app.fuxcdn.de/api/88a8ac53-6277-4ba3-9f52-6c743e914d99/thumbnail/30/30/7f/1660922850/new-balance-numeric-nm1010-tiago-lemos-sea-salt-skateboarding-nm1010wi_3_618x773.jpg",
//                    "https://app.fuxcdn.de/api/88a8ac53-6277-4ba3-9f52-6c743e914d99/thumbnail/5e/a9/4c/1660922851/new-balance-numeric-nm1010-tiago-lemos-sea-salt-skateboarding-nm1010wi_4_618x773.jpg"
//                ),
//                listShop.get(4).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(7).idManu,
//                InitData.listPutData.get(7).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrur",
//                "Giày New Balance Nữ",
//                100f,
//                5f,
//                listShop.get(4).id,
//                10,
//                mutableListOf(
//                    "https://d3vfig6e0r0snz.cloudfront.net/rcYjnYuenaTH5vyDF/images/products/7b97c2e505d3bb4f0fe5c56dbe7e1592.webp",
//                    "https://d3vfig6e0r0snz.cloudfront.net/rcYjnYuenaTH5vyDF/images/products/d470510f4a4c83c87157e0cbf5fa1e0f.webp"
//                ),
//                listShop.get(4).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(7).idManu,
//                InitData.listPutData.get(7).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrus",
//                "Giày Puma Nam",
//                100f,
//                5f,
//                listShop.get(0).id,
//                10,
//                mutableListOf(
//                    "https://sithimy.s3.ap-southeast-1.amazonaws.com/sithimy/media/Z5Ae6rObnpw09fZvACsooNrdpWh8mblTd7KhMtbS.jpg",
//                    "https://sithimy.s3.ap-southeast-1.amazonaws.com/sithimy/media/2g2eS4XSn9OhNv2kXzugR6fi0F0Ehyhk00Y8diNd.jpg"
//                ),
//                listShop.get(0).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(8).idManu,
//                InitData.listPutData.get(8).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcrut",
//                "Giày Puma Nữ",
//                100f,
//                5f,
//                listShop.get(0).id,
//                10,
//                mutableListOf(
//                    "https://product.hstatic.net/200000581855/product/giay_puma_skye_clean_pink_380147-05__3__fa4d30398f1449b6ba35b719cb89da93_grande.jpg",
//                    "https://product.hstatic.net/200000581855/product/giay_puma_skye_clean_pink_380147-05__4__fad4e2c81462456abba478557749bd41_grande.jpg"
//                ),
//                listShop.get(0).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(8).idManu,
//                InitData.listPutData.get(8).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcruu",
//                "Giày Vans Nữ",
//                100f,
//                5f,
//                listShop.get(0).id,
//                10,
//                mutableListOf(
//                    "https://bizweb.dktcdn.net/thumb/small/100/140/774/products/vans-old-skool-black-white-vn000d3hy28-1.jpg?v=1661757331440",
//                    "https://bizweb.dktcdn.net/thumb/small/100/140/774/products/vans-old-skool-black-white-vn000d3hy28-3.jpg?v=1661757331440"
//                ),
//                listShop.get(0).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(9).idManu,
//                InitData.listPutData.get(9).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            ),
//            Product(
//                "HJwm1eFo4vu95mwtcruu",
//                "Giày Vans Nam",
//                100f,
//                5f,
//                listShop.get(0).id,
//                10,
//                mutableListOf(
//                    "https://bizweb.dktcdn.net/thumb/small/100/140/774/products/vans-old-skool-classic-navy-white-vn000d3hnvy-1.jpg?v=1625922070377",
//                    "https://bizweb.dktcdn.net/thumb/small/100/140/774/products/vans-old-skool-classic-navy-white-vn000d3hnvy-2.jpg?v=1625922077960"
//                ),
//                listShop.get(0).userID,
//                0,
//                Date().toString(),
//                true,
//                mutableListOf(
//                    ProductSize("35"),
//                    ProductSize("36"),
//                    ProductSize("37"),
//                    ProductSize("40")
//                ),
//                InitData.listPutData.get(9).idManu,
//                InitData.listPutData.get(9).nameManu,
//                "Yêu thích kiểu dáng cổ điển của bóng rổ thập niên 80 nhưng bạn có thích văn hóa nhịp độ nhanh của trò chơi ngày nay không? Cổ giày có đệm, kiểu dáng cắt thấp trông bóng bẩy và tạo cảm giác tuyệt vời trong khi các lỗ đục ở mũi giày và hai bên tạo thêm sự thoải mái và dễ thở."
//            )
//        )

//        listProduct.forEach { product: Product ->
//            addToDb(product)
//        }
    }

    private fun addToDb(product: Product) {
        val hashMap = HashMap<String, Any>()
        hashMap[Constant.PRODUCT_NAME] = product.name
        hashMap[Constant.PRODUCT_SIZE] = product.porductSize!!.toMutableList()
        hashMap[Constant.PRODUCT_PRICE] = product.price
        hashMap[Constant.PRODUCT_SALEOFF] = product.saleOff
        hashMap[Constant.PRODUCT_STAR] = 0
        hashMap[Constant.PRODUCT_IMG] = product.imageUrl!!.toMutableList()
        hashMap[Constant.PRODUCT_COUNT] = product.count
        hashMap[Constant.PRODUCT_USER_ID] = product.idUser
        hashMap[Constant.PRODUCT_TIME_UP] = product.timeUp
        hashMap[Constant.PRODUCT_SHOP_ID] = product.idShop
        hashMap[Constant.PRODUCT_DESCRIBLE] = product.description
        hashMap[Constant.PRODUCT_MANU_ID] = product.idManufacturer
        hashMap[Constant.PRODUCT_MANU_NAME] = product.nameManufacturer
        hashMap[Constant.PRODUCT_STYLE]= product.style
        hashMap[Constant.PRODUCT_IS_SALE] = true

        firestore.collection(Constant.KEY_PRODUCT).document(product.id)
            .set(hashMap).addOnSuccessListener {
                loadding.dismiss()
                show_toast("Thêm sản phẩm thành công")
            }.addOnFailureListener {
                show_toast(it.message.toString())
            }

    }


    private fun addShop() {
        loadding.show()

        listShop.forEach { shopManager ->
            addShopToDb(shopManager)
        }
    }

    private fun addShopToDb(shopManager: ShopManager) {
        val hasmap = HashMap<String, Any>()
        hasmap[Constant.SHOP_NAME] = shopManager.name
        hasmap[Constant.SHOP_ADDRESS] = shopManager.address
        hasmap[Constant.SHOP_PHONE] = ""
        hasmap[Constant.SHOP_IMG_URL] = shopManager.urlImg
        hasmap[Constant.SHOP_IMG] = shopManager.image
        hasmap[Constant.SHOP_US_ID] = shopManager.userID
        hasmap[Constant.SHOP_COUTN_PR] = "0"
        firestore.collection(Constant.KEY_SHOP).document(shopManager.id)
            .set(hasmap)
            .addOnSuccessListener { task ->

            }
            .addOnFailureListener { e ->
                show_toast(e.message.toString())
            }
        loadding.dismiss()
    }


    var listUsers = mutableListOf(
        User(
            "1kkDE18I0fJaoxwrbtCH", "Yên Bùi", "yenbui123@gmail.com", "123456",
            "https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MH" +
                    "xzZWFyY2h8Mnx8dXNlciUyMHByb2ZpbGV8ZW58MHx8MHx8fDA%3D&w=1000&q=80", "", "",
            "0455737791", "Hung Yen", "1"
        ),
        User(
            "2kkDE18I0fJaoxwrbtCH", "Giang Hiệp", "hiepgiang@gmail.com", "123456",
            "https://img.freepik.com/free-vector/businessman-char" +
                    "acter-avatar-isolated_24877-60111.jpg?w=2000", "", "",
            "0455737791", "Ninh Binh", "1"
        ),
        User(
            "3kkDE18I0fJaoxwrbtCH",
            "Đông Đông",
            "dongdong@gmail.com",
            "123456",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT7g" +
                    "XPWxo_fQPzvP2TNGFtHzqQiChF6VklO68Fydsig_A69sVnizAMg_bxzCLvFEDMpwI8&usqp=CAU",
            "",
            "",
            "0455737791",
            "Thanh Hoa",
            "1"
        ),
        User(
            "4kkDE18I0fJaoxwrbtCH",
            "Hoàng Việt",
            "hoangviet123@gmail.com",
            "123456",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9nPR4_C" +
                    "yTE4xJdV3eDJ3P4rDGs8uvpTEW-qK_-WzU6xfjqg1I1CeyCcPOHztCFtwfbrc&usqp=CAU",
            "",
            "",
            "0455737791",
            "Thai Binh",
            "1"
        ),
        User(
            "5kkDE18I0fJaoxwrbtCH",
            "Bùi Dương",
            "duongduong@gmail.com",
            "123456",
            "https://images.unsplash.com/photo-1560011316-90b2677df5d9?ixli" +
                    "b=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEwfHx8ZW58MHx8fHx8&w=1000&q=80",
            "",
            "",
            "0455737791",
            "Thanh Hoa",
            "1"
        ),
    )

    val listShop = mutableListOf(
        ShopManager(
            "Cva2I5GjapfKYxRgqjSp",
            "Anan",
            "https://cdn.pixabay.com/photo/2013/07/13/11/31/shop-158317_960_720.png",
            url_to_string("https://cdn.pixabay.com/photo/2013/07/13/11/31/shop-158317_960_720.png"),
            listUsers.get(0).userId,
            listUsers.get(0).userAddress,
            Date().toString()
        ),
        ShopManager(
            "Cva2I4GjapfKYxRgqjSp",
            "Store",
            "https://cdn.w600.comps.canstockphoto.com/shop-market-icon-eps-vector_csp16356345.jpg",
            url_to_string("https://cdn.w600.comps.canstockphoto.com/shop-market-icon-eps-vector_csp16356345.jpg"),
            listUsers.get(1).userId,
            listUsers.get(1).userAddress,
            Date().toString()
        ),
        ShopManager(
            "Cva2I3GjapfKYxRgqjSp",
            "Dior",
            "https://img.freepik.com/free-vector/shop-with-sign-we-are-open_52683-38687.jpg",
            url_to_string("https://img.freepik.com/free-vector/shop-with-sign-we-are-open_52683-38687.jpg"),
            listUsers.get(2).userId,
            listUsers.get(2).userAddress,
            Date().toString()
        ),
        ShopManager(
            "Cva2I2GjapfKYxRgqjSp",
            "Channel",
            "https://img.freepik.com/free-vector/cartoon-style-cafe-front-shop-view_134830-697.jpg",
            url_to_string("https://img.freepik.com/free-vector/cartoon-style-cafe-front-shop-view_134830-697.jpg"),
            listUsers.get(3).userId,
            listUsers.get(3).userAddress,
            Date().toString()
        ),
        ShopManager(
            "Cva2I1GjapfKYxRgqjSp",
            "Style",
            "https://img.freepik.com/free-vector/shop-with-we-are-open-sign_23-2148557016.jpg?w=360",
            url_to_string("https://img.freepik.com/free-vector/shop-with-we-are-open-sign_23-2148557016.jpg?w=360"),
            listUsers.get(4).userId,
            listUsers.get(4).userAddress,
            Date().toString()
        ),
    )

    private fun addUsers() {
        listUsers.forEach { user: User ->
            putUser(user)
        }


    }

    private fun putUser(user: User) {
        loadding.show()
        val hasmap = HashMap<String, String>()
        hasmap[Constant.USER_NAME] = user.userName
        hasmap[Constant.USER_EMAIL] = user.userEmail
        hasmap[Constant.USER_PASS] = user.userPass
        hasmap[Constant.USER_BIRTHDAY] = user.userBirthday
        hasmap[Constant.USER_IMG] = user.userImg
        hasmap[Constant.USER_GENDER] = ""
        hasmap[Constant.USER_ADDRESS] = user.userAddress
        hasmap[Constant.USER_PHONE] = user.userPhone
        hasmap[Constant.ACCOUNT_TYPE] = user.accountType
        firestore.collection(Constant.KEY_USER).document(user.userId).set(hasmap)
            .addOnSuccessListener { task ->
                loadding.dismiss()
            }
            .addOnFailureListener {
                show_toast(it.message.toString())
            }
    }

}