 package com.fluentapps.nbviewer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

 class MainViewModel : ViewModel() {
     private val _fileContent = MutableLiveData<String>("json here")
     val fileContent: LiveData<String>
         get() = _fileContent

     private val _filename = MutableLiveData<String>("file name")
     val filename: LiveData<String>
         get() = _filename

     fun updateFileContent(filename: String?, content: String?) {
         _filename.postValue(filename)
         _fileContent.postValue(content)
     }
 }
