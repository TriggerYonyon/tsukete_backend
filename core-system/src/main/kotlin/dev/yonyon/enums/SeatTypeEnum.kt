package dev.yonyon.enums

enum class SeatTypeEnum(val id: Int, val type: String) {

    /**
     * テーブル
     */
    TABLE(0, "テーブル"),

    /**
     * カウンター
     */
    COUNTER(1, "カウンター");

    companion object {
        fun findById(id: Int): SeatTypeEnum? {
            return values().firstOrNull { it.id == id }
        }
    }

}
