package com.example.spendtrackernotes.data

class SpendsTrackerDataSource(
    private val db: SpendDao
) {
    suspend fun addSpend(spend: Spend) = db.addSpend(spend)

    suspend fun getLast50Spends() = db.getLast50Spends()
}