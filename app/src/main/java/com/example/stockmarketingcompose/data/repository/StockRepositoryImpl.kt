package com.example.stockmarketingcompose.data.repository

import com.example.stockmarketingcompose.data.csv.CsvParser
import com.example.stockmarketingcompose.data.local.StockDatabase
import com.example.stockmarketingcompose.data.mapper.toCompanyInfo
import com.example.stockmarketingcompose.data.mapper.toCompanyListing
import com.example.stockmarketingcompose.data.mapper.toCompanyListingEntity
import com.example.stockmarketingcompose.data.remote.StockAPI
import com.example.stockmarketingcompose.domain.model.CompanyInfo
import com.example.stockmarketingcompose.domain.model.CompanyListing
import com.example.stockmarketingcompose.domain.model.IntradayInfo
import com.example.stockmarketingcompose.domain.repository.StockRepository
import com.example.stockmarketingcompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockAPI,
    private val db: StockDatabase,
    private val companyListingsParser: CsvParser<CompanyListing>,
    private val intradayInfoParser: CsvParser<IntradayInfo>
) : StockRepository {

    private val dao = db.dao
    override suspend fun getCompanyListener(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow<Resource<List<CompanyListing>>> {
            emit(Resource.Loading(true))
            val localListing = dao.searchCompanyListing(query)
            emit(Resource.Success(data = localListing.map { it.toCompanyListing() }))

            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading())
                return@flow
            }

            val remoteListings = try {
                val response = api.getListing()
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }


            remoteListings?.let { listCompanyListings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listCompanyListings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(isLoading = false))
            }


        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getInteradayInfo(symbol = symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load intraday info...")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load intraday info...")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load company info...")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load company info...")
        }
    }

}