package com.example.stockmarketingcompose.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    @PrimaryKey(autoGenerate = true)
    val id :Int? = null,
    val name: String,
    val symbol: String,
    val exchange: String
)