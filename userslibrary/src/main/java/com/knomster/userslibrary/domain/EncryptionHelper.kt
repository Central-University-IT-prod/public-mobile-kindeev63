package com.knomster.userslibrary.domain

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionHelper {

    fun generateKey(password: String): SecretKeySpec {
        val sha = MessageDigest.getInstance("SHA-256")
        val key = sha.digest(password.toByteArray(Charsets.UTF_8))
        val key192 = key.copyOf(24)
        return SecretKeySpec(key192, "AES")
    }

    fun encryptPassword(data: String, key: SecretKeySpec): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = ByteArray(16)
        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
        val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(iv + encryptedData, Base64.DEFAULT)
    }

    fun decryptPassword(data: String, key: SecretKeySpec): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val decodedData = Base64.decode(data, Base64.DEFAULT)
        val iv = decodedData.copyOfRange(0, 16) // Извлекаем IV из начала данных
        val encryptedData = decodedData.copyOfRange(16, decodedData.size)
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        return String(cipher.doFinal(encryptedData), Charsets.UTF_8)
    }
}