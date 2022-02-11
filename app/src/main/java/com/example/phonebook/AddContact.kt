package com.example.phonebook

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.google.android.material.textfield.TextInputLayout
import com.vicmikhailau.maskededittext.MaskedFormatter
import kotlin.math.abs

class AddContact : Fragment() {
    private lateinit var binding: FragmentAddContactBinding
    private var imageUri = ""
    private val phoneLenght = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)

        // Establishes a contract for getting an image from storage
        val getImageContract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val cropImg = BitmapCropper.createBitmap(context, it)
                imageUri = it.toString()
                binding.addImageIv.setImageBitmap(cropImg)
            }
        }

        // Recovers state
        savedInstanceState?.getString("name")?.let { binding.addNameEt.setText(it) }
        savedInstanceState?.getString("phone")?.let { binding.addPhoneEt.setText(it) }
        savedInstanceState?.getString("email")?.let { binding.addEmailEt.setText(it) }
        savedInstanceState?.getString("image_uri")?.let { imageUri = it }

        // binding listeners
        binding.addImageIv.setOnClickListener { getImageContract.launch("image/*") }
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
        var nameValid = false
        var emailValid = false
        var phoneValid = false

        if (!namePattern.matches(binding.addNameEt.text.toString())) {
            binding.addNameTil.error = "name must be between ${nameLength.first} and ${nameLength.second} characters long"
            binding.addNameTil.isErrorEnabled = true
        }
        else {
            nameValid = true
            binding.addNameTil.isErrorEnabled = false
        }

        if (!emailPattern.matches(binding.addEmailEt.text.toString())) {
            binding.addEmailTil.error = "Email is not valid"
            binding.addEmailTil.isErrorEnabled = true
        }
        else {
            emailValid = true
            binding.addEmailTil.isErrorEnabled = false
        }

        if (binding.addPhoneEt.unMaskedText?.length != phoneLenght) {
            binding.addPhoneTil.error = "Phone is not valid"
            binding.addPhoneTil.isErrorEnabled = true
        }
        else {
            phoneValid = true
            binding.addPhoneTil.isErrorEnabled = false
            var phone = binding.addPhoneEt.text.toString()
            if (phone[phone.length - 1] != ')') {
                phone += ")"
                binding.addPhoneEt.setText(phone)
            }
        }

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