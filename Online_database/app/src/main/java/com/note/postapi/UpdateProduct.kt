package com.note.postapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class UpdateProduct : AppCompatActivity() {

    lateinit var recycle: RecyclerView
    lateinit var progress : ProgressBar

    var arraylist = ArrayList<MyModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)


        recycle = findViewById(R.id.up_recycle)
        progress = findViewById(R.id.up_progress)


        progress.visibility = View.VISIBLE
        var que = Volley.newRequestQueue(this)
        var url = "https://kotlinwork.000webhostapp.com/selectproduct.php"
        var stringrequest: StringRequest =
            object : StringRequest(Request.Method.POST, url, { Response ->


                var array = JSONObject(Response)
                var productdaetail = array.getJSONArray("response")
                var USER_ID = ""
                var PRODUCT_IMAGE = ""
                var PRODUCT_NAME = ""
                var PRODUCT_PRICE = ""
                var PRODUCT_DES = ""
                var DISCOUNT = ""
                arraylist.clear()
                for (i in 0 until productdaetail.length()) {
                    var user = productdaetail.getJSONObject(i)
                    USER_ID = user.getString("USER_ID")
                    PRODUCT_IMAGE = user.getString("PRODUCT_IMAGE")
                    PRODUCT_NAME = user.getString("PRODUCT_NAME")
                    PRODUCT_PRICE = user.getString("PRODUCT_PRICE")
                    PRODUCT_DES = user.getString("PRODUCT_DES")
                    DISCOUNT = user.getString("DISCOUNT")
                    var mymodel =
                        MyModel(
                            USER_ID,
                            PRODUCT_IMAGE,
                            PRODUCT_NAME,
                            PRODUCT_PRICE,
                            PRODUCT_DES,
                            DISCOUNT
                        )
                    arraylist.add(mymodel)
                }

                progress.visibility = View.INVISIBLE
                var adapter = MyRecycler2(this, arraylist)
                recycle.adapter = adapter

                Log.e("responsecheck", "onCreate: $Response")


            }, {
                Log.e("forerrorcheck", "onCreate: ${it.localizedMessage}")
            }) {
                override fun getParams(): MutableMap<String, String>? {
                    var map = HashMap<String, String>()

                    map.put("userid", SplashScreen.sp.getInt("id", 0).toString())


                    return map
                }
            }

        que.add(stringrequest)


    }
}