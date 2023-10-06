package com.example.domain.model

data class House(
    val house: String,
    val house_id: String
) {
    override fun toString(): String {
        return house
    }
}