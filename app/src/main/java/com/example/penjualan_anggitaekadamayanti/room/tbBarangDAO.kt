package com.example.penjualan_anggitaekadamayanti.room

import androidx.room.*


@Dao
interface tbBarangDAO {
    @Insert
    fun addtbBarang(tbbrg:tbBarang)

    @Update
    fun updatetbBarang(tbbrg: tbBarang)

    @Delete
    fun deletetbBarang(tbbrg: tbBarang)

    @Query("SELECT * FROM tbBarang")
    fun tampilsemua(): List<tbBarang>

    @Query("SELECT * FROM tbBarang WHERE id_barang=:id_barang")
    fun tampilid(id_barang: Int) : List<tbBarang>

}