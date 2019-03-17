package com.github.saldiwe.panicbutton.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "t_instansi")
data class Instansi(
    @PrimaryKey(autoGenerate = true)
    var instansiId: Int?,

    @ColumnInfo(name = "icon")
    var icon: Int?,

    @ColumnInfo(name = "nama_instansi")
    var namaInstansi: String,

    @ColumnInfo(name = "nomor_instansi")
    var nomorInstansi: String
){
    constructor():this(null, null, "", "")
}