package com.example.phonebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.phonebook.databinding.FragmentAddContactBinding

class AddContact : Fragment() {
    private lateinit var binding: FragmentAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)

        binding.addBtn.setOnClickListener{ validateInput(it) }

        return binding.root
    }

    fun validateInput(view: View) {
        val nameLength = Pair(3, 50)
        val namePattern = """.{3,50}""".toRegex()
        val emailPattern = """([\w\d_]+\.)+@[\w\d_]+(\.[\w\d]+)+""".toRegex()
        val phonePattern = """(\(\+?\d{1,3}\)? ?)?((\(?\d{3} ?\)){2}\d{2} ?\d{2}|\(?\d{2}\)? ?\(?\d{4}\)? ?\(?\d{4}\)?)""".toRegex()
        var nameValid = false
        var emailValid = false
        var phoneValid = false

        if (!namePattern.matches(binding.addNameEt.text))
            binding.addNameEt.error = "name must be between ${nameLength.first} and ${nameLength.second} characters long"
        else
            nameValid = true

        if (!emailPattern.matches(binding.addEmailEt.text))
            binding.addEmailEt.error = "Email is not valid"
        else
            emailValid = true

        if (!phonePattern.matches(binding.addPhoneEt.text))
            binding.addPhoneEt.error = "Phone is not valid"
        else
            phoneValid = true

        // If all fields are correct
        if (nameValid && emailValid && phoneValid) {
            // TODO: Create a new fragment and add it to ContactList
        }
    }
}