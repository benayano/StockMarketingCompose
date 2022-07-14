package com.example.stockmarketingcompose.presentation.company_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stockmarketingcompose.ui.theme.DarkBlue
import com.ramcosta.composedestinations.annotation.Destination


@Composable
@Destination
fun CompanyInfoScreen(
    symbol: String,
    viewModel: CompanyInfoViewModel = hiltViewModel()
) {
    val state = viewModel.state
    if (state.error == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(16.dp)
        ) {
            state.companyInfo?.let { companyInfo ->
                Text(
                    text = companyInfo.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                spacer(8)
                Text(
                    text = companyInfo.symbol,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                sds()
                textInfoParameter("Industry :${companyInfo.industry}")
                spacer(8)
                textInfoParameter("Country ${companyInfo.country}")
                sds()
                Text(
                    text = companyInfo.description,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                if (state.stockInfos.isNotEmpty()) {
                    spacer(16)
                    Text(text = "marker Summary")
                    spacer(height = 32)
                    StockChart(
                        infos = state.stockInfos,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .align(CenterHorizontally)
                    )

                }


            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(text = state.error, color = MaterialTheme.colors.error)
        }
    }
}

@Composable
private fun sds() {
    spacer(8)
    divider()
    spacer(8)
}

@Composable
private fun textInfoParameter(infoParaneter: String) {
    Text(
        text = infoParaneter,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth(),
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun divider() {
    Divider(modifier = Modifier.fillMaxWidth())
}

@Composable
private fun spacer(height: Int) {
    Spacer(modifier = Modifier.height(height.dp))
}















