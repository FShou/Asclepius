package com.dicoding.asclepius.view.result

import android.graphics.Bitmap
import android.icu.text.NumberFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.asclepius.databinding.ActivityResultBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

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


        val label = intent.getStringExtra(EXTRA_LABEL)
        val score = NumberFormat.getPercentInstance().format(intent.getFloatExtra(EXTRA_SCORE,0f)).trim()


        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)


        val result = "$label $score"
        binding.resultImage.setImageURI(Uri.parse(intent.getStringExtra(EXTRA_IMG_URI)))
        binding.resultText.text =  result
        binding.percetage.progress = score.dropLast(1).toInt()
        binding.tvTimeStamp.text = formattedDate
    }

    fun storeImage(image: Bitmap): Uri? {
        var pictureFile: File = File(Environment.getExternalStorageDirectory().path + "/Folder")
        // Todo: Load Img
        val timeStamp = "a"
        val name = "$timeStamp.jpg"
        pictureFile = File(pictureFile.path + File.separator + name)

        return try {
            val fos = FileOutputStream(pictureFile)
            image.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.close()
            pictureFile.toUri()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        const val EXTRA_IMG_URI = "img-uri"
        const val EXTRA_LABEL = "label"
        const val EXTRA_SCORE = "score"
    }

}