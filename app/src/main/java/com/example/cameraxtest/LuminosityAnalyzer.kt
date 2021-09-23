package com.example.cameraxtest

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.nio.ByteBuffer
import java.util.concurrent.Executors


class LuminosityAnalyzer(
    val onLabeledSuccessful : (labels : List<ImageLabel>) -> Unit) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {
        classification(image)

    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun classification(imageProxy : ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {

            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val localModel = LocalModel.Builder()
                .setAssetFilePath("model.tflite")
                .build()


            val options = CustomImageLabelerOptions.Builder(localModel)
                .setConfidenceThreshold(0.7f)
                .build()
            val labeler = ImageLabeling.getClient(options)

            labeler.process(image)
                .addOnSuccessListener { labels ->
                    fetchData(labels)
                }
                .addOnFailureListener {

                }
        }
    }

    private fun fetchData(labels: List<ImageLabel>) {
            onLabeledSuccessful(labels)
    }
}