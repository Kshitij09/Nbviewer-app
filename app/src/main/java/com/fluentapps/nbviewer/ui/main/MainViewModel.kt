 package com.fluentapps.nbviewer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

 class MainViewModel : ViewModel() {
     private val _fileText = MutableLiveData<String>("json here")
     val fileText: LiveData<String>
         get() = _fileText
 }
