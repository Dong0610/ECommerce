package dong.duan.ecommerce.interfaces

import dong.duan.ecommerce.model.Manufacturer
import dong.duan.ecommerce.model.Product

interface SearchManufactData {
    fun onHomeSearch(manufacturer: Manufacturer)
}

interface FetchAllData {
    fun onSuccess(data:MutableList<Product>)
}