package com.dicoding.asclepius.view.result

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.ImageDecoder.decodeBitmap
import android.icu.text.NumberFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.asclepius.data.local.History
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.util.ViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private lateinit var history: History

    private val viewModel: ResultViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_HISTORY, History::class.java)?.let { history = it }
        } else {
            intent.getParcelableExtra<History>(EXTRA_HISTORY)?.let { history = it }
        }
        showResult()
        binding.fab.setOnClickListener { saveToHistory() }


        binding.btnSend.setOnClickListener { sendResult(binding.resultText.text.toString()) }
    }

    private fun sendResult(result: String) {
        val imgUri = getImgUri()
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Skin cancer detector result: $result")
            putExtra(Intent.EXTRA_STREAM, imgUri)

            type = "text/plain"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        }
        startActivity(Intent.createChooser(shareIntent, null))
    }

    private fun getImgUri(): Uri {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            decodeBitmap(ImageDecoder.createSource(contentResolver, Uri.parse(history.imgUri)))
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(history.imgUri))
        }
        val path: String =
            MediaStore.Images.Media.insertImage(contentResolver, bitmap, null, null)
        return Uri.parse(path)
    }

    private fun showResult() {
        val formattedScore = NumberFormat.getPercentInstance().format(history.score).trim()
        val dateFormat = SimpleDateFormat("EEE, d MMM, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(history.dateTime)
        val uri = Uri.parse(history.imgUri)


        val result = "${history.label} $formattedScore"
        binding.resultImage.setImageURI(uri)
        binding.resultText.text = result
        binding.percetage.progress = formattedScore.dropLast(1).toInt()
        binding.tvTimeStamp.text = formattedDate
    }

    private fun saveToHistory() {
        // Todo: check if it saved and make a ui change
        val savedImgUri = storeImage(history.imgUri)
        if (savedImgUri == null) {
            // Todo: Error Message
            return
        }
        history.imgUri = savedImgUri.toString()
        viewModel.addHistory(history)
    }

    private fun storeImage(uri: String): Uri? {
        val dateFormat = SimpleDateFormat("EEE_d_MMM_yyyy_hh_mm_ss", Locale.getDefault())
        val formattedDate = dateFormat.format(history.dateTime)
        val name = "$formattedDate.jpg"
        val pictureFile = File(this.filesDir, name)
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            decodeBitmap(ImageDecoder.createSource(contentResolver, Uri.parse(uri)))
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(uri))
        }
        return try {
            val fos = FileOutputStream(pictureFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.close()
            pictureFile.toUri()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        const val EXTRA_HISTORY = "history"
    }

}