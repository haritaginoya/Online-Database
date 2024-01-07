package com.note.postapi


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.ByteArrayOutputStream


class SignUp : AppCompatActivity() {

    lateinit var number: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var name: TextInputEditText
    lateinit var username: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var confermpass: TextInputEditText
    lateinit var readyforsignin: MaterialButton
    lateinit var gosignin: MaterialButton
    lateinit var profile: ImageView
    lateinit var progressbarrr: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)


        number = findViewById(R.id.number)
        email = findViewById(R.id.email)
        name = findViewById(R.id.name)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        confermpass = findViewById(R.id.confermpass)
        readyforsignin = findViewById(R.id.readyforsignin)
        gosignin = findViewById(R.id.gosignin)
        profile = findViewById(R.id.profile)
        progressbarrr = findViewById(R.id.progressbarrr)


        profile.setOnClickListener {


            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

        }

        gosignin.setOnClickListener {

            startActivity(Intent(this, SignIn::class.java))
            finish()

        }

        readyforsignin.setOnClickListener {
            progressbarrr.visibility = View.VISIBLE

            //     ImageView => Bitmap drawble => Bitmap = > Byte Array = String

            val bitmap = (profile.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageInByte = baos.toByteArray()

            var imagedata =  Base64.encodeToString(imageInByte,0)

            var number1 = number.text.toString()
            var email1 = email.text.toString()
            var name1 = name.text.toString()
            var username1 = username.text.toString()
            var password1 = password.text.toString()
            var confermpass1 = confermpass.text.toString()


            var que = Volley.newRequestQueue(this)

            var url = "https://kotlinwork.000webhostapp.com/register.php"

            var stringrequest: StringRequest =
                object : StringRequest(Request.Method.POST, url, Response.Listener { response ->


                    Log.e("showe", "onCreate: ${response}")
                }, Response.ErrorListener {

                    Log.e("showerror", "onCreate: ${it.localizedMessage}")
                }) {
                    override fun getParams(): MutableMap<String, String> {
                        var map = HashMap<String, String>()

                        map.put("mobile", number1)
                        map.put("email", email1)
                        map.put("name", name1)
                        map.put("username", username1)
                        map.put("password", password1)
                        map.put("imagedata", imagedata)

                        return map
                    }
                }

            que.add(stringrequest)
            startActivity(Intent(this,SignIn::class.java))
            finish()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                profile.setImageURI(resultUri)


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}