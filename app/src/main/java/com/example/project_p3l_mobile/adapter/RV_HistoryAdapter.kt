package com.example.project_p3l_mobile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project_p3l_mobile.DetailHistoryActivity
import com.example.project_p3l_mobile.R
import com.example.project_p3l_mobile.data_api.model.HistoryTransaksiData
import com.example.project_p3l_mobile.data_api.model.JenisKamarData
import com.example.project_p3l_mobile.data_api.model.TransaksiReservasiData

class RV_HistoryAdapter (private  val data: List<TransaksiReservasiData>) : RecyclerView.Adapter<RV_HistoryAdapter.viewHolder>() {
    var onItemClick: ((TransaksiReservasiData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewTypes: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_riwayat_reservasi, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvJudul.text = "Transaksi #${currentItem.id_booking.toString()}"
        holder.tvWaktuPemesanan.text = "${currentItem.waktu_reservasi.toString()}"
        holder.tvWaktuCheckIn.text = "${currentItem.waktu_check_in.toString()}"
        holder.tvWaktuCheckOut.text = "${currentItem.waktu_check_out.toString()}"
        holder.tvWaktuPembayaran.text = "${currentItem.waktu_pembayaran.toString()}"
        holder.tvTotalHarga.text = "Rp ${currentItem.total_harga.toString()}"
        holder.tvStatus.text = "${currentItem.status}"
        if (holder.tvStatus.text == "Menunggu Pembayaran"){
            val color = ContextCompat.getColor(holder.itemView.context, R.color.secondary)
            holder.tvStatus.setBackgroundColor(color)
        } else if (holder.tvStatus.text == "In" || holder.tvStatus.text == "Terkonfirmasi"){
            val color = ContextCompat.getColor(holder.itemView.context, R.color.success)
            holder.tvStatus.setBackgroundColor(color)
        } else if (holder.tvStatus.text == "Batal"){
            val color = ContextCompat.getColor(holder.itemView.context, R.color.danger)
            holder.tvStatus.setBackgroundColor(color)
        }

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(currentItem)
            val intent = Intent(holder.itemView.context, DetailHistoryActivity::class.java)
            intent.putExtra("id", currentItem.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        //disini kita memberitahu jumlah dari item pda recycler view kita
        return data.size ?: 0
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvJudul : TextView = itemView.findViewById(R.id.tvJudulRiwayat)
        val tvWaktuPemesanan : TextView = itemView.findViewById(R.id.tvWaktuPemesanan)
        val tvWaktuCheckIn : TextView = itemView.findViewById(R.id.tvWaktuCheckIn)
        val tvWaktuCheckOut : TextView = itemView.findViewById(R.id.tvWaktuCheckOut)
        val tvWaktuPembayaran : TextView = itemView.findViewById(R.id.tvWaktuPembayaran)
        val tvTotalHarga : TextView = itemView.findViewById(R.id.tvTotalHargaPesanan)
        val tvStatus : TextView = itemView.findViewById(R.id.tvStatus)
    }
}