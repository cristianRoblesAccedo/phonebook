package com.example.phonebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.phonebook.databinding.FragmentContactCardBinding

class ContactCard : Fragment() {
    private lateinit var card: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        card = Card()
        arguments?.let {
            val name = arguments?.getString("name")
            val email = arguments?.getString("email")
            val phone = arguments?.getString("phone")
            name?.let { card.name = it }
            email?.let { card.email = it }
            phone?.let { card.phone = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentContactCardBinding>(inflater,
            R.layout.fragment_contact_card,
            container,
            false)
        binding.card = card
        return binding.root
    }
}