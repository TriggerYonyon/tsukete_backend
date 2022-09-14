package dev.yonyon.api.controller

import dev.yonyon.application.query_service.ShopWithSheetsResult
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ShopRestControllerTest : BaseRestControllerTest() {
    // API PATH
    private val basePath = "/api/shops"
    private val getShopsPath = basePath

    @Test
    fun successToGetShops() {
        connection.prepareStatement(
            """
                INSERT INTO public.shops (id, name, zipcode, prefecture, locality, street, building, has_parking, non_smoking, image_url, registered_at) 
                    VALUES ('c323658e-8e15-45ea-b3db-88103d27aae2', 'yonyon食堂', '1500001', 13, '渋谷区', '神宮前5-52-2', '青山オーバルビル 3階', false, true, 'no image', DEFAULT)
            """.trimIndent()
        ).execute()
        connection.prepareStatement(
            """
                INSERT INTO public.seats (id, shop_id, type, capacity, has_outlet, is_near_air_conditioner, is_used, registered_at) 
                    VALUES ('65f20e20-46e7-48af-8995-b9e7bcd6f693', 'c323658e-8e15-45ea-b3db-88103d27aae2', 0, 5, true, true, false, '2022-09-12 18:39:46.613012 +00:00')
            """.trimIndent()
        ).execute()
        connection.prepareStatement(
            """
                INSERT INTO public.seats (id, shop_id, type, capacity, has_outlet, is_near_air_conditioner, is_used, registered_at) 
                    VALUES ('483d14ad-28af-4a5c-8338-e62c134fccf5', 'c323658e-8e15-45ea-b3db-88103d27aae2', 1, 10, false, false, true, '2022-09-12 18:40:09.028528 +00:00')
            """.trimIndent()
        ).execute()

        val accessToken = this.login()
        val request = HttpRequest.GET<String>(getShopsPath).bearerAuth(accessToken)
        val response = client.toBlocking().exchange(request, Argument.listOf(ShopWithSheetsResult::class.java))

        Assertions.assertEquals(HttpStatus.OK, response.status)
        val body = response.body.get()

        Assertions.assertEquals(1, body.size)
        Assertions.assertEquals(2, body[0].seats.size)
    }

}
