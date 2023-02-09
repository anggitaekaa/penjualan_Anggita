package com.example.penjualan_anggitaekadamayanti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.penjualan_anggitaekadamayanti.room.Constant
import com.example.penjualan_anggitaekadamayanti.room.dbPenjualan
import com.example.penjualan_anggitaekadamayanti.room.tbBarang
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { dbPenjualan(this) }
    lateinit var barangAdapter: BarangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        halData()
        setupRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        loadtbBarang()

    }
    fun loadtbBarang(){
        CoroutineScope(Dispatchers.IO).launch {
            val databarang= db.tbbrgDao().tampilsemua()
            Log.d("MainActivity", "dbResponse: $databarang")
            withContext(Dispatchers.Main){
                barangAdapter.setData(databarang)
            }
        }
    }
    private fun halData() {
        btinput.setOnClickListener {
            intentData(0,Constant.TYPE_CREATE)
        }
    }

    fun intentData(idBarang: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,DataActivity::class.java)
                .putExtra("intent_id", idBarang)
                .putExtra("intent_type", intentType))
    }
    private fun setupRecyclerView() {
        barangAdapter = BarangAdapter(arrayListOf(), object : BarangAdapter.OnadapterListener {
            override fun onClick(tbbarang: tbBarang) {
                intentData(tbbarang.id_barang, Constant.TYPE_READ)
            }

            override fun onUpdate(tbbarang: tbBarang) {
                intentData(tbbarang.id_barang, Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbbarang: tbBarang) {
                deleteDialog(tbbarang)
            }

        })
        RVinput.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = barangAdapter
        }
    }
        private fun deleteDialog(tbbarang: tbBarang) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.apply {
                setTitle("konfirmasi")
                setMessage("Yakin hapus ${tbbarang.nama_barang}")
                setNegativeButton("Batal") {dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                setPositiveButton("Hapus") {dialogInterface, i ->
                    dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbbrgDao().deletetbBarang(tbbarang)
                    loadtbBarang()
                }
                }
            }
            alertDialog.show()
        }



}