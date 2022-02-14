package com.example.phonebook

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.example.phonebook.databinding.FragmentContactInfoBinding
import com.example.phonebook.models.BitmapCropper
import com.example.phonebook.models.Contact
import com.example.phonebook.viewmodels.ContactViewModel

class ContactInfo : Fragment() {
    private lateinit var binding: FragmentContactInfoBinding
    private val displayData = Contact("", "", "", "")
    private val contactViewModel: ContactViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        contactViewModel.contactModel.observe(viewLifecycleOwner, Observer {
            displayData.name = it.name
            displayData.email = it.email
            displayData.phone = it.phone
            if (it.image.isNotEmpty()) {
                // Setting image URI
                val cropImg = BitmapCropper.createBitmap(context, Uri.parse(it.image))
                binding.imageIv.setImageBitmap(cropImg)
            }
        })

        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,
            R.layout.fragment_contact_info, container, false)
        // Variable for displaying data binding
        binding.displayData = displayData

        binding.emailIv.setOnClickListener { _ ->
            startActivity(getEmailIntent())
        }
        binding.phoneIv.setOnClickListener { _ ->
            startActivity(getPhoneIntent())
        }
        binding.messageIv.setOnClickListener { _ ->
            startActivity(getSmsIntent())
        }

        return binding.root
    }

    // Creates an Intent for making a phone call
    private fun getPhoneIntent(): Intent = Intent(
        Intent.ACTION_DIAL, Uri.parse("tel:${displayData.phone}")
    )

    // Creates an Intent for sending an email
    private fun getEmailIntent(): Intent = Intent(
        Intent.ACTION_SENDTO, Uri.fromParts("mailto", displayData.email, null))

    // Creates an Intent for sending a text message
    private fun getSmsIntent(): Intent = Intent(
        Intent.ACTION_SENDTO, Uri.parse("smsto:${displayData.phone}")
    )
}