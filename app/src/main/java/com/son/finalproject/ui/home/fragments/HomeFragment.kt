package com.son.finalproject.ui.home.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.son.finalproject.R
import com.son.finalproject.base.BaseFragment
import com.son.finalproject.databinding.FragmentHomeBinding
import com.son.finalproject.ui.home.viewmodels.HomeViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    override val layoutId = R.layout.fragment_home

    private val emailUser by lazy { arguments?.getString(ARG_EMAIL_USER)?: DEFAULT_EMAIL_USER }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }



    companion object{
        const val ARG_EMAIL_USER = "userEmail"
        const val DEFAULT_EMAIL_USER = "trandinhnam1199@gmail.com"
    }
}