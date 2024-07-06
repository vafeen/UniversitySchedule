package ru.vafeen.universityschedule.utils

fun String.removeSubStrings(vararg substrings: String): String {
    var result = this
    substrings.forEach {
        result = result.replace(it, "")
    }
    return result
}
fun String.dataToJsonString(): String = substringAfter("Query.setResponse(").let {
    it.substring(0, it.lastIndex - 1).removeSubStrings("null,")
}