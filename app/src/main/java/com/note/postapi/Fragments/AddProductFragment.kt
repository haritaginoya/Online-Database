package com.note.postapi.Fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.note.postapi.Activity.HomePage
import com.note.postapi.R
import com.note.postapi.SplashScreen
import java.io.ByteArrayOutputStream


class AddProductFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    lateinit var selct_photo: ImageView
    lateinit var product_name: TextInputEditText
    lateinit var product_price: TextInputEditText
    lateinit var product_des: TextInputEditText
    lateinit var dis: TextInputEditText
    lateinit var add: MaterialButton
    lateinit var progress: ProgressBar
    var idd=0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selct_photo = view.findViewById(R.id.selct_photo)
        progress = view.findViewById(R.id.progress)
        dis = view.findViewById(R.id.dis)
        idd = SplashScreen.sp.getInt("id", 0)

        var value = arguments?.getBoolean("value")
        selct_photo.setOnClickListener {

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



        product_name = view.findViewById(R.id.product_name)
        product_price = view.findViewById(R.id.product_price)
        product_des = view.findViewById(R.id.product_des)
        add = view.findViewById(R.id.add)

        if (value != null) {
            editproduct()
        } else {
            add.setOnClickListener {
                if (product_price.text.toString().equals("") || dis.text.toString().equals("")) {
                    if (product_price.text.toString().equals("")) {
                        product_price.setError("fill the blank")
                    } else {
                        dis.setError("fill the blank")
                    }
                } else {


                    progress.visibility = View.VISIBLE
                    var bitmap = (selct_photo.drawable as BitmapDrawable).bitmap
                    var baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    var imageinbyte = baos.toByteArray()
                    var imagedata = Base64.encodeToString(imageinbyte, 0)



                    var que = Volley.newRequestQueue(context)
                    var url = "https://kotlinwork.000webhostapp.com/addproduct.php"
                    var stringRequest: StringRequest =
                        object :
                            StringRequest(Request.Method.POST, url, Response.Listener { response ->

                                Log.e("checccc", "onCreate: ${response}")
                                startActivity(Intent(context, HomePage::class.java))

                            }, Response.ErrorListener {

                                Log.e("checkkkkkkkk", "onCreate: ${it.localizedMessage}")

                            }) {
                            override fun getParams(): MutableMap<String, String>? {

                                var mapp = HashMap<String, String>()

                                mapp.put("loginid", idd.toString())
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

    }

    var mArrayUri: ArrayList<Uri>? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {

            Log.d("=====", "onActivityResult: ")

            // if multiple images are selected
            if (data?.getClipData() != null) {

                Log.d("=====", "onActivityResult:  --->   1")
                var count = data.clipData?.itemCount

                for (i in 0..count!! - 1) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                    var imageUri1: Uri = data.clipData?.getItemAt(0)!!.uri
                    mArrayUri!!.add(imageUri)
                    selct_photo.setImageURI(imageUri1)
                    //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
                }

            } else if (data?.getData() != null) {
                // if single image is selected

                Log.d("=====", "onActivityResult:  ---> ${data.data!!}")

                var imageUri: Uri = data.data!!
//                selct_photo.setImageURI(imageUri)

                Glide.with(this@AddProductFragment)
                    .load(data.data)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.appicon)
                    .into(selct_photo)
                //   iv_image.setImageURI(imageUri) Here you can assign the picked image uri to your imageview

            }
        }
    }

    val REQUEST_CODE = 200
    fun editproduct() {

        Log.d("=====", "editproduct: ")

        var pr_name = arguments?.getString("pr_name")
        var pr_price = arguments?.getString("pr_price")
        var pr_des = arguments?.getString("pr_des")
        var pr_image = arguments?.getString("pr_image")
        var discount = arguments?.getString("discount")

        Glide.with(this)
            .load("https://kotlinwork.000webhostapp.com/$pr_image")
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .error(R.drawable.app)
            .into(selct_photo)

        product_name.setText(pr_name)
        product_price.setText(pr_price)
        dis.setText(discount)
        product_des.setText(pr_des)


        add.setOnClickListener {
            progress.visibility = View.VISIBLE
            val bitmap = (selct_photo.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageInByte = baos.toByteArray()

            var imagedata = Base64.encodeToString(imageInByte, 0)


            var que = Volley.newRequestQueue(context)
            var url = "https://kotlinwork.000webhostapp.com/edit_product.php"

            var stringrequest: StringRequest = object : StringRequest(Request.Method.POST, url, {


                Log.e("========", "onCreate: $it")

                progress.visibility = View.INVISIBLE

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.framelayout, ViewFragment()).commit()


            }, {

                Log.e("====", "onCreate: ${it.localizedMessage}")

            }) {
                override fun getParams(): MutableMap<String, String>? {
                    var map = HashMap<String, String>()

                    map.put("loginid", idd.toString())
                    map.put("product_name", product_name.text.toString())
                    map.put("product_price", product_price.text.toString())
                    map.put("product_des", product_des.text.toString())
                    map.put("discount", dis.text.toString())
                    Log.e("------", "getParams: $pr_image")
                    map.put("oldimage", pr_image.toString())
                    map.put("newimage", imagedata.toString())
                    Log.e(
                        "pppppppp",
                        "getParams: ${idd},${product_name.text.toString()},${product_name.text.toString()},${product_des.text.toString()},${dis.text.toString()}",
                    )
                    return map
                }
            }
            que.add(stringrequest)
        }
    }



}

