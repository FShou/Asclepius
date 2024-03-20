package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.

        binding.resultImage.setImageURI(Uri.parse(intent.getStringExtra(EXTRA_IMG_URI)))
        binding.resultText.text = intent.getStringExtra(EXTRA_RESULT)
    }

    companion object {
        const val EXTRA_IMG_URI = "img-uri"
        const val EXTRA_RESULT = "result"
    }

}