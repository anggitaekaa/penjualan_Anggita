package com.example.penjualan_anggitaekadamayanti.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.locks.Lock


@Database(entities = [tbBarang::class], version = 1)
abstract class dbPenjualan : RoomDatabase(){
    abstract fun tbbrgDao() : tbBarangDAO

    companion object{
        @Volatile private var instance: dbPenjualan?= null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(Lock){
            instance?: buildDatabase(context).also{
                instance = it
            }
        }
        private fun buildDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            dbPenjualan::class.java,
            "smksa.db"
        ).build()
    }
}