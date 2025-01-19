package com.example.dictionaryapp.ui.viewmodel

sealed class DictionaryResponse<out T> {
    data class Success<out T>(val data: T) : DictionaryResponse<T>()
    data class Error(val errMsg: String) : DictionaryResponse<Nothing>()
    object Loading: DictionaryResponse<Nothing>()
}
