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

data class KetersediaanKamarData(
    @SerializedName("id_jenis_kamar") val id_jenis_kamar: String,
    @SerializedName("jumlah_kamar") val jumlah_kamar: Int,
    @SerializedName("jenis_kamar") val jenis_kamar: String,
    @SerializedName("harga_dasar") val harga_dasar: String,
    @SerializedName("nama_season") val nama_season: String,
    @SerializedName("perubahan_tarif") val perubahan_tarif: String,
    @SerializedName("jenis_season") val jenis_season: String,
    @SerializedName("harga_saat_ini") val harga_saat_ini: Int,
    @SerializedName("jenis_kamars") val jenis_kamars: JenisKamarData,
)

data class ResponseDataKetersediaanKamar(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String,
    val totaldata: Int,
    val data:List<KetersediaanKamarData>
)

data class LayananData(
    @SerializedName("id") val id:Int,
    @SerializedName("nama_layanan") val nama_layanan:String,
    @SerializedName("harga") val harga:String,
    @SerializedName("satuan") val satuan:String,
    @SerializedName("keterangan") val keterangan:String,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("updated_by") val updated_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
)

data class TransaksiLayananData(
    @SerializedName("id") val id:Int,
    @SerializedName("id_layanan") val id_layanan:String,
    @SerializedName("id_trx_reservasi") val id_trx_reservasi:String,
    @SerializedName("jumlah") val jumlah:Int,
    @SerializedName("total_harga") val total_harga:String,
    @SerializedName("waktu_pemakaian") val waktu_pemakaian:String?,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("updated_by") val updated_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
    @SerializedName("layanans") val layanans: LayananData?,
)

data class KamarData(
    @SerializedName("id") val id:Int,
    @SerializedName("id_jenis_kamar") val id_jenis_kamar:String,
    @SerializedName("nomor_kamar") val nomor_kamar:String,
    @SerializedName("jenis_bed") val jenis_bed:String,
    @SerializedName("nomor_lantai") val nomor_lantai:String,
    @SerializedName("smoking_area") val smoking_area:String,
    @SerializedName("catatan") val catatan:String,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("updated_by") val updated_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
)

data class TransaksiKamarData(
    @SerializedName("id") val id:Int,
    @SerializedName("id_jenis_kamar") val id_jenis_kamar:String,
    @SerializedName("id_trx_reservasi") val id_trx_reservasi:String,
    @SerializedName("harga_per_malam") val harga_per_malam:String,
    @SerializedName("flag_stat") val flag_stat:String,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("updated_by") val updated_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
    @SerializedName("jenis_kamars") val jenis_kamars: JenisKamarData?,
    @SerializedName("kamars") val kamars: KamarData?,
)

data class InvoiceData(
    @SerializedName("id") val id:Int,
    @SerializedName("no_invoice") val no_invoice:String,
    @SerializedName("id_trx_reservasi") val id_trx_reservasi:String,
    @SerializedName("id_pegawai") val id_pegawai:String,
    @SerializedName("tgl_lunas") val tgl_lunas:String,
    @SerializedName("total_harga_kamar") val total_harga_kamar:String,
    @SerializedName("total_harga_layanan") val total_harga_layanan:String,
    @SerializedName("pajak_layanan") val pajak_layanan:String,
    @SerializedName("total_semua") val total_semua:String,
    @SerializedName("created_by") val created_by:String?,
    @SerializedName("created_at") val created_at:String?,
    @SerializedName("updated_at") val updated_at:String?,
    @SerializedName("pegawais") val pegawais: PegawaiData?,
)
data class DetailTrxReservasiData(
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
    @SerializedName("customers") val customers:CustomerData?,
    @SerializedName("pic") val pic: SMData?,
    @SerializedName("fo") val fo: FrontOfficeData?,
    @SerializedName("trx_layanans") val trx_layanans:List<TransaksiLayananData>?,
    @SerializedName("trx_kamars") val trx_kamars:List<TransaksiKamarData>?,
    @SerializedName("invoices") val invoices:List<InvoiceData>?,
)

data class ResponseDataDetailHistory(
    @SerializedName("status") val status:String,
    @SerializedName("message") val message:String,
    val totaldata: Int,
    val data:DetailTrxReservasiData
)

data class DetailLaporanCustBaru(
    @SerializedName("no") val no:Int,
    @SerializedName("bulan") val bulan:String,
    @SerializedName("tahun") val tahun:String,
    @SerializedName("jumlah_customer") val jumlah_customer:Int,
)
data class DataLaporanCustBaru(
    @SerializedName("total_customer") val total_customer:Int,
    val laporan: List<DetailLaporanCustBaru>
)
data class ResponseDataLaporanCustBaruPerBulan(
    @SerializedName("status") val status:String,
    val data:DataLaporanCustBaru
)

data class DetailLaporan5Cust(
    @SerializedName("id_customer") val id_customer:Int,
    @SerializedName("nama_customer") val nama_customer:String,
    @SerializedName("jumlah_reservasi") val jumlah_reservasi:Int,
    @SerializedName("total_harga") val total_harga:String,
)
data class ResponseDataLaporan5Cust(
    @SerializedName("status") val status:String,
    val data:List<DetailLaporan5Cust>
)


