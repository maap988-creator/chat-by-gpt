package com.countbygpt

data class Folder(

    val id: Int,
    var name: String,
    val counters: MutableList<Counter>

)