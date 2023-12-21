package com.example.bangkit_capstone.ui.editProfile

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.bangkit_capstone.R
import com.example.bangkit_capstone.databinding.ActivityEditProfileBinding
import com.example.bangkit_capstone.di.Injection
import com.example.bangkit_capstone.ui.ViewModelFactory
import com.example.bangkit_capstone.ui.auth.login.LoginActivity
import com.example.bangkit_capstone.ui.auth.login.LoginViewModel

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Bangkit_Capstone_OnMain)
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Edit Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.cornsilk)))

        initViewModel()
        setUpData()
    }

    private fun initViewModel() {
        val loginProviderToken = Injection.loginProvider(this).getToken().value ?: ""
        Injection.provideApplicationInfoMetadata(this)?.let { bundle ->
            bundle.getString("ENDPOINT_AUTH")?.let {
                viewModel = ViewModelProvider(
                    this,
                    ViewModelFactory.getInstance(this, it, loginProviderToken)
                )[EditProfileViewModel::class.java]
            }
        }
    }

    private fun setUpData () {

        val intent = intent
        val bundle = intent.extras
        val userId = bundle?.getString("id")
        val userName = bundle?.getString("username")
        val userEmail = bundle?.getString("email")

        binding.edUsername.setText(userName)
        binding.edEmail.setText(userEmail)

        binding.btnSaveEdit.setOnClickListener { editProfile(userId.toString()) }
        binding.btnDeteleAccount.setOnClickListener {
            deleteUser(userId.toString())
            AlertDialog.Builder(this@EditProfileActivity).apply {
                setTitle("Yakin menghapus akun?")
                setPositiveButton("Ya") {_, _ ->
                    viewModel.deleteUser(userId.toString())
                    viewModel.editMessage.observe(this@EditProfileActivity) {
                        message(it.toString())
                    }
                    viewModel.setLogout()
                    startActivity(
                        Intent(
                            this@EditProfileActivity, LoginActivity::class.java
                        )
                    )
                }
                create()
                show()
            }
        }

    }

    private fun editProfile(id : String) {
        val username = binding.edUsername.text.toString()
        val email = binding.edEmail.text.toString()
        val password = binding.edPasswor.text.toString()
        val confirmPassword = binding.edConfirmPassword.text.toString()

        if (password == confirmPassword) {
            viewModel.editProfile(id, username, email, password)
            viewModel.editMessage.observe(this) {
                message(it.toString())
                finish()
            }
        } else {
            message("password tidak sesuai")
        }
    }

    private fun deleteUser(userId : String){
        viewModel.deleteUser(userId)
        viewModel.deleteMessage.observe(this) {
            message(it.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun message(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}