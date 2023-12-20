package com.example.bangkit_capstone.home.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bangkit_capstone.databinding.FragmentAccountBinding
import com.example.bangkit_capstone.di.Injection
import com.example.bangkit_capstone.ui.ViewModelFactory
import com.example.bangkit_capstone.ui.auth.login.LoginActivity
import com.example.bangkit_capstone.ui.editProfile.EditProfileActivity

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: AccountViewModel

    private var userId : String = ""
    private var userName : String = ""
    private var userEmail : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val userProvider = Injection.loginProvider(requireActivity()).getToken().value?:""
        Injection.provideApplicationInfoMetadata(requireActivity())?.let { bundle ->
            bundle.getString("ENDPOINT_AUTH")?.let {
                viewModel = ViewModelProvider(
                    requireActivity(),
                    ViewModelFactory.getInstance(requireActivity(), it, userProvider)
                )[AccountViewModel::class.java]
            }
        }

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSession()
        setUp()
    }

    /*
    check if the user has logged in. if not move to the login activity page
   */
    private fun getSession() {
        viewModel.getUserSession().observe(viewLifecycleOwner) {
            if (it.accToken.isNullOrBlank()) {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            } else {
                userId = it.id.toString()
                viewModel.getUserById(it.id.toString())
            }
        }
    }

    private fun setUp(){
        // Check if binding is not null before accessing its properties
        viewModel.detailUserById.observe(viewLifecycleOwner) { data ->
            userEmail = data.email.toString()
            userName = data.username.toString()
            binding?.edUsername?.setText(data.username)
        }

        action()
    }

    private fun action() {
        binding.btnLogout.setOnClickListener { viewModel.setLogout() }
        binding.editProfile.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", userId)
            bundle.putString("username", userName)
            bundle.putString("email", userEmail)

            val intent = Intent(activity, EditProfileActivity::class.java)
            Log.d("bundele", bundle.toString())
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.detailUserById.removeObservers(viewLifecycleOwner)
    }
}