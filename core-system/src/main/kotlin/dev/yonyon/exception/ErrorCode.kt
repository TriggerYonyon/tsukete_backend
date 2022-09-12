package dev.yonyon.exception

import io.micronaut.http.HttpStatus

enum class ErrorCode(val code: Int, val message: String) {

    /**
     * 401
     */
    INVALID_CERTIFICATIONS(HttpStatus.UNAUTHORIZED.code, "認証情報が間違っています。"),

    /**
     * 404
     */
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.code, "ユーザーが見つかりません。"),

    /**
     * 500
     */
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.code, "不明なエラーが発生しました。問題が解決しない場合は管理者にお問い合わせください。")

}
