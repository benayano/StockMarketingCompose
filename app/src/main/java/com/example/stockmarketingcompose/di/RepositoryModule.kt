package com.example.stockmarketingcompose.di

import com.example.stockmarketingcompose.data.csv.CompanyListingsParser
import com.example.stockmarketingcompose.data.csv.CsvParser
import com.example.stockmarketingcompose.data.csv.IntradayInfoParser
import com.example.stockmarketingcompose.data.repository.StockRepositoryImpl
import com.example.stockmarketingcompose.domain.model.CompanyListing
import com.example.stockmarketingcompose.domain.model.IntradayInfo
import com.example.stockmarketingcompose.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ):CsvParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CsvParser<IntradayInfo>


    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ):StockRepository

}