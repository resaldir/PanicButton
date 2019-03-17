package com.github.saldiwe.panicbutton.data

import androidx.room.*

@Dao
interface InstansiDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInstansi(instansi: Instansi): Long

    @Query("SELECT * FROM t_instansi")
    fun selectAllInstansi(): List<Instansi>

    @Query("DELETE FROM t_instansi")
    fun deleteAllInstansi()
}