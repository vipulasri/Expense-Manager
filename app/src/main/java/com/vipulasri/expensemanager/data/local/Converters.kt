package com.vipulasri.expensemanager.data.local

import android.annotation.SuppressLint
import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vipulasri.expensemanager.data.local.entity.TransactionEntity

/**
 * Created by Vipul Asri on 19/10/21.
 */

class Converters {

    @SuppressLint("LongLogTag")
    @TypeConverter
    fun fromStringToTransactionUiModelList(value: String?): List<TransactionEntity> {
        val listType = object :
            TypeToken<ArrayList<TransactionEntity?>?>() {}.type
        val validJson = "[$value]"
        Log.d("Converters, string-to-TransactionEntity", "database value: $value")
        Log.d("Converters, string-to-TransactionEntity", "valid json: $validJson")
        return Gson().fromJson(validJson, listType)
    }

}