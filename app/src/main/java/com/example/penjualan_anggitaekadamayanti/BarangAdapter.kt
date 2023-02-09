package com.example.penjualan_anggitaekadamayanti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualan_anggitaekadamayanti.room.tbBarang
import kotlinx.android.synthetic.main.activity_barang_adapter.view.*

class BarangAdapter (private val brg: ArrayList<tbBarang>, private val listener: OnadapterListener) : RecyclerView.Adapter<BarangAdapter.barangViewHolder>() {
    class barangViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<tbBarang>) {
        brg.clear()
        brg.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): barangViewHolder{
        return barangViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_barang_adapter, parent,false)
        )
    }

    override fun onBindViewHolder(holder: barangViewHolder, position: Int) {
        val barang = brg[position]
        holder.view.tvnama.text = barang.nama_barang
        holder.view.tvharga.text = barang.harga_barang.toString()
        //mengklik
        holder.view.tvnama.setOnClickListener{
            listener.onClick(barang)
        }
        holder.view.imedit.setOnClickListener{
            listener.onUpdate(barang)
        }
        holder.view.imdelete.setOnClickListener{
            listener.onDelete(barang)
        }
    }

    override fun getItemCount() = brg.size

    interface OnadapterListener{
     fun onClick(tbbarang: tbBarang)
     fun onUpdate(tbbarang: tbBarang)
     fun onDelete(tbbarang: tbBarang)
    }

}
