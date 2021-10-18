package com.vipulasri.expensemanager.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vipulasri.expensemanager.model.TransactionUiModel

/**
 * Created by Vipul Asri on 19/10/21.
 */

class Converters {

    @TypeConverter
    fun fromStringToTransactionUiModelList(value: String?): List<TransactionUiModel> {
        val listType = object :
            TypeToken<ArrayList<TransactionUiModel?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTransactionUiModelListToString(list: List<TransactionUiModel?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}