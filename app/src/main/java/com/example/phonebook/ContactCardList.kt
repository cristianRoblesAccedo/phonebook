package com.example.phonebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.phonebook.databinding.FragmentContactCardListBinding

class ContactCardList : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentContactCardListBinding>(inflater,
            R.layout.fragment_contact_card_list,
            container,
            false)
        binding.addContactButton.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_contactCardList_to_addContact)
        }

        return binding.root
    }
}