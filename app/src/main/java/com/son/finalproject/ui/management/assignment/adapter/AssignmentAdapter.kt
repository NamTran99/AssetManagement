package com.son.finalproject.ui.management.assignment.adapter

import com.son.finalproject.R
import com.son.finalproject.base.BaseRecyclerViewAdapter
import com.son.finalproject.data.Assignment
import com.son.finalproject.databinding.ItemFieldAssignManagementBinding
import com.son.finalproject.ui.management.return_asset.adapter.ReturnAdapter

class AssignmentAdapter : BaseRecyclerViewAdapter<Assignment , ItemFieldAssignManagementBinding>(){
    override val layoutId = R.layout.item_field_assign_management


    // text: tên thuộc tính muốn xóa
    private var mOnClickRemove : ((text: Assignment) -> Unit)? = null

    fun setOnClickRemove(func: ((text: Assignment) ->Unit)?): AssignmentAdapter {
        mOnClickRemove = func
        return this
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemFieldAssignManagementBinding>,
        position: Int
    ) {
        val item = items[position]

        holder.binding.apply {
            assignment = item
            index = (position + 1).toString()

            state.text = when(item.status){
                Assignment.STATE_CANCELED -> "canceled"
                Assignment.STATE_COMPLETED -> "completed"
                Assignment.STATE_WAITING -> "waiting"
                else -> "declined"
            }

            btnRemove.setOnClickListener {
                mOnClickRemove?.invoke(item)
            }
        }
    }
}