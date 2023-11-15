package com.example.project_p3l_mobile.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_p3l_mobile.R
import com.example.project_p3l_mobile.data_api.model.TransaksiKamarData
import com.example.project_p3l_mobile.data_api.model.TransaksiLayananData

class RV_LayananDiPesan (private  val data: List<TransaksiLayananData>) : RecyclerView.Adapter<RV_LayananDiPesan.viewHolder>() {
    var onItemClick: ((TransaksiLayananData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_layanan_di_pesan, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvJudul.text = "${currentItem.layanans?.nama_layanan}"
        holder.tvHargaSatuan.text = "Rp ${currentItem.layanans?.harga}"
        holder.tvJumlah.text = "x${currentItem.jumlah}"
        holder.tvHargaTotal.text = "Rp ${currentItem.total_harga}"


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
        val tvJudul : TextView = itemView.findViewById(R.id.textJudulLayananDiPesan)
        val tvHargaSatuan : TextView = itemView.findViewById(R.id.textHargaSatuanLayananDiPesan)
        val tvJumlah : TextView = itemView.findViewById(R.id.textJumlahLayananDiPesan)
        val tvHargaTotal : TextView = itemView.findViewById(R.id.textHargaTotalLayananDiPesan)
    }
}