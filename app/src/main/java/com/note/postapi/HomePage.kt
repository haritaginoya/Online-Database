package com.note.postapi

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.note.postapi.Fragments.UpdateFragment
import com.note.postapi.Fragments.ViewFragment
import com.note.postapi.Fragments.AddProductFragment as AddProductFragment1

class HomePage : AppCompatActivity() {

    lateinit var tool: Toolbar
    lateinit var drawer: DrawerLayout
    lateinit var navigation: NavigationView
    var username = ""
    var imagepath = ""
    lateinit var heading : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        tool = findViewById(R.id.tool)
        drawer = findViewById(R.id.drawer)
        navigation = findViewById(R.id.navigation)

        heading = findViewById(R.id.heading)
        setSupportActionBar(tool)

        var actiontoggle = ActionBarDrawerToggle(this, drawer, tool, R.string.open, R.string.close)

        actiontoggle.syncState()

        var image: ImageView
        var profilename: TextView
        var addproduct: TextView
        var view = navigation.getHeaderView(0)

        supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout, ViewFragment()).commit()





        navigation.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.addroduct -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, AddProductFragment1()).commit()
                    heading.setText("ADD PRODUCT")
                    drawer.closeDrawers();
                }
                R.id.viewproduct ->{

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, ViewFragment()).commit()
                    heading.setText("VIEW PRODUCT")
                    drawer.closeDrawers();

                }
                R.id.editproduct ->
                {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, UpdateFragment()).commit()
                    heading.setText("EDIT PRODUCT")

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

}