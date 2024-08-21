package com.knomster.mobile_kindeev63.data.apis

import com.google.gson.Gson
import com.knomster.mobile_kindeev63.data.apis.clients.NagerRetrofitClient
import com.knomster.mobile_kindeev63.data.apis.services.NagerService
import com.knomster.mobile_kindeev63.domain.entities.responses.HolidayResponse
import com.knomster.mobile_kindeev63.domain.interfaces.NagerApi
import org.json.JSONArray
import java.util.Locale

class NagerApiImpl: NagerApi {

    /**
     * Получение информации о следующем празднике
     */
    override fun getNextHolidays(): List<HolidayResponse>? {
        try {
            val client = NagerRetrofitClient.getClient("https://date.nager.at/")
            val nagerService = client.create(NagerService::class.java)
            val call = nagerService.getNextHolidays(Locale.getDefault().language)
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                val jsonArray = JSONArray(data)
                val results = mutableListOf<HolidayResponse>()
                val gson = Gson()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val myData = gson.fromJson(jsonObject.toString(), HolidayResponse::class.java)
                    results.add(myData)
                }
                return results
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }
}