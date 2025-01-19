package com.example.dictionaryapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.api.RetrofitInstance
import com.example.dictionaryapp.model.Dictionary
import kotlinx.coroutines.launch

class DictionaryViewModel : ViewModel() {

    private val dicApi = RetrofitInstance.api

    private val _dictionaryResult = MutableLiveData<DictionaryResponse<Dictionary>>()
    val dictionaryResult: LiveData<DictionaryResponse<Dictionary>> = _dictionaryResult

    fun getMeaningOfWord(word: String) {
        _dictionaryResult.value = DictionaryResponse.Loading

        try {
            viewModelScope.launch {
                val response = dicApi.getDictionary(word)
                if(response.isSuccessful) {
                    _dictionaryResult.value = DictionaryResponse.Success(response.body()!!)
                } else {
                    _dictionaryResult.value = DictionaryResponse.Error("Failed to Load meaning of word: $word")
                }
            }
        }catch (e: Exception) {
            _dictionaryResult.value = DictionaryResponse.Error("Failed to Load meaning of word: $word")
        }
    }
}