package com.example.stockmarketingcompose.data.csv

import android.annotation.SuppressLint
import com.example.stockmarketingcompose.data.mapper.toItradayInfo
import com.example.stockmarketingcompose.data.remote.dto.IntradayInfoDto
import com.example.stockmarketingcompose.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CsvParser<IntradayInfo> {

    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timeTamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(timeTamp, close.toDouble())
                    dto.toItradayInfo()
                }
                .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(3).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }

    }

}