package com.example.spendtrackernotes.ui

object Validator {
    fun validateInput(amount: Int, description: String) =
        !(amount <= 0 || description.isEmpty())
}