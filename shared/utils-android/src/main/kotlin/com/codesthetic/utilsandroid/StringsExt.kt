package com.codesthetic.utilsandroid

/**
 * Created by razylvidal on July 09, 2024
 */

fun String.capitalizeFirstChar(): String {
    return split(" ").joinToString(" ") { str ->
        str.replaceFirstChar { it.uppercase() }
    }
}
