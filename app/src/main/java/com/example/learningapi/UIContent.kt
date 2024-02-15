package com.example.learningapi

data class UIContent(
    val models: List<Model>
) {
    data class Model(
        val image: String,
        val name: String,
        val age: Int,
        val profession: String,
        val description: String,
        val distance: String
    )
}

