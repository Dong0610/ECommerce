package dong.duan.ecommerce.interfaces

import dong.duan.ecommerce.model.Order

interface OnAdminData {
    fun onGetAllOrder(listOrder:MutableList<Order>)
}