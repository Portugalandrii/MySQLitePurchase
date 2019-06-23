package com.example.mysqlitepurchase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_layout.*
import java.util.ArrayList

class ProductsAdapter(var productsList: List<Pruducts>, var context: Context) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
         return ProductsViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent,false))
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        var products: Pruducts = productsList.get(position) //достали объект из списка
        // передаем во ViewHolder элементы по полям
        holder.id.text = products.id.toString()
        holder.name.text = products.name
        holder.quantity.text = products.quant.toString()
        holder.price.text = products.pr.toString()
        holder.sum.text = (products.quant*products.pr).toString()
        holder.item.setOnClickListener {             //Вешаем на контейнер обработчик нажатия
            val i = Intent(context, AddActivity::class.java)
            i.putExtra("id",products)
            context.startActivity(i)
        }
    }


    class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView = view.findViewById(R.id.id)
        var name: TextView = view.findViewById(R.id.product)
        var quantity: TextView = view.findViewById(R.id.quantity)
        var price: TextView = view.findViewById(R.id.price)
        var sum: TextView = view.findViewById(R.id.sum)
        var item: LinearLayout = view.findViewById(R.id.item) //инициализируем элемент разметки (для обработки нажатия
        // элемента RecyclerView)
    }
}