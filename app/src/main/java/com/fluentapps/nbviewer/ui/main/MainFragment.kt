package com.fluentapps.nbviewer.ui.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.fluentapps.nbviewer.databinding.MainFragmentBinding
import com.fluentapps.nbviewer.utils.queryName

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private val FILE_PICK_REQUEST = 350

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding. inflate(
            inflater, container, false).apply {
            button.setOnClickListener { callFileIntent() }
        }

        return binding.root
    }

    private fun callFileIntent() = runWithPermissions(Permission.READ_EXTERNAL_STORAGE) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
//            putExtra(Intent.EXTRA_MIME_TYPES, "text/plain")
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        startActivityForResult(intent, FILE_PICK_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_PICK_REQUEST && resultCode == RESULT_OK) {
            data?.let { Log.d("MainFragment", "Got file ${activity?.contentResolver?.queryName(data.data!!)}") }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
    }

}
