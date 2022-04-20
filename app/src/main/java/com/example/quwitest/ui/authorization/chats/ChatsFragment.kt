package com.example.quwitest.ui.authorization.chats

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quwitest.R
import com.example.quwitest.databinding.FragmentChatsBinding
import com.example.quwitest.network.ApiService
import com.example.quwitest.ui.authorization.AuthorizationFragment


class ChatsFragment : Fragment() {

    private lateinit var binding: FragmentChatsBinding
    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences(
            "TOKEN",
            Context.MODE_PRIVATE
        )
    }
    private val apiService by lazy { ApiService.getInstance(sharedPreferences) }
    private val chatsAdapter = ChatsAdapter()

    private val viewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ChatsViewModel(
                    apiService
                ) as T
            }
        })[ChatsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chatsAdapter.adapter = chatsAdapter
        viewModel.getDialogs()
        viewModel.users.observe(viewLifecycleOwner) {
            chatsAdapter.submitList(it.channels.toMutableList())
        }
        binding.imgReturn.setOnClickListener {
            returnToAuthorizationFragment()
        }
    }

    private fun returnToAuthorizationFragment() {
        val fragment = AuthorizationFragment()
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack("")
            ?.commit()
    }

    companion object {
        fun newInstance(token: String): ChatsFragment {
            return ChatsFragment().apply {
                arguments = Bundle().apply {
                    putString("TOKEN", token)
                }
            }
        }
    }

}