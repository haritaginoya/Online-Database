package com.note.postapi.Activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.note.postapi.Fragments.AddProductFragment
import com.note.postapi.Fragments.ViewFragment
import com.note.postapi.R
import com.note.postapi.SplashScreen


class HomePage : AppCompatActivity() {

    lateinit var tool: Toolbar
    lateinit var drawer: DrawerLayout
    lateinit var navigation: NavigationView
    var username = ""
    var imagepath = ""
    lateinit var heading: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.note.postapi.R.layout.homepage)


        tool = findViewById(com.note.postapi.R.id.tool)
        drawer = findViewById(com.note.postapi.R.id.drawer)
        navigation = findViewById(com.note.postapi.R.id.navigation)

        heading = findViewById(com.note.postapi.R.id.heading)
        setSupportActionBar(tool)

        var actiontoggle = ActionBarDrawerToggle(
            this,
            drawer,
            tool,
            com.note.postapi.R.string.open,
            com.note.postapi.R.string.close
        )

        actiontoggle.syncState()

        var image: ImageView
        var profilename: TextView
        var addproduct: TextView
        var view = navigation.getHeaderView(0)

        supportFragmentManager.beginTransaction()
            .replace(com.note.postapi.R.id.framelayout, ViewFragment()).commit()
//        frag(com.note.postapi.R.id.framelayout)

        navigation.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                com.note.postapi.R.id.addproduct -> {
                    supportFragmentManager.beginTransaction()
                        .replace(com.note.postapi.R.id.framelayout, AddProductFragment()).commit()
                    heading.setText("ADD PRODUCT")
                    drawer.closeDrawers();
                }

                com.note.postapi.R.id.viewproduct -> {
                    supportFragmentManager.beginTransaction()
                        .replace(com.note.postapi.R.id.framelayout, ViewFragment()).commit()
                    heading.setText("VIEW PRODUCT")
                    drawer.closeDrawers();
                }
            }
            false
        }

        image = view.findViewById(R.id.profileimage)
        profilename = view.findViewById(R.id.profilename)
        addproduct = view.findViewById(R.id.addproduct)


        username = SplashScreen.sp.getString("username", "USER").toString()
        imagepath = SplashScreen.sp.getString("imagepath", "Image").toString()

        profilename.setText(username)

//        Log.e("=====check", "onCreate: ${SignIn.mydata.IMAGEPATH}")
        Glide.with(this).load("https://kotlinwork.000webhostapp.com/${imagepath}")
            .into(image);
    }

    override fun onBackPressed() {

        fragmentManager

        //if(supportFragmentManager.fragments.size > 0) {
        Log.d("=====", "frag:  ---> ${supportFragmentManager.fragments.size}")
        if (supportFragmentManager.fragments.lastOrNull() is AddProductFragment) {
            Log.d("=====", "frag:  ---> this is AddProductFragment")

            supportFragmentManager.beginTransaction()
                .replace(R.id.framelayout, ViewFragment()).commit()

        }else{
            super.onBackPressed()
        }
        //}
    }


}
