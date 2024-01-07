package com.note.postapi

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class ViewSingleProduct : AppCompatActivity() {

    lateinit var up_image: ImageView
    lateinit var up_product_name: TextInputEditText
    lateinit var up_product_price: TextInputEditText
    lateinit var up_dis: TextInputEditText
    lateinit var up_product_des: TextInputEditText
    lateinit var up_add: MaterialButton
    lateinit var up_progressbar: ProgressBar
    val REQUEST_CODE = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_single_product)

        up_image = findViewById(R.id.up_image)
        up_product_name = findViewById(R.id.up_product_name)
        up_product_price = findViewById(R.id.up_product_price)
        up_dis = findViewById(R.id.up_dis)
        up_product_des = findViewById(R.id.up_product_des)
        up_add = findViewById(R.id.up_add)
        up_progressbar = findViewById(R.id.up_progressbar)


        var id = SplashScreen.sp.getInt("id", 0)


        var pr_name = intent.getStringExtra("pr_name")
        var pr_price = intent.getStringExtra("pr_price")
        var pr_des = intent.getStringExtra("pr_des")
        var pr_image = intent.getStringExtra("pr_image")
        var discount = intent.getStringExtra("discount")
        Glide.with(this).load("https://kotlinwork.000webhostapp.com/$pr_image").diskCacheStrategy(
            DiskCacheStrategy.NONE
        ).skipMemoryCache(true).into(up_image)

        up_product_name.setText(pr_name)
        up_product_price.setText(pr_price)
        up_dis.setText(discount)
        up_product_des.setText(pr_des)

        up_image.setOnClickListener {

            if (Build.VERSION.SDK_INT < 19) {
                var intent = Intent()
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(intent, "Choose Pictures"), REQUEST_CODE
                )
            } else { // For latest versions API LEVEL 19+
                var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_CODE);
            }
//            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
        }

        up_add.setOnClickListener {
            up_progressbar.visibility = View.VISIBLE
            val bitmap = (up_image.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageInByte = baos.toByteArray()

            var imagedata =  Base64.encodeToString(imageInByte,0)


            var que = Volley.newRequestQueue(this)
            var url = "https://kotlinwork.000webhostapp.com/edit_product.php"

            var stringrequest: StringRequest = object : StringRequest(Request.Method.POST, url, {


                Log.e("========", "onCreate: $it")

                up_progressbar.visibility = View.INVISIBLE
                startActivity(Intent(this@ViewSingleProduct,ViewProduct::class.java))

            }, {

                Log.e("====", "onCreate: ${it.localizedMessage}")

            }) {
                override fun getParams(): MutableMap<String, String>? {
                    var map = HashMap<String, String>()

                    map.put("loginid", id.toString())
                    map.put("product_name", up_product_name.text.toString())
                    map.put("product_price", up_product_price.text.toString())
                    map.put("product_des", up_product_des.text.toString())
                    map.put("discount", up_dis.text.toString())
                    Log.e("------", "getParams: $pr_image")
                    map.put("oldimage", pr_image.toString())
                    map.put("newimage", imagedata.toString())

                    return map
                }
            }
            que.add(stringrequest)
        }
    }

    var mArrayUri: ArrayList<Uri>? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {

            // if multiple images are selected
            if (data?.getClipData() != null) {
                var count = data.clipData?.itemCount






                for (i in 0..count!! - 1) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    var imageUri1: Uri = data.clipData?.getItemAt(0)!!.uri
                    mArrayUri!!.add(imageUri)
                    up_image.setImageURI(imageUri1)
                    //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
                }

            } else if (data?.getData() != null) {
                // if single image is selected

                var imageUri: Uri = data.data!!
                up_image.setImageURI(imageUri)
                //   iv_image.setImageURI(imageUri) Here you can assign the picked image uri to your imageview

            }
        }
    }


//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            val result = CropImage.getActivityResult(data)
//            if (resultCode == RESULT_OK) {
//                val resultUri = result.uri
//                up_image.setImageURI(resultUri)
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                val error = result.error
//            }
//        }
//
//
//    }
}