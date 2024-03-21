package com.dicoding.asclepius.view.home

import android.content.Intent
import android.icu.text.NumberFormat
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.asclepius.databinding.FragmentHomeBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.view.ResultActivity
import org.tensorflow.lite.task.vision.classifier.Classifications

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

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
        viewModel.setCurrentContentUri(uri)
        binding.progressIndicator.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Make history Layout and Show here


        binding.galleryButton.setOnClickListener { startGallery() }

        viewModel.currentImgUri.observe(requireActivity()) { uri ->
            currentImageUri = uri
            binding.previewImageView.setImageURI(uri)
        }

        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let { imgUri ->
                analyzeImage(imgUri)
            } ?: showToast("No image is chosen")
        }

    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun analyzeImage(imgUri: Uri) {
        binding.progressIndicator.visibility = View.VISIBLE
        val imageClassifier = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(msg: String) {
                    showToast(msg)
                }

                override fun onResults(results: List<Classifications>?) {
                    results?.let { classificationsList ->
                        if (classificationsList.isNotEmpty() && classificationsList[0].categories.isNotEmpty()) {
                            val category = classificationsList[0].categories[0]
                            moveToResult(imgUri, category.label, category.score)
                        }
                    }
                }
            })
        imageClassifier.classifyStaticImage(imgUri)
        binding.progressIndicator.visibility = View.GONE
    }
    private fun moveToResult(imgUri: Uri, label: String, score: Float) {
        val intent = Intent( activity, ResultActivity::class.java)
        intent.putExtra(ResultActivity.EXTRA_IMG_URI, imgUri.toString())
        intent.putExtra(ResultActivity.EXTRA_LABEL, label)
        intent.putExtra(ResultActivity.EXTRA_SCORE, score)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText( requireActivity(), message, Toast.LENGTH_SHORT).show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}