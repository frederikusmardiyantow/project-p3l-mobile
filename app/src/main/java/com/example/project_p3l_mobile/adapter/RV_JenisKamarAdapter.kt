package com.example.project_p3l_mobile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_p3l_mobile.DetailKamarActivity
import com.example.project_p3l_mobile.R
import com.example.project_p3l_mobile.data_api.model.JenisKamarData

class RV_JenisKamarAdapter (private  val data: List<JenisKamarData>) : RecyclerView.Adapter<RV_JenisKamarAdapter.viewHolder>() {
    var onItemClick: ((JenisKamarData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_jenis_kamar, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.gambar)
            .into(holder.tvGambar)
        holder.tvJudul.text = "Kamar Spesial ${currentItem.jenis_kamar}"
        holder.tvKapasitas.text = "Kapasitas Kamar : ${currentItem.kapasitas.toString()} Dewasa"
        holder.tvUkuran.text = "Ukuran Kamar : ${currentItem.ukuran_kamar.toString()} m²"

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(currentItem)
            val intent = Intent(holder.itemView.context, DetailKamarActivity::class.java)
            intent.putExtra("id", currentItem.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        //disini kita memberitahu jumlah dari item pda recycler view kita
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvGambar : ImageView = itemView.findViewById(R.id.gambarJenis)
        val tvJudul : TextView = itemView.findViewById(R.id.textJudulJenisKamar)
        val tvKapasitas : TextView = itemView.findViewById(R.id.textKapasitas)
        val tvUkuran : TextView = itemView.findViewById(R.id.textUkuranKamar)
    }
}