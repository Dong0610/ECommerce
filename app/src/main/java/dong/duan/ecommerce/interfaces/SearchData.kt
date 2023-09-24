package dong.duan.ecommerce.interfaces

import dong.duan.ecommerce.model.Manufacturer

interface SearchManufactData {
    fun onHomeSearch(manufacturer: Manufacturer)
}