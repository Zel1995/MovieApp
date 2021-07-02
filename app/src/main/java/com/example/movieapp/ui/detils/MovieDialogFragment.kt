package com.example.movieapp.ui.detils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.movieapp.R
import com.example.movieapp.databinding.DialogFragmentMovieBinding

class MovieDialogFragment : DialogFragment(R.layout.dialog_fragment_movie) {
    lateinit var viewBinding: DialogFragmentMovieBinding
    private var callback: ((msg: String) -> Unit)? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = DialogFragmentMovieBinding.bind(view)
        viewBinding.cancelBtn.setOnClickListener {
            dismiss()
        }
        viewBinding.addBtn.setOnClickListener {
            callback?.invoke(viewBinding.noteEdit.text.toString())
            dismiss()
        }
    }

    fun addCallback(callback: (msg: String) -> Unit) {
        this.callback = callback
    }


}