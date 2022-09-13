package dev.yonyon.enums

enum class PrefectureEnum(val id: Int, val prefectureName: String) {

    /**
     * 東京
     */
    TOKYO(13, "東京都");

    /**
     * idからEnumを取得
     */
    companion object {
        fun findById(id: Int): PrefectureEnum? {
            return values().firstOrNull { it.id == id }
        }
    }

}
