package com.example.phonebook.views.fragments

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.phonebook.R
import com.example.phonebook.databinding.FragmentAddContactBinding
import com.example.phonebook.models.BitmapCropper
import com.example.phonebook.models.Contact
import com.example.phonebook.modelviews.ContactViewModel

// Bitmap that stores a selected cropped image
private var croppedImg: Bitmap? = null

class AddContact : Fragment() {
    private var imageUri = ""
    private var dataSubmitted = false
    private lateinit var binding: FragmentAddContactBinding
    private val phoneLength = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)

        // Gets ContactViewModel instance
        val contactViewModel: ContactViewModel by navGraphViewModels(R.id.nav_host_fragment)

        // Renders user image everytime the fragment gets rendered
        croppedImg?.let {
            binding.addImageIv.setImageBitmap(it)
        }

        // Establishes a contract for getting an image from storage
        val getImageContract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                getCroppedBitmap(it)
            }
        }

        // binding listeners
        binding.addImageIv.setOnClickListener { getImageContract.launch("image/*") }
        binding.addBtn.setOnClickListener{ validateInput(contactViewModel) }
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        if (!dataSubmitted)
            saveState()
    }

    override fun onResume() {
        super.onResume()
        recoverState()
        try {
            if (imageUri.isNotEmpty()) {
                getCroppedBitmap(Uri.parse(imageUri))
                croppedImg.let {
                    binding.addImageIv.setImageBitmap(it)
                }
            }
        } catch (e: SecurityException) {
            imageUri = ""
        }
    }

    private fun getCroppedBitmap(uri: Uri) {
        croppedImg = BitmapCropper.createBitmap(context, uri)
        imageUri = uri.toString()
        binding.addImageIv.setImageBitmap(croppedImg)
    }

    private fun saveState() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        with (sharedPref?.edit()) {
            this?.putString("name", binding.addNameEt.text.toString())
            this?.putString("phone", binding.addPhoneEt.text.toString())
            this?.putString("email", binding.addEmailEt.text.toString())
            if (imageUri.isNotEmpty())
                this?.putString("image_uri", imageUri)
            this?.apply()
        }
    }

    private fun recoverState() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val name = sharedPref?.getString("name", null)
        val phone = sharedPref?.getString("phone", null)
        val email = sharedPref?.getString("email", null)
        val image = sharedPref?.getString("image_uri", null)
        if (!name.isNullOrEmpty())
            binding.addNameEt.setText(name)
        if (!phone.isNullOrEmpty())
            binding.addPhoneEt.setText(phone)
        if (!email.isNullOrEmpty())
            binding.addEmailEt.setText(email)
        if (!image.isNullOrEmpty() && imageUri.isEmpty())
            imageUri = image
    }

    private fun validateInput(contactViewModel: ContactViewModel) {
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

        if (binding.addPhoneEt.unMaskedText?.length != phoneLength) {
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
            // Removes temporal data from shared preferences
            val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()
            dataSubmitted = true
            croppedImg = null
            contactViewModel.addContact(Contact(
                binding.addNameEt.text.toString(),
                binding.addPhoneEt.text.toString(),
                binding.addEmailEt.text.toString(),
                imageUri
            ))
            // Returns back to contact list
            findNavController().navigate(R.id.action_addContact_to_contactCardList)
        }
    }
}