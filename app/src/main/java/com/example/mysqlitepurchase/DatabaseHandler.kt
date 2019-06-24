package com.example.mysqlitepurchase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID Integer PRIMARY KEY, $NAME TEXT, $QUANTITY Integer,$PRICE Integer)"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
    fun sumAll(): Int? {
        val db = this.readableDatabase
        var sum = db.rawQuery("select sum (quantity*price) from $TABLE_NAME", null)
        var a:Int? = 0
        if (sum != null) {
            if (sum.moveToFirst()) {
               a = sum.getInt(0)
            }
        }
        return a
    }

        fun addProducts(prod: Pruducts): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase  //Получили доступ к базе данных
        val values = ContentValues()    //Коллекция для хранения данных
        values.put(NAME, prod.name)//put - положить firstName(из EditText) в поле FIRST_NAME (в колекцию)
        values.put(QUANTITY, prod.quant)
        values.put(PRICE,prod.pr)
        //Вставляем в базу данных наши значения
        val _success = db.insert(TABLE_NAME, null, values) // данные записываем в базу данных
        db.close()
        // Log.v("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }
    fun delete(_id: Int?): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAllTasks(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1

    }

    //get all users
    fun getProducts(): List<Pruducts> {
        var allProducts: MutableList<Pruducts> = mutableListOf()
        val db = readableDatabase //создаем объект, позволяющий считывать данные с БД
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        //создаем cursor, куда передаем запрос
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    // cursor.getColumnIndex(ID)
                    var id: String = cursor.getString(cursor.getColumnIndex("id"))
                    var name = cursor.getString(cursor.getColumnIndex(NAME))
                    var quantity = cursor.getString(cursor.getColumnIndex(QUANTITY))
                    var price = cursor.getString(cursor.getColumnIndex(PRICE))
                    var product=Pruducts(id.toInt(), name, quantity.toInt(), price.toInt() )
                    allProducts.add(product)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allProducts
    }
    fun getElement(id: Int): Pruducts {
        val element = Pruducts()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        element.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        element.name = cursor.getString(cursor.getColumnIndex(NAME))
        element.pr = cursor.getColumnIndex(PRICE)
        element.quant = cursor.getColumnIndex(QUANTITY)
        cursor.close()
        return element
    }
    companion object {
        private val DB_NAME = "ListProducts"
        private val DB_VERSIOM = 1;
        private val TABLE_NAME = "Products"
        private val ID = "id"
        private val NAME = "Name"
        private val QUANTITY = "Quantity"
        private val PRICE = "Price"
    }
}