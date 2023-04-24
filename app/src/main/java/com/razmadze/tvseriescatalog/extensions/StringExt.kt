package com.razmadze.tvseriescatalog.extensions

import java.text.DecimalFormat

fun Double.voteAverageFormatter(): String {
    return DecimalFormat("0.0").format(this).toString()
}

fun Int.voteCountFormatter(): String {
    return if (this < 1000)
        this.toString()
    else
        "${DecimalFormat("0.0").format(this.toDouble() / 1000)}k"
}