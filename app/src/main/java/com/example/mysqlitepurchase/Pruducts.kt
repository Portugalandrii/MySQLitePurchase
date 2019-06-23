package com.example.mysqlitepurchase

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pruducts(
    var id: Int = 0,
    var name: String = "",
    var quant: Int = 0,
    var pr: Int = 0
): Parcelable  //это интерфейс для передачи объектов по сети по байтам и соберут в объект
               // чтобы передать объект из одного класса в другой иначе нам необходимо передавать объект
               // по полям