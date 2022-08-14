package com.example.taehaed.Pojo.home
//Here We use Model Data Kotlin Cause The api data send data in var new and java not allow and easey with kind of thing
data class HomeRoot(
    val message: String,
    val new: Int,
    val progress: Int,
    val successful: Boolean

)