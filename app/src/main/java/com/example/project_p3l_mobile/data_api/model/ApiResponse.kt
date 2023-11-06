package com.example.project_p3l_mobile.data_api.model

import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:HashMap<String, List<String>>? //eg: {"email": ["The email has already been taken."]},
){
    override fun toString(): String {
        var errors = ""
        this.message?.forEach { (key, value) ->
            errors += "$key: $value[0]\n"
        }
        return errors
    }
}
data class ApiErrorResponseMessageAsString(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String
)

data class ResponseBase(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String,
)

data class ResponseDataLoginCustomer(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String,
    val totaldata: Int,
    val data: ResponseDataLoginCustomerBagianData
)
data class ResponseDataLoginPegawai(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String,
    val totaldata: Int,
    val data: ResponseDataLoginPegawaiBagianData
)

data class ResponseDataLoginCustomerBagianData(
    val user: CustomerData,
    val authorization: ResponseDataLoginBagianDataAuthorization
)
data class ResponseDataLoginPegawaiBagianData(
    val user: PegawaiData,
    val authorization: ResponseDataLoginBagianDataAuthorization
)

data class ResponseDataLoginBagianDataAuthorization(
    val token: String,
    val type: String
)

data class ResponseDataProfile(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String,
    val totaldata: Int,
    val data: CustomerData
)

data class ResponseDataRegistrasi(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String,
    val totaldata: Int,
    val user:List<CustomerData>
)

data class PegawaiData(
    @SerializedName("id") val id:Int,
    @SerializedName("id_role") val id_role:Int,
    @SerializedName("nama_pegawai") val nama_pegawai:String,
    @SerializedName("email") val email:String,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("updated_by") val updated_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
    @SerializedName("role") val role:RoleData?,
)

data class RoleData(
    @SerializedName("id") val id:Int,
    @SerializedName("nama_role") val nama_role:String,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("updated_by") val updated_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
)

data class CustomerData(
    @SerializedName("id") val id:Int,
    @SerializedName("jenis_customer") val jenis_customer:String,
    @SerializedName("nama_customer") val nama_customer:String,
    @SerializedName("no_identitas") val no_identitas:String,
    @SerializedName("jenis_identitas") val jenis_identitas:String,
    @SerializedName("no_telp") val no_telp:String,
    @SerializedName("email") val email:String,
    @SerializedName("alamat") val alamat:String,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
)

data class ResponseDataJenisKamar(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String,
    val totaldata: Int,
    val data:List<JenisKamarData>
)

data class ResponseDataJenisKamarById(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String,
    val totaldata: Int,
    val data:JenisKamarData
)

data class JenisKamarData(
    @SerializedName("id") val id:Int,
    @SerializedName("jenis_kamar") val jenis_kamar:String,
    @SerializedName("ukuran_kamar") val ukuran_kamar:Int,
    @SerializedName("fasilitas_kamar") val fasilitas_kamar:String,
    @SerializedName("deskripsi") val deskripsi:String,
    @SerializedName("kapasitas") val kapasitas:Int,
    @SerializedName("harga_dasar") val harga_dasar:Int,
    @SerializedName("gambar") val gambar:String,
    @SerializedName("flag_stat") val flag_stat:Int,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_by") val updated_by:String?,
    @SerializedName("updated_at") val updated_at:String?,
)

data class FrontOfficeData(
    @SerializedName("id") val id:Int,
    @SerializedName("id_role") val id_role:Int,
    @SerializedName("nama_pegawai") val nama_pegawai:String,
    @SerializedName("email") val email:String,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("updated_by") val updated_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
)

data class SMData(
    @SerializedName("id") val id:Int,
    @SerializedName("id_role") val id_role:Int,
    @SerializedName("nama_pegawai") val nama_pegawai:String,
    @SerializedName("email") val email:String,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("updated_by") val updated_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
)

data class TransaksiReservasiData(
    @SerializedName("id") val id:Int,
    @SerializedName("id_customer") val id_customer:Int,
    @SerializedName("id_booking") val id_booking:String?,
    @SerializedName("id_pic") val id_pic:Int?,
    @SerializedName("id_fo") val id_fo:Int?,
    @SerializedName("jumlah_dewasa") val jumlah_dewasa:Int,
    @SerializedName("jumlah_anak_anak") val jumlah_anak_anak:Int,
    @SerializedName("req_layanan") val req_layanan:String?,
    @SerializedName("waktu_check_in") val waktu_check_in:String,
    @SerializedName("waktu_check_out") val waktu_check_out:String,
    @SerializedName("jumlah_malam") val jumlah_malam:Int,
    @SerializedName("total_harga") val total_harga:Int,
    @SerializedName("waktu_pembayaran") val waktu_pembayaran:String?,
    @SerializedName("waktu_reservasi") val waktu_reservasi:String,
    @SerializedName("uang_jaminan") val uang_jaminan:Int?,
    @SerializedName("status") val status:String,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("updated_by") val updated_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
    @SerializedName("pic") val pic: SMData?,
    @SerializedName("fo") val fo: FrontOfficeData?,
)
data class HistoryTransaksiData(
    @SerializedName("id") val id:Int,
    @SerializedName("jenis_customer") val jenis_customer:String,
    @SerializedName("nama_customer") val nama_customer:String,
    @SerializedName("nama_institusi") val nama_institusi:String? ,
    @SerializedName("no_identitas") val no_identitas:String,
    @SerializedName("jenis_identitas") val jenis_identitas:String,
    @SerializedName("no_telp") val no_telp:String,
    @SerializedName("email") val email:String,
    @SerializedName("alamat") val alamat:String,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
    @SerializedName("trx_reservasis") val trx_reservasis:List<TransaksiReservasiData>
)

data class ResponseDataHistoryByIdCust(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String,
    val totaldata: Int,
    val data:HistoryTransaksiData
)


