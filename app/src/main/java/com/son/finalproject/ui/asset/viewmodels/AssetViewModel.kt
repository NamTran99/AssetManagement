package com.son.finalproject.ui.asset.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.son.finalproject.R
import com.son.finalproject.base.BaseViewModel
import com.son.finalproject.data.Asset
import com.son.finalproject.data.Category
import com.son.finalproject.repository.ManageRepositoryImpl
import com.son.finalproject.utils.forceRefresh
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetViewModel @Inject constructor(
    application: Application,
    private val manageRepo: ManageRepositoryImpl
) : BaseViewModel(application) {


    val liveAsset = MutableLiveData(Asset())
    var selectedCategoryID = 0
    private var currentCategory: Category? = null
    private var symbolsCategory = ""

    init {
        getListCategory()
    }

    val listCategoryName = MutableLiveData(listOf<Category>())

    private fun getListCategory() = viewModelScope.launch {
        listCategoryName.value = manageRepo.getListCategoryName()
    }

    fun onSelectedCategorySpinner(position: Int) = viewModelScope.launch {
        listCategoryName.value?.let {
            setAssetCategoryID(position)
            currentCategory = it[position]
            selectedCategoryID = it[position].categoryID
            symbolsCategory = it[position].categoryName.take(2)
        }
    }

    private fun setAssetCategoryID(id: Int){
        liveAsset.value?.categoryID = id
        liveAsset.forceRefresh()
    }

    fun setItemStatus(position: Int) {
        liveAsset.value?.status = position
        liveAsset.forceRefresh()
    }

    fun setDateTime(dateTime: String){
        liveAsset.value?.installDate = dateTime
        liveAsset.forceRefresh()
    }

    fun onClickSaveButton() = viewModelScope.launch {
        var countItemWithCategory  =  ""
        val listAsset =
            manageRepo.getAllAssetCodeByCategoryID(selectedCategoryID)

        if(listAsset.isNotEmpty()){
            countItemWithCategory = (getIDBehindSymbol(listAsset[0])+1).toString().padStart(3, '0')
        }else{
            countItemWithCategory = DEFAULT_ASSET_CODE_ID
        }

        val futureAssetID = "$symbolsCategory$countItemWithCategory"

        Log.d(TAG, "onClickSaveButton: $countItemWithCategory")

        liveAsset.value?.apply {
            assetCode = futureAssetID
            manageRepo.insertSpecification(specification)
            manageRepo.insertAsset(this)
            showToast(R.string.create_asset_successfully)
            onBackStack()
        }
    }

    /*
    * giả sử assetcode: LA001 thì sẽ get ra 001
    * */
    private fun getIDBehindSymbol(value: String): Int {
        return value.drop(2).toInt()
    }

    companion object{
        const val DEFAULT_ASSET_CODE_ID = "001"
    }


}