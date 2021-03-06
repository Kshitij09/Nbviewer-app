package com.fluentapps.nbviewer.ui.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.fluentapps.nbviewer.databinding.MainFragmentBinding
import com.fluentapps.nbviewer.utils.executeAfter
import com.fluentapps.nbviewer.utils.queryName
import java.io.FileNotFoundException

class MainFragment : Fragment() {
    private val TAG = MainFragment::class.java.simpleName
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
            btnPickFile.setOnClickListener { callFileIntent() }
            txtFileContent.movementMethod = ScrollingMovementMethod()
        }

        return binding.root
    }

    private fun callFileIntent() = runWithPermissions(Permission.READ_EXTERNAL_STORAGE) {
        // TODO: Filter for only .ipynb files
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        startActivityForResult(intent, FILE_PICK_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_PICK_REQUEST && resultCode == RESULT_OK) {
            data?.data?.let {
                val filename = activity?.contentResolver?.queryName(it)
                Log.d(TAG, "Got file $filename")
                try {
                    val inputStream = activity?.contentResolver?.openInputStream(it)
                    viewModel.updateFileContent(
                        filename,
                        inputStream
                    )
                } catch (e: FileNotFoundException) {
                    Log.e(TAG, e.message)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.executeAfter {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }
}
