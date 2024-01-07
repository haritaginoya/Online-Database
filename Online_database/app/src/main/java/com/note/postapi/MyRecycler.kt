package com.note.postapi

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class MyRecycler(var context: Context, var arraylist: ArrayList<MyModel>) : RecyclerView.Adapter<MyRecycler.MyClass>() {
    class MyClass(itemView: View) : ViewHolder(itemView) {

        lateinit var PRODUCT_IMAGE : ImageView
        lateinit var PRODUCT_NAME : TextView
        lateinit var PRODUCT_PRICE : TextView
        lateinit var PRODUCT_DES : TextView
        lateinit var pr_finalprice : TextView
        lateinit var pr_discount : TextView


        init {
            PRODUCT_IMAGE = itemView.findViewById(R.id.pr_image)
            PRODUCT_NAME = itemView.findViewById(R.id.pr_name)
            PRODUCT_PRICE = itemView.findViewById(R.id.pr_price)
            PRODUCT_DES = itemView.findViewById(R.id.pr_des)
            pr_finalprice = itemView.findViewById(R.id.pr_finalprice)
            pr_discount = itemView.findViewById(R.id.pr_discount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyClass {

        var vv = LayoutInflater.from(context).inflate(R.layout.view,parent,false)



        return MyClass(vv)
    }

    override fun getItemCount(): Int {

        return arraylist.size
    }

    override fun onBindViewHolder(holder: MyClass, position: Int) {

        Glide.with(context).load("https://kotlinwork.000webhostapp.com/${arraylist[position].PRODUCT_IMAGE}").diskCacheStrategy(
            DiskCacheStrategy.NONE)
            .skipMemoryCache(true).into(holder.PRODUCT_IMAGE)

        holder.PRODUCT_NAME.setText(arraylist[position].PRODUCT_NAME)
        holder.PRODUCT_PRICE.setText(arraylist[position].PRODUCT_PRICE+" ₹")
        holder.PRODUCT_DES.setText(arraylist[position].PRODUCT_DES)

        if(!arraylist[position].DISCOUNT.equals("0")) {
            var per = (arraylist[position].PRODUCT_PRICE.toDouble() * arraylist[position].DISCOUNT.toDouble())/100
            var finalprice = arraylist[position].PRODUCT_PRICE.toDouble()-per
            holder.pr_finalprice.setText(finalprice.toString()+" ₹")
            holder.pr_discount.setText(arraylist[position].DISCOUNT + "%")
            holder.PRODUCT_PRICE.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        }
        else
        {
            holder.pr_finalprice.visibility = View.GONE
            holder.pr_discount.visibility = View.GONE
        }


    }

}
