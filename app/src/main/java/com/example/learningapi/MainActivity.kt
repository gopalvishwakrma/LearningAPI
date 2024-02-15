package com.example.learningapi

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.learningapi.ui.theme.LearningAPITheme
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningAPITheme {
                val jsonString = readJSONFromAssets(this, "sample.json")
                val data = Gson().fromJson(jsonString, UIContent::class.java)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(data.models) { model ->
                            ModelCard(model = model)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ModelCard(model: UIContent.Model) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            PicassoImage(
                url = model.image,
                contentDescription = "Images",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Name: ${model.name}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Age: ${model.age}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Profession: ${model.profession}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Description: ${model.description}", style = MaterialTheme.typography.titleSmall)
                Text(text = "Distance: ${model.distance}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun PicassoImage(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    errorDrawable: Painter = painterResource(id = R.drawable.baseline_error_24)
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(url) {
        try {
            val imageBitmap = withContext(Dispatchers.IO) {
                Picasso.get().load(url).get()
            }
            bitmap = imageBitmap
        } catch (e: Exception) {
            Log.e("PicassoImage", "Error loading image: $e")
        } finally {
            loading = false
        }
    }

    if (loading) {
        // Show a placeholder or loading indicator
        CircularProgressIndicator(modifier = Modifier.size(100.dp))
    } else {
        bitmap?.asImageBitmap()?.let { imageBitmap ->
            Image(
                bitmap = imageBitmap,
                contentDescription = contentDescription,
                modifier = modifier
            )
        } ?: Image(
            painter = errorDrawable,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}





