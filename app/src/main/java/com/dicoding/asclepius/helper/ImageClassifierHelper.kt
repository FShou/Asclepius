package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier


class ImageClassifierHelper(
    private val threshold: Float = 0.1f,
    private val maxResult: Int = 1,
    private val modelName: String = "cancer_classification.tflite",
    val context: Context,
    val classifierListener: ClassifierListener

) {

    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {

        // TODO: Use NPU & GPU
        val imgClassifierOptions = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResult)

        val baseOptions = BaseOptions.builder()
            .setNumThreads(4)
            .build()


        imgClassifierOptions.setBaseOptions(baseOptions)

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                imgClassifierOptions.build()
            )
        } catch (e: IllegalStateException) {
            classifierListener.onError("Error msg") // Todo: add error msg in string res
            Log.e(TAG, e.message.toString())
        }

    }

    fun classifyStaticImage(imageUri: Uri) {

        if (imageClassifier == null) setupImageClassifier()

        val bitmap = uriToBitmap(imageUri)

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.UINT8))
            .build()

        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(bitmap))

        val result = imageClassifier?.classify(tensorImage)


        classifierListener.onResults(result)

    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        return bitmap.copy(Bitmap.Config.ARGB_8888, true)
    }

    interface ClassifierListener {
        fun onError(msg: String)
        fun onResults(
            results: List<Classifications>?,
        )
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }

}