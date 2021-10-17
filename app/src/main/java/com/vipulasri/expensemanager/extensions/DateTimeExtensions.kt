package com.vipulasri.expensemanager.extensions

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Created by Vipul Asri on 17/10/21.
 */

fun Long.toDate(format: String = "dd MMM, yyyy"): String {

    val localDate = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val formatter = DateTimeFormatter.ofPattern(format)

    return localDate.format(formatter)
}