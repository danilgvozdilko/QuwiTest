package com.example.quwitest.ui.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quwitest.R
import com.example.quwitest.ui.authorization.chats.ChatsFragment
import com.example.quwitest.databinding.FragmentAuthorizationBinding
import com.example.quwitest.network.ApiService


class AuthorizationFragment : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding
    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences(
            "TOKEN",
            Context.MODE_PRIVATE
        )
    }
    private val apiService by lazy { ApiService.getInstance(sharedPreferences) }
    private val viewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AuthorizationViewModel(
                    apiService
                ) as T
            }
        })[AuthorizationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            editEMail.doOnTextChanged { text, start, before, count ->
                viewModel.setEmail(text.toString())
            }
            editPassword.doOnTextChanged { text, start, before, count ->
                viewModel.setPassword(text.toString())
            }
            buttonLogin.setOnClickListener {
                viewModel.authUser(
                    email = editEMail.text.toString(),
                    password = editPassword.text.toString()
                )
                viewModel.users.observe(viewLifecycleOwner) {
                    val prefToken =
                        requireContext().getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
                    val editor = prefToken.edit()
                    editor.putString("TOKEN", it.token)
                    editor.apply()
                    openChatsFragments(it.token)
                }

            }
        }
    }

    private fun openChatsFragments(token: String) {
        val fragment = ChatsFragment.newInstance(token)
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack("")
            ?.commit()
    }

}