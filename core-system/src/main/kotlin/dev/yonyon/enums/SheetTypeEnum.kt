package dev.yonyon.enums

enum class SheetTypeEnum(val id: Int, val type: String) {

    /**
     * テーブル
     */
    TABLE(0, "テーブル"),

    /**
     * カウンター
     */
    COUNTER(1, "カウンター");

    companion object {
        fun findById(id: Int): SheetTypeEnum? {
            return values().firstOrNull { it.id == id }
        }
    }

}
