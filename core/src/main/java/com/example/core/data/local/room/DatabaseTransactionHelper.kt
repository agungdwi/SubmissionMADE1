package com.example.core.data.local.room

import androidx.room.withTransaction

class DatabaseTransactionHelper(private val database: MovieDatabase) {
    suspend fun <T> withTransaction(block: suspend () -> T): T {
        return database.withTransaction {
            block()
        }
    }
}