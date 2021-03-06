package com.son.finalproject.ui.management.user.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.son.finalproject.R
import com.son.finalproject.base.BaseFragment
import com.son.finalproject.databinding.FragmentCreateUserBinding
import com.son.finalproject.ui.management.user.viewmodels.CreateUserViewModel
import com.son.finalproject.utils.helper.showPopUp
import com.son.finalproject.utils.helper.showTimePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_user.*

@AndroidEntryPoint
class CreateUserFragment : BaseFragment<FragmentCreateUserBinding, CreateUserViewModel>() {
    override val viewModel: CreateUserViewModel by activityViewModels()
    override val layoutId = R.layout.fragment_create_user
    val staffCode: String by lazy { arguments?.getString(ARG_STAFF_CODE) ?: "" }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            action = viewModel
            lifecycleOwner = viewLifecycleOwner
            viewModel.getNextUserID()
            isEditType = !staffCode.isNullOrEmpty()
            groupGender.setOnCheckedChangeListener { _, viewId ->
                val radioButton: View = groupGender.findViewById(viewId)
                viewModel.onGenderChangeListener(groupGender.indexOfChild(radioButton))
            }

            edt_select_dob_user.setOnClickListener {
                showTimePickerDialog {
                    edt_select_dob_user.text = it
                    viewModel.setDobUser(it)
                }
            }
            edt_joined_date_user.setOnClickListener {
                showTimePickerDialog {
                    edt_joined_date_user.text = it
                    viewModel.setJoinDateTimeUser(it)
                }
            }

            viewModel.setUserCode(userCode = staffCode)
            edtTypeUser.showPopUp(R.menu.menu_user_type, viewModel::onClickSetUserType)
        }

        initObserve()
    }

    private fun initObserve() {
        viewModel.liveUser.observe(viewLifecycleOwner){
            when(it.gender){
                0 -> binding.groupGender.check(binding.radioMale.id)
                1 -> binding.groupGender.check(binding.radioFemale.id)
                else -> binding.groupGender.check(binding.radioMale.id)
            }
            binding.edtTypeUser.text = if(it.type == 0) "Admin" else "User"
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel
    }

    companion object {
        const val ARG_STAFF_CODE = "staff_code"
    }
}