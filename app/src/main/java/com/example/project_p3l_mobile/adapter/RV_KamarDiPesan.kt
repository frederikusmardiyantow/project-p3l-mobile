package com.example.project_p3l_mobile.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_p3l_mobile.DetailHistoryActivity
import com.example.project_p3l_mobile.R
import com.example.project_p3l_mobile.data_api.model.TransaksiKamarData
import com.example.project_p3l_mobile.data_api.model.TransaksiReservasiData

class RV_KamarDiPesan (private  val data: List<TransaksiKamarData>) : RecyclerView.Adapter<RV_KamarDiPesan.viewHolder>() {
    var onItemClick: ((TransaksiKamarData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_kamar_di_pesan, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.jenis_kamars?.gambar)
            .into(holder.tvGambar)
        holder.tvJudulKamar.text = "${currentItem.jenis_kamars?.jenis_kamar}"
        holder.tvHargaKamar.text = "Rp ${currentItem.harga_per_malam}"

        Log.d("RV_KamarDiPesan", currentItem.jenis_kamars?.jenis_kamar.toString())

//        holder.itemView.setOnClickListener{
//            onItemClick?.invoke(currentItem)
//            val intent = Intent(holder.itemView.context, DetailHistoryActivity::class.java)
//            intent.putExtra("id", currentItem.id)
//            holder.itemView.context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        //disini kita memberitahu jumlah dari item pda recycler view kita
        return data.size ?: 0
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvGambar : ImageView = itemView.findViewById(R.id.gambarKamarDiPesan)
        val tvJudulKamar : TextView = itemView.findViewById(R.id.textJudulKamarDiPesan)
        val tvHargaKamar : TextView = itemView.findViewById(R.id.textHargaKamarDiPesan)
    }
}