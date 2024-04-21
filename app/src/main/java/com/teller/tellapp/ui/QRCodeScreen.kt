package com.teller.tellapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.teller.tellapp.Route
import java.nio.ByteBuffer

@Composable
fun QRCodeScanner(
    navController: NavController,
    onQrCodeSubmit: (String) -> Unit,
    onCancel: () -> Unit
) {
    // State to hold the scanned QR code
    var code by remember {
        mutableStateOf("")
    }

    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    var scannedData by remember { mutableStateOf("") }


    // State to track whether the QR code is scanned
    var qrCodeScanned by remember {
        mutableStateOf(false)
    }

    // Accessing context and lifecycle owner from Compose
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Camera permission state
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher for requesting camera permission
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCamPermission = granted
    }

    // Request camera permission on first launch
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    // Column to hold camera preview and scanned QR code
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Check if camera permission is granted
        if (hasCamPermission) {
            // Display camera preview
            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(
                                previewView.width,
                                previewView.height
                            )
                        )
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    // Set up QR code analyzer
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer { result ->
                            code = result
                            qrCodeScanned =
                                true // Set qrCodeScanned to true when a QR code is scanned
                        }
                    )
                    // Bind camera lifecycle
                    try {
                        ProcessCameraProvider.getInstance(context).get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                },
                modifier = Modifier.weight(1f)
            )

            // Display scanned QR code
            Text(
                text = code,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )

            // Inside the QRCodeScanner composable
// Trigger navigation to EditScannedDataScreen when a QR code is scanned
//            if (qrCodeScanned) {
//                LaunchedEffect(key1 = qrCodeScanned) {
//                    // Navigate to the EditScannedDataScreen and pass scanned data directly
//                    navController.navigate(
//                        Route.EditScannedDataScreen().name
//                            .replace("{qrCode}", code)
//                    )
//                }
//            }



//            LaunchedEffect(key1 = qrCodeScanned) {
//                if (qrCodeScanned) {
//                    // Verify if LaunchedEffect is triggered
//                    Log.d("Navigation", "LaunchedEffect triggered")
//
//                    // Navigate to the editing screen with the scanned data
//                    val scannedData = code // Assuming 'code' holds the scanned data
//                    navController.navigate(
//                        Route.EditScannedDataScreen().name
//                            .replace("{qrCode}", code)
//                    )
//                }
//            }


            // Inside the QRCodeScanner composable

//             Display submit and cancel buttons if a QR code is scanned
            if (qrCodeScanned) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            try {

                                navController.navigate(
                                    Route.EditScannedDataScreen().name
                                        .replace("{qrCode}", code)
                                )

                                // Pass the scanned data to the EditScannedDataScreen
//                                navController.navigate(
//                                    Route.EditScannedDataScreen().name +
//                                            "$code"
//                                )


                            } catch (e: Exception) {
                                // Log or handle the exception
                                e.printStackTrace()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = buttonColors
                    ) {
                        Text("Submit")
                    }
                    Button(
                        onClick = { onCancel() },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = buttonColors
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

class QrCodeAnalyzer(
    private val onQrCodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
    )

    override fun analyze(image: ImageProxy) {
        if (image.format in supportedImageFormats) {
            val bytes = image.planes.first().buffer.toByteArray()
            val source = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
            )
            val binaryBmp = BinaryBitmap(HybridBinarizer(source))
            try {
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                            DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                                BarcodeFormat.QR_CODE
                            )
                        )
                    )
                }.decode(binaryBmp)
                onQrCodeScanned(result.text)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                image.close()
            }
        }
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        return ByteArray(remaining()).also {
            get(it)
        }
    }

}

