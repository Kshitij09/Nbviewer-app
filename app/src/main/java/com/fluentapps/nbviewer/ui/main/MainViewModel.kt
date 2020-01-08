 package com.fluentapps.nbviewer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream

 class MainViewModel : ViewModel() {
     private val _fileContent = MutableLiveData<String>("json here")
     val fileContent: LiveData<String>
         get() = _fileContent

     private val _filename = MutableLiveData<String>("file name")
     val filename: LiveData<String>
         get() = _filename

     private val _showLoadingText = MutableLiveData<Boolean>(false)
     val showLoadingText: LiveData<Boolean>
         get() = _showLoadingText

     fun updateFileContent(filename: String?, inputStream: InputStream?) {
         _filename.postValue(filename)
         _showLoadingText.postValue(true)
         GlobalScope.launch {
             val content = inputStream?.readBytes()?.toString(Charsets.UTF_8)
             _showLoadingText.postValue(false)
             _fileContent.postValue(content)
         }
     }
 }
