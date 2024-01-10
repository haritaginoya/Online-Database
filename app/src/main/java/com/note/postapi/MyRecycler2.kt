package com.note.postapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MyRecycler2(var context: Context, var arraylist: ArrayList<MyModel>) :
    RecyclerView.Adapter<MyRecycler2.MyClass>() {
    class MyClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var PRODUCT_IMAGE: ImageView
        lateinit var PRODUCT_NAME: TextView
        lateinit var PRODUCT_PRICE: TextView
        lateinit var PRODUCT_DES: TextView
        lateinit var click: RelativeLayout


        init {
            PRODUCT_IMAGE = itemView.findViewById(R.id.pr_image)
            PRODUCT_NAME = itemView.findViewById(R.id.pr_name)
            PRODUCT_PRICE = itemView.findViewById(R.id.pr_price)
            PRODUCT_DES = itemView.findViewById(R.id.pr_des)
            click = itemView.findViewById(R.id.click)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyClass {


        var vv = LayoutInflater.from(context).inflate(R.layout.view, parent, false)



        return MyClass(vv)
    }

    override fun getItemCount(): Int {

        return arraylist.size
    }

    override fun onBindViewHolder(holder: MyClass, position: Int) {

        Glide.with(context)
            .load("https://kotlinwork.000webhostapp.com/${arraylist[position].PRODUCT_IMAGE}")
            .diskCacheStrategy(
                DiskCacheStrategy.NONE
            ).skipMemoryCache(true)
            .into(holder.PRODUCT_IMAGE)

        holder.PRODUCT_NAME.setText(arraylist[position].PRODUCT_NAME)
        holder.PRODUCT_PRICE.setText(arraylist[position].PRODUCT_PRICE)
        holder.PRODUCT_DES.setText(arraylist[position].PRODUCT_DES)

        holder.click.setOnClickListener {

//            context.startActivity(
//                Intent(context, ViewSingleProduct::class.java)
//                    .putExtra("pr_name", arraylist[position].PRODUCT_NAME)
//                    .putExtra("pr_price", arraylist[position].PRODUCT_PRICE)
//                    .putExtra("pr_des", arraylist[position].PRODUCT_DES)
//                    .putExtra("pr_image",arraylist[position].PRODUCT_IMAGE).putExtra("discount",arraylist[position].DISCOUNT)
//            )


        }
    }

}
