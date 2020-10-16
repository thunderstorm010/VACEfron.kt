@file:Suppress("unused")

package me.thunderstorm.api.vacefron

import com.eclipsesource.json.Json
import me.thunderstorm.commons.api.ApiUtil
import okhttp3.ResponseBody
import okhttp3.internal.toHexString
import java.awt.Color
import java.io.ByteArrayInputStream
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class VACEfron : ApiUtil() {
    override var baseUrl = "https://vacefron.nl/api/"
    override var userAgent: String = "VACEfron.kt"

    override fun parseErr(body: ResponseBody) {
        val obj = Json.parse(body.string()).asObject()
        throw Exception("Error: ${obj["message"]} (${obj["code"]})")
    }

    /**
     * Encodes text to url format.
     * @param t Text to encode
     */
    private fun enc(t: String): String = URLEncoder.encode(t, StandardCharsets.UTF_8.toString())

    fun carReverse(text: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "carreverse",
        arguments = mutableMapOf(
            Pair("text", enc(text))
        )
    )

    fun changeMyMind(text: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "changemymind",
        arguments = mutableMapOf(
            Pair("text", enc(text))
        )
    )

    fun distractedBoyfriend(
        boyfriendAvatar: String,
        womanAvatar: String,
        girlfriendAvatar: String
    ): ByteArrayInputStream =
        this.makeImageRequest(
            endpoint = "distractedbf",
            mutableMapOf(
                Pair("boyfriend", enc(boyfriendAvatar)),
                Pair("woman", enc(womanAvatar)),
                Pair("girlfriend", enc(girlfriendAvatar))
            )
        )

    //region Color enum
    enum class CrewmateColor {
        BLACK,
        BLUE,
        BROWN,
        CYAN,
        DARKGREEN,
        LIME,
        ORANGE,
        PINK,
        PURPLE,
        RED,
        WHITE,
        YELLOW
    }
    //endregion

    fun ejected(name: String, impostor: Boolean, color: CrewmateColor): ByteArrayInputStream =
        this.makeImageRequest(
            "ejected", mutableMapOf(
                Pair("name", enc(name)),
                Pair("impostor", enc(impostor.toString())),
                Pair("crewmate", enc(color.toString().toLowerCase()))
            )
        )

    fun emergencyMeeting(text: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "emergencymeeting",
        arguments = mutableMapOf(
            Pair("text", enc(text))
        )
    )

    fun firstTime(userAvatar: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "firsttime",
        arguments = mutableMapOf(
            Pair("user", enc(userAvatar))
        )
    )

    fun grave(userAvatar: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "grave",
        arguments = mutableMapOf(
            Pair("user", enc(userAvatar))
        )
    )

    fun iAmSpeed(userAvatar: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "iamspeed",
        arguments = mutableMapOf(
            Pair("user", enc(userAvatar))
        )
    )

    fun iCanMilkYou(userAvatar1: String, userAvatar2: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "icanmilkyou",
        arguments = mutableMapOf(
            Pair("user1", enc(userAvatar1)),
            Pair("user2", enc(userAvatar2))
        )
    )

    fun heaven(userAvatar: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "heaven",
        arguments = mutableMapOf(
            Pair("user", enc(userAvatar))
        )
    )

    fun npc(text1: String, text2: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "npc",
        arguments = mutableMapOf(
            Pair("text1",enc(text1)),
            Pair("text2",enc(text2))
        )
    )

    private fun Color.toHex(): String = "${this.red.toHexString()}${this.green.toHexString()}${this.blue.toHexString()}"

    fun rankCard(username: String, avatar: String, level: Int, rank: Int, currentXp: Int, nextLevelXp: Int, previousLevelXp: Int, customBackground: URL?, xpColor: Color?, isBoosting: Boolean?): ByteArrayInputStream {
        val args = mutableMapOf(
            Pair("username", enc(username)),
            Pair("avatar", enc(avatar)),
            Pair("level", enc(level.toString())),
            Pair("rank", enc(rank.toString())),
            Pair("currentxp", enc(currentXp.toString())),
            Pair("nextlevelxp", enc(nextLevelXp.toString())),
            Pair("previouslevelxp", enc(previousLevelXp.toString()))
        )
        if (customBackground != null) {
            args["custombg"] = enc(customBackground.toString())
        }
        if (xpColor != null) {
            args["xpcolor"] = enc(xpColor.toHex())
        }
        if (isBoosting != null) {
            args["isboosting"] = enc(isBoosting.toString())
        }
        return this.makeImageRequest(
            endpoint = "rankcard",
            arguments = args
        )
    }

    fun stonks(userAvatar: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "stonks",
        arguments = mutableMapOf(
            Pair("user",enc(userAvatar))
        )
    )

    fun tableFlip(userAvatar: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "tableflip",
        arguments = mutableMapOf(
            Pair("user",enc(userAvatar))
        )
    )

    fun water(text: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "water",
        arguments = mutableMapOf(
            Pair("text",enc(text))
        )
    )

    fun wide(imageUrl: String): ByteArrayInputStream = this.makeImageRequest(
        endpoint = "wide",
        arguments = mutableMapOf(
            Pair("image",enc(imageUrl))
        )
    )

}