package com.github.saldiwe.panicbutton.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Instansi::class)], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun instansiDAO(): InstansiDAO
}