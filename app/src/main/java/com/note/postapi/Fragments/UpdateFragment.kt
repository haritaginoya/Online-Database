package com.note.postapi.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.note.postapi.MyModel
import com.note.postapi.MyRecycler2
import com.note.postapi.R
import com.note.postapi.SplashScreen
import org.json.JSONObject


class UpdateFragment : Fragment() {

    lateinit var recycle: RecyclerView
    lateinit var progress : ProgressBar

    var arraylist = ArrayList<MyModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recycle = view.findViewById(R.id.up_recycle)
        progress = view.findViewById(R.id.up_progress)


        progress.visibility = View.VISIBLE
        var que = Volley.newRequestQueue(context)
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
                var adapter = context?.let { MyRecycler2(it, arraylist) }
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