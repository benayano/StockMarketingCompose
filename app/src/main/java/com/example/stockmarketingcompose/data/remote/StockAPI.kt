package com.example.stockmarketingcompose.data.remote

import com.example.stockmarketingcompose.BuildConfig
import com.example.stockmarketingcompose.data.remote.StockAPI.Companion.API_KEY
import com.example.stockmarketingcompose.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListing(
        @Query("apikey")
        apikey: String = API_KEY
    ): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getInteradayInfo(
        @Query("symbol")
        symbol: String ,
        @Query("apikey")
        apikey: String = API_KEY
    ):ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getInteradayInfoFullTime(
        @Query("symbol")
        symbol: String ,
        @Query("apikey")
        apikey: String = API_KEY
    ):ResponseBody

    @GET(" query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol")
        symbol: String ,
        @Query("apikey")
        apikey: String = API_KEY
    ):CompanyInfoDto

    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://alphavantage.co"
    }

}