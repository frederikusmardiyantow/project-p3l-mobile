package com.example.project_p3l_mobile.adapter

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_p3l_mobile.DetailKamarActivity
import com.example.project_p3l_mobile.R
import com.example.project_p3l_mobile.data_api.model.KetersediaanKamarData

class RV_KetersediaanKamarAdapter (private  val data: List<KetersediaanKamarData>) : RecyclerView.Adapter<RV_KetersediaanKamarAdapter.viewHolder>() {
    var onItemClick: ((KetersediaanKamarData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_ketersediaan_kamar, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.jenis_kamars.gambar)
            .into(holder.tvGambar)
        holder.tvJudul.text = "Kamar ${currentItem.jenis_kamar}"
        holder.tvSisaKamar.text = "Tersisa : ${currentItem.jumlah_kamar.toString()} Kamar"
        holder.tvUkuran.text = "${currentItem.jenis_kamars.ukuran_kamar} m²"
        holder.tvKapasitas.text = "${currentItem.jenis_kamars.kapasitas} Orang"
        holder.tvFasilitas.text = "${currentItem.jenis_kamars.fasilitas_kamar}"
        holder.tvHargaLama.text = "Rp ${currentItem.harga_dasar}"
        holder.tvHargaKamar.text = "Rp ${currentItem.harga_saat_ini}"

        holder.tvHargaLama.paintFlags = holder.tvHargaLama.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(currentItem)
            val intent = Intent(holder.itemView.context, DetailKamarActivity::class.java)
            intent.putExtra("id", currentItem.jenis_kamars.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        //disini kita memberitahu jumlah dari item pda recycler view kita
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvGambar : ImageView = itemView.findViewById(R.id.gambarKamar)
        val tvJudul : TextView = itemView.findViewById(R.id.textJudulKamar)
        val tvSisaKamar : TextView = itemView.findViewById(R.id.textSisaKamar)
        val tvUkuran : TextView = itemView.findViewById(R.id.textUkuranKamar)
        val tvKapasitas : TextView = itemView.findViewById(R.id.textKapasitas)
        val tvFasilitas : TextView = itemView.findViewById(R.id.textFasilitas)
        val tvHargaLama : TextView = itemView.findViewById(R.id.textHargaLama)
        val tvHargaKamar : TextView = itemView.findViewById(R.id.textHargaKamar)
    }
}