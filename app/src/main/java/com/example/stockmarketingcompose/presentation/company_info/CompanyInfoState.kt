package com.example.stockmarketingcompose.presentation.company_info

import com.example.stockmarketingcompose.domain.model.CompanyInfo
import com.example.stockmarketingcompose.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val companyInfo: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null

)