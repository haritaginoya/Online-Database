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

class AddProduct : AppCompatActivity() {

    lateinit var selct_photo: ImageView
    lateinit var product_name: TextInputEditText
    lateinit var product_price: TextInputEditText
    lateinit var product_des: TextInputEditText
    lateinit var dis: TextInputEditText
    lateinit var add: MaterialButton
    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        selct_photo = findViewById(R.id.selct_photo)
        progress = findViewById(R.id.progress)
        dis = findViewById(R.id.dis)

        selct_photo.setOnClickListener {

            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
        }

        product_name = findViewById(R.id.product_name)
        product_price = findViewById(R.id.product_price)
        product_des = findViewById(R.id.product_des)
        add = findViewById(R.id.add)

        add.setOnClickListener {
            if (product_price.text.toString().equals("") || dis.text.toString().equals("")) {
                if(product_price.text.toString().equals("")) {
                    product_price.setError("fill the blank")
                }
                else
                {
                    dis.setError("fill the blank")
                }
            } else {


                progress.visibility = View.VISIBLE
                var bitmap = (selct_photo.drawable as BitmapDrawable).bitmap
                var baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                var imageinbyte = baos.toByteArray()
                var imagedata = Base64.encodeToString(imageinbyte, 0)

                var id = SplashScreen.sp.getInt("id", 0)


                var que = Volley.newRequestQueue(this)
                var url = "https://kotlinwork.000webhostapp.com/addproduct.php"
                var stringRequest: StringRequest =
                    object : StringRequest(Request.Method.POST, url, Response.Listener { response ->


                        Log.e("checccc", "onCreate: ${response}")

                        startActivity(Intent(this, HomePage::class.java))
                        finish()


                    }, Response.ErrorListener {

                        Log.e("checkkkkkkkk", "onCreate: ${it.localizedMessage}")

                    }) {
                        override fun getParams(): MutableMap<String, String>? {

                            var mapp = HashMap<String, String>()

                            mapp.put("loginid", id.toString())
                            mapp.put("product_name", product_name.text.toString())
                            mapp.put("product_price", product_price.text.toString())
                            mapp.put("product_des", product_des.text.toString())
                            mapp.put("image", imagedata)
                            mapp.put("discount", dis.text.toString())


                            return mapp

                        }


                    }
                que.add(stringRequest)

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                selct_photo.setImageURI(resultUri)


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }


    }
}