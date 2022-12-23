package ie.wit.placemark.models

interface PlacemarkStore {
    fun findAll(): List<PlacemarkModel>
    fun create(placemark: PlacemarkModel)
    fun update(placemark: PlacemarkModel)
    fun findById(id:Long) : PlacemarkModel?
    fun delete(placemark: PlacemarkModel)
}