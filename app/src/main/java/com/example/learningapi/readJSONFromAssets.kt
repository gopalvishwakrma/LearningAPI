package com.example.learningapi

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

fun readJSONFromAssets(context: Context, path: String): String {
    val identifier = "[ReadJSON]"
    try {
        val file = context.assets.open(path)
        Log.i(
            identifier,
            "Success: Found File: $file."
        )
        val bufferedReader = BufferedReader(InputStreamReader(file))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }
        val jsonString = stringBuilder.toString()
        Log.i(
            identifier,
            "Success: JSON as String: $jsonString."
        )
        return jsonString
    } catch (e: Exception) {
        Log.e(
            identifier,
            "Failure: Error reading JSON: $e."
        )
        e.printStackTrace()
        return ""
    }
}

