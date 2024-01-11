package com.note.postapi.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.note.postapi.R
import com.note.postapi.SplashScreen
import org.json.JSONObject

class SignIn : AppCompatActivity() {

    lateinit var gosignup: MaterialButton
    lateinit var goprofile: MaterialButton
    lateinit var usernamein: TextInputEditText
    lateinit var passwordin: TextInputEditText
    lateinit var progressbar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)

        gosignup = findViewById(R.id.gosignup)
        goprofile = findViewById(R.id.goprofile)
        usernamein = findViewById(R.id.usernamein)
        passwordin = findViewById(R.id.passwordin)
        progressbar = findViewById(R.id.progressbar)

        gosignup.setOnClickListener {

            startActivity(Intent(this@SignIn, SignUp::class.java))
            finish()

        }
        var ID = 0
        var MOBILE = ""
        var EMAIL = ""
        var NAME = ""
        var USERNAME = ""
        var PASSWORD = ""
        var IMAGEPATH = ""
        goprofile.setOnClickListener {
            progressbar.visibility = View.VISIBLE
            var que = Volley.newRequestQueue(this)
            var url = "https://kotlinwork.000webhostapp.com/login.php"

            var stringrequest: StringRequest =
                object : StringRequest(Request.Method.POST, url, { Response ->

                    Log.e("ressss", "onCreate: $Response")
                    var jsonobject = JSONObject(Response)

                    var respos = jsonobject.getJSONArray("response")

                    var data = respos.getJSONObject(0)

                    ID = data.getInt("ID")
                    MOBILE = data.getString("MOBILE")
                    EMAIL = data.getString("EMAIL")
                    NAME = data.getString("NAME")
                    USERNAME = data.getString("USERNAME")
                    PASSWORD = data.getString("PASSWORD")
                    IMAGEPATH = data.getString("IMAGEPATH")




                    SplashScreen.edit.putBoolean("user", true)
                    SplashScreen.edit.putString("username", USERNAME)
                    SplashScreen.edit.putInt("id", ID)
                    SplashScreen.edit.putString("imagepath", IMAGEPATH)
                    SplashScreen.edit.apply()


                    startActivity(Intent(this, HomePage::class.java))
                    finish()

                    Log.e("datamydata", "onCreate: ${MOBILE} $EMAIL $NAME $USERNAME $PASSWORD")
                }, {

                    Log.e("TAGfor", "onCreate: ${it.localizedMessage}")
                }) {
                    override fun getParams(): MutableMap<String, String>? {

                        var map = HashMap<String, String>()
                        map.put("username", usernamein.text.toString())
                        map.put("password", passwordin.text.toString())

                        return map
                    }
                }

            que.add(stringrequest)

        }
    }
}