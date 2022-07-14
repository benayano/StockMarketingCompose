package com.example.stockmarketingcompose.domain.repository

import com.example.stockmarketingcompose.domain.model.CompanyInfo
import com.example.stockmarketingcompose.domain.model.CompanyListing
import com.example.stockmarketingcompose.domain.model.IntradayInfo
import com.example.stockmarketingcompose.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListener(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}