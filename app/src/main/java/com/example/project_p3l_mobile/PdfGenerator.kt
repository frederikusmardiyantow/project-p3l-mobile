//package com.example.project_p3l_mobile
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.os.Environment
//import android.view.View
//import com.itextpdf.io.image.ImageDataFactory
//import com.itextpdf.kernel.pdf.PdfWriter
//import com.itextpdf.layout.Document
//import com.itextpdf.layout.element.Image
//import java.io.ByteArrayOutputStream
//import java.io.File
//
//class PdfGenerator(private val context: Context) {
//
//    fun createPdfFromView(view: View, fileName: String) {
//        val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "$fileName.pdf")
//
//        val pdfWriter = PdfWriter(filePath)
//        val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(pdfWriter)
//        val document = Document(pdfDocument)
//
//        // Tentukan ukuran halaman saat membuat objek Document
//        document.pageSize = com.itextpdf.kernel.geom.PageSize.A4
//
//        view.measure(
//            View.MeasureSpec.makeMeasureSpec(document.pageSize.width.toInt(), View.MeasureSpec.EXACTLY),
//            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//        )
//
//        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
//
//        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        view.draw(canvas)
//
//        val image = Image(ImageDataFactory.create(bitmapToByteArray(bitmap)))
//
//        document.add(image)
//        document.close()
//    }
//
//    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        return stream.toByteArray()
//    }
//}
