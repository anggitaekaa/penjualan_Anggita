package com.example.penjualan_anggitaekadamayanti.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tbBarang (
    @PrimaryKey(autoGenerate = true)
    val id_barang: Int,
    val nama_barang: String,
    val harga_barang: Int,
    val jumlah_barang: Int
        )