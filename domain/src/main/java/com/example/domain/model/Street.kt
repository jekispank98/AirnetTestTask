package com.example.domain.model

data class Street(
    val street: String,
    val street_id: String
) {
    override fun toString(): String {
        return street
    }
}