package com.example.phonebook

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.phonebook.databinding.FragmentAddContactBinding

class AddContact : Fragment() {
    private lateinit var binding: FragmentAddContactBinding
    private var imageUri = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Sets a custom behaviour for the back button so that contacts in contactList are not duplicated
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(AddContactDirections.actionAddContactToContactCardList(null, null, null, null))
        }

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)

        // Establishes a contract for getting an image from storage
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it.toString()
                binding.addImageIv.setImageURI(it)
            }
        }

        // Recovers state
        savedInstanceState?.getString("name")?.let {
            println("Recovering data: $it")
            binding.addNameEt.setText(it)
        }
        savedInstanceState?.getString("phone")?.let { binding.addPhoneEt.setText(it) }
        savedInstanceState?.getString("email")?.let { binding.addEmailEt.setText(it) }
        savedInstanceState?.getString("image_uri")?.let { imageUri = it }

        // binding listeners
        binding.addImageIv.setOnClickListener { getContent.launch("image/*") }
        binding.addBtn.setOnClickListener{ validateInput(it) }
        return binding.root
    }

    // Saves state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("Saving state...")
        outState.putString("name", binding.addNameEt.text.toString())
        outState.putString("phone", binding.addPhoneEt.text.toString())
        outState.putString("email", binding.addEmailEt.text.toString())
        outState.putString("image_uri", imageUri)
    }

    fun validateInput(view: View) {
        val nameLength = Pair(3, 50)
        val namePattern = """.{3,50}""".toRegex()
        val emailPattern = """([\w\d_]+\.)?+[\w\d_]+@[\w\d_]+(\.[\w\d]+)+""".toRegex()
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
            // Returns back to contact list
            findNavController().navigate(AddContactDirections.actionAddContactToContactCardList(
                binding.addNameEt.text.toString(),
                binding.addPhoneEt.text.toString(),
                binding.addEmailEt.text.toString(),
                imageUri
            ))
        }
    }
}