package com.example.penjualan_anggitaekadamayanti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.penjualan_anggitaekadamayanti.room.Constant
import com.example.penjualan_anggitaekadamayanti.room.dbPenjualan
import com.example.penjualan_anggitaekadamayanti.room.tbBarang
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataActivity : AppCompatActivity() {
    val db by lazy { dbPenjualan(this) }
    private var idBarang: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        simpandata()
        setupView()
        idBarang = intent.getIntExtra("intent_id", 0)
        Toast.makeText(this,idBarang.toString(), Toast.LENGTH_SHORT).show()
    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType= intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                imupdate.visibility = View.GONE

            }
            Constant.TYPE_READ -> {
                imsave.visibility = View.GONE
                imupdate.visibility = View.GONE
                getTampilid()
            }
            Constant.TYPE_UPDATE -> {
                imsave.visibility = View.GONE
                getTampilid()
            }
        }
    }
    fun simpandata(){
        imsave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbbrgDao().addtbBarang(
                    tbBarang(etId.text.toString().toInt(),etnama.text.toString(),etharga.text.toString().toInt(),etjumlah.text.toString().toInt())
                )
                finish()
            }
        }
        imupdate.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.tbbrgDao().updatetbBarang(
                    tbBarang(etId.text.toString().toInt(), etnama.text.toString(), etjumlah.text.toString().toInt(), etharga.text.toString().toInt())
                )
                finish()
            }
        }
    }
    fun getTampilid(){
        idBarang = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val brg = db.tbbrgDao().tampilid(idBarang)[0]
            val id: String = brg.id_barang.toString()
            val harga: String = brg.harga_barang.toString()
            val jumlah: String = brg.jumlah_barang.toString()
            etId.setText(id)
            etnama.setText(brg.nama_barang)
            etharga.setText(harga)
            etjumlah.setText(jumlah)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}