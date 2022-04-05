package ca.tiffinsp.tiffinserviceapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ca.tiffinsp.tiffinserviceapplication.search.SearchActivity
import ca.tiffinsp.tiffinserviceapplication.tabs.home.HomeFragment
import ca.tiffinsp.tiffinserviceapplication.tabs.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.menu.findItem(R.id.placeholder).isEnabled = false
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    changeTab(HomeFragment.newInstance(), false)
                }
                R.id.action_setting -> {
                    changeTab(SettingsFragment.newInstance(), false)
                }
            }
            true
        }
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        bottomNavigationView.selectedItemId = R.id.action_home
    }


    private fun changeTab(fragment: Fragment, addToBackStack:Boolean){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        if(addToBackStack){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}