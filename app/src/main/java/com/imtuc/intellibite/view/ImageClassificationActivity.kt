package com.imtuc.intellibite.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.imtuc.intellibite.ml.Model
import com.imtuc.intellibite.model.FruitVegetables
import com.imtuc.intellibite.ui.theme.DarkGreen
import com.imtuc.intellibite.viewmodel.MainViewModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.Objects


@Composable
fun ImageClassificationActivity(mainViewModel: MainViewModel, lifecycleOwner: LifecycleOwner) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePredict = remember {
        mutableStateOf(false)
    }

    val fruitVegetablesName = remember {
        mutableStateOf("")
    }

    val fruitVegetablesServingsInGrams = remember {
        mutableStateOf(0)
    }

    val fruitVegetablesCalories = remember {
        mutableStateOf(0)
    }

    val fruitVegetablesTotalFat = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesSaturatedFat = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesTransFat = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesPolyunsaturatedFat = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesMonounsaturatedFat = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesCarbohydrate = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesProtein = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesFiber = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesSugar = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesCholesterol = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesSodium = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesVitaminD = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesCalcium = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesIron = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesPotassium = remember {
        mutableStateOf(0.0)
    }

    val fruitVegetablesCaffeine = remember {
        mutableStateOf(0.0)
    }

    val context = LocalContext.current

    val file = File(context.filesDir, "external_files")

    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    );

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it

            imageUri?.let { it ->
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)
                        .copy(Bitmap.Config.ARGB_8888, true)
                    imagePredict.value = true
                } else if (it.path!!.isNotEmpty()) {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap = ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
                    imagePredict.value = true
                }
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            launcher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    mainViewModel.fruitVegetablesError.observe(lifecycleOwner, Observer { response ->
        if (mainViewModel.fruitVegetablesError.value == "Success") {
            var fruitVegetable = mainViewModel.fruitVegetables.value
            fruitVegetablesName.value = fruitVegetable!!.name
            fruitVegetablesServingsInGrams.value = fruitVegetable.servings_in_grams
            fruitVegetablesCalories.value = fruitVegetable.calories
            fruitVegetablesTotalFat.value = fruitVegetable.total_fat
            fruitVegetablesSaturatedFat.value = fruitVegetable.saturated_fat
            fruitVegetablesTransFat.value = fruitVegetable.trans_fat
            fruitVegetablesPolyunsaturatedFat.value = fruitVegetable.polyunsaturated_fat
            fruitVegetablesMonounsaturatedFat.value = fruitVegetable.monounsaturated_fat
            fruitVegetablesCholesterol.value = fruitVegetable.cholesterol
            fruitVegetablesSodium.value = fruitVegetable.sodium
            fruitVegetablesCarbohydrate.value = fruitVegetable.carbohydrate
            fruitVegetablesVitaminD.value = fruitVegetable.vitamind
            fruitVegetablesCalcium.value = fruitVegetable.calcium
            fruitVegetablesIron.value = fruitVegetable.iron
            fruitVegetablesPotassium.value = fruitVegetable.potassium
            fruitVegetablesCaffeine.value = fruitVegetable.caffeine

            Log.e("Response", response.toString())
        } else if (mainViewModel.fruitVegetablesError.value != "reset" && mainViewModel.fruitVegetablesError.value != "") {
            Toast.makeText(context, mainViewModel.fruitVegetablesError.value, Toast.LENGTH_SHORT)
                .show()
        } else if (mainViewModel.fruitVegetablesError.value == "reset") {
            fruitVegetablesName.value = ""
            mainViewModel.clearFruitVegetables()
        }
    })

    LaunchedEffect(key1 = imagePredict.value) {
        if (imagePredict.value != false) {
            var result = classifyImage(bitmap!!, context)
            Log.e("Result", result)
            mainViewModel.resetFruitVegetables()
            mainViewModel.getFruitVegetables(result)
            imagePredict.value = false
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        launcher.launch("image/*")
                    } else {
                        // Request a permission
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                modifier = Modifier
                    .padding(8.dp, 0.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen)
            ) {
                Text(text = "Select Image", color = Color.White, fontWeight = FontWeight.Bold)
            }
            if (imageUri?.path?.isNotEmpty() == true) {
                Image(
                    modifier = Modifier
                        .padding(16.dp, 8.dp),
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null
                )
            }
            if (fruitVegetablesName.value != "") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        "${fruitVegetablesName.value}",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Servings : ${fruitVegetablesServingsInGrams.value}g",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Calories : 95",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Divider(
                        color = Color.Black,
                        thickness = 2.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        "Total Fat : ${fruitVegetablesTotalFat.value}g",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        fontSize = 18.sp
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp, 0.dp, 0.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            "Saturated Fat : ${fruitVegetablesSaturatedFat.value}g",
                        )
                        Text(
                            "Trans Fat : ${fruitVegetablesTransFat.value}g",
                        )
                        Text(
                            "Polyunsaturated Fat : ${fruitVegetablesPolyunsaturatedFat.value}g",
                        )
                        Text(
                            "Monounsaturated Fat : ${fruitVegetablesMonounsaturatedFat.value}g",
                        )
                    }
                    Text(
                        "Cholesterol : ${fruitVegetablesCholesterol.value}mg",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        fontSize = 18.sp
                    )
                    Text(
                        "Sodium : ${fruitVegetablesSodium.value}mg",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        fontSize = 18.sp
                    )
                    Text(
                        "Total Carbohydrates : ${fruitVegetablesCarbohydrate.value}g",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        fontSize = 18.sp
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp, 0.dp, 0.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            "Vitamin D : ${fruitVegetablesVitaminD.value}mcg",
                        )
                        Text(
                            "Calcium : ${fruitVegetablesCalcium.value}mg",
                        )
                        Text(
                            "Iron : ${fruitVegetablesIron.value}mg",
                        )
                        Text(
                            "Potassium : ${fruitVegetablesPotassium.value}mg",
                        )
                    }
                    Divider(
                        color = Color.Black,
                        thickness = 2.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        "Caffeine : ${fruitVegetablesCaffeine.value}mg",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

fun classifyImage(image: Bitmap, context: Context): String {
    try {
        val newImage = Bitmap.createScaledBitmap(image, 224, 224, false)
        val model: Model = Model.newInstance(context)

        // Creates inputs for reference.
        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * 224 * 224 * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(224 * 224)
        newImage.getPixels(intValues, 0, 224, 0, 0, 224, 224)
        var pixel = 0
        //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
        for (i in 0 until 224) {
            for (j in 0 until 224) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 1))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 1))
            }
        }
        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs: Model.Outputs = model.process(inputFeature0)
        val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
        val confidences = outputFeature0.floatArray
        Log.e("Confidences", confidences.joinToString())

        // find the index of the class with the biggest confidence.
        val classes = arrayOf(
            "Apple",
            "Banana",
            "Beetroot",
            "Bell Pepper",
            "Cabbage",
            "Capsicum",
            "Carrot",
            "Cauliflower",
            "Chilli Pepper",
            "Corn",
            "Cucumber",
            "Eggplant",
            "Garlic",
            "Ginger",
            "Grapes",
            "Jalepeno",
            "Kiwi",
            "Lemon",
            "Lettuce",
            "Mango",
            "Onion",
            "Orange",
            "Paprika",
            "Pear",
            "Peas",
            "Pineapple",
            "Pomegranate",
            "Potato",
            "Raddish",
            "Rice",
            "Salad",
            "Sandwich",
            "Soy Beans",
            "Spinach",
            "Sweetpotato",
            "Tomato",
            "Turnip",
            "Watermelon"
        )
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        // Releases model resources if no longer used.
        model.close()

        return classes[maxPos]
    } catch (e: IOException) {
        // TODO Handle the exception

        return ""
    }
}