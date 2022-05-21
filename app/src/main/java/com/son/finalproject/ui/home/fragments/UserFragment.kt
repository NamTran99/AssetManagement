package com.son.finalproject.ui.home.fragments

import androidx.fragment.app.viewModels
import com.son.finalproject.R
import com.son.finalproject.base.BaseFragment
import com.son.finalproject.databinding.FragmentUserBinding
import com.son.finalproject.ui.home.viewmodels.UserViewModel

class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>() {
    override val viewModel: UserViewModel by viewModels()
    override val layoutId = R.layout.fragment_user
}