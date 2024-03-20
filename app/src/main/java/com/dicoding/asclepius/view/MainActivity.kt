package com.dicoding.asclepius.view

import android.content.Intent
import android.icu.text.NumberFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null


    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->

        binding.progressIndicator.visibility = View.VISIBLE
        if (uri == null) {
            binding.progressIndicator.visibility = View.GONE
            showToast("No Image is Selected")
            return@registerForActivityResult
        }
        currentImageUri = uri
        showImage()
        binding.progressIndicator.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let { imgUri ->
                analyzeImage(imgUri)
            } ?: showToast("No image is chosen")

        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }


    private fun showImage() {
        binding.previewImageView.setImageURI(currentImageUri)
    }

    private fun analyzeImage(imgUri: Uri) {
        binding.progressIndicator.visibility = View.VISIBLE
        val imageClassifier = ImageClassifierHelper(
            context = this@MainActivity,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(msg: String) {
                    showToast(msg)
                }

                override fun onResults(results: List<Classifications>?) {
                    results?.let { classificationsList ->
                        if (classificationsList.isNotEmpty() && classificationsList[0].categories.isNotEmpty()) {
                            val category = classificationsList[0].categories[0]
                            val result = "${category.label} " + NumberFormat.getPercentInstance()
                                .format(category.score).trim()
                            moveToResult(imgUri, result)
                        }
                    }
                }
            })
        imageClassifier.classifyStaticImage(imgUri)
        binding.progressIndicator.visibility = View.GONE
    }

    private fun moveToResult(imgUri: Uri, result: String) {
        val intent = Intent(this@MainActivity, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMG_URI, imgUri.toString())
        intent.putExtra(ResultActivity.EXTRA_RESULT, result)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

}