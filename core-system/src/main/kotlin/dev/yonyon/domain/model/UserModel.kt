package dev.yonyon.domain.model

import java.time.OffsetDateTime
import java.util.*

/**
 * ユーザーモデル
 */
class UserModel(
    /**
     * ユーザーID
     */
    val id: UUID,

    /**
     * ファーストネーム
     */
    val firstName: String,

    /**
     * ラストネーム
     */
    val lastName: String,

    /**
     * ニックネーム
     */
    val nickName: String,

    /**
     * メールアドレス
     */
    val email: String,

    /**
     * パスワード
     */
    val password: String,

    /**
     * 登録日
     */
    val registeredAt: OffsetDateTime? = null

)
