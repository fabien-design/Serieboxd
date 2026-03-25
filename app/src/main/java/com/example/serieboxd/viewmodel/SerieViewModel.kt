package com.example.serieboxd.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.serieboxd.data.entities.Serie
import com.example.serieboxd.repository.SerieRepository

class SerieViewModel (private val repository: SerieRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allItems: LiveData<List<Serie>> = repository.allSeries.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
//    fun insert(dbItem: MovieDb) = viewModelScope.launch {
//        withContext(Dispatchers.IO) {
//            repository.insetMovie(dbItem)
//        }
//    }
//
//    fun delete(ids: List<Int>) = viewModelScope.launch {
//        withContext(Dispatchers.IO) {
//            repository.delete(ids)
//        }
//    }
//
//    fun updateTitle(id:Int, title:String) = viewModelScope.launch {
//        withContext(Dispatchers.IO)
//        {
//            repository.updateTitle(id = id, title = title)
//        }
//    }
}