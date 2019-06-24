package com.example.mysqlitepurchase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.add_layout.*

class AddActivity : AppCompatActivity() {
    var dbHandler: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_layout)
        dbHandler = DatabaseHandler(this)



        var product: Pruducts? = null
        //lateinit var product: Pruducts
        val bundle: Bundle? = intent.extras  //проверяем Bundle, который получили с MainActitity c помощью Parceble
        val id =  (bundle?.apply {                      // достаем наш bundle

//       var a = getInt("key")
//            println(a)

        var products:Pruducts? = getParcelable<Pruducts>("id")    // получаем наш объект с MainActivity

            product = Pruducts(products!!.id,products.name,products.quant,products.pr)

            edit_name.setText(products?.name)
            edit_quantity.setText(products?.quant.toString())
            edit_price.setText(products?.pr.toString())

        })
        if (product?.id==null){
            button_delete.isEnabled = false
            button_delete.isClickable = false
        }

        button_delete.setOnClickListener  {
            dbHandler?.delete(product!!.id)
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }

        button_save.setOnClickListener(View.OnClickListener {
            // checking input text should not be null
            if (validation()){
                val products: Pruducts = Pruducts()
                var success: Boolean = false
                products.name = edit_name.text.toString()
                products.quant = edit_quantity.text.toString().toInt()
                products.pr = edit_price.text.toString().toInt()

                success = dbHandler!!.addProducts(products)

                if (success){
                    val toast = Toast.makeText(this,"Saved Successfully", Toast.LENGTH_LONG).show()
                    println(dbHandler!!.getProducts())
                    val i = Intent(applicationContext, MainActivity::class.java)
                    startActivity(i)
                }
            }

        })



    }
    fun validation(): Boolean{
        var validate = false

        if (!edit_name.text.toString().equals("")){
            validate = true
        }else{
            validate = false
            val toast = Toast.makeText(this,"Fill all details", Toast.LENGTH_LONG).show()
        }

        return validate
    }


}
