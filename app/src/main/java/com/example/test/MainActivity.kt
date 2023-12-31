package com.example.test

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            // 프래그먼트 트랜잭션 시작
            val transaction = supportFragmentManager.beginTransaction()

            // 프래그먼트 생성
            val fragment = TestFragment()

            // 프래그먼트 교체
            transaction.replace(R.id.fragmentContainer, fragment)

            // 트랜잭션 커밋
            transaction.commit()
        }
    }
}
