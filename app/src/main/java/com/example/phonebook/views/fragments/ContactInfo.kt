package com.example.phonebook.views.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.phonebook.R
import com.example.phonebook.databinding.FragmentContactInfoBinding
import com.example.phonebook.models.BitmapCropper
import com.example.phonebook.viewmodels.ContactViewModel

class ContactInfo : Fragment() {
    private lateinit var binding: FragmentContactInfoBinding
    private val contactViewModel: ContactViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,
            R.layout.fragment_contact_info, container, false)
        binding.lifecycleOwner = this

        contactViewModel.contactLiveData.observe(viewLifecycleOwner, Observer {
            val image = contactViewModel.contactLiveData.value?.image
            if (!image.isNullOrEmpty()) {
                val imageBitmap = BitmapCropper.createBitmap(context, Uri.parse(image))
                binding.imageIv.setImageBitmap(imageBitmap)
            } else {
                binding.imageIv.setImageDrawable(requireContext().getDrawable(R.drawable.ic_baseline_image_not_supported_24))
            }
        })

        // Variable for displaying data binding
        binding.displayData = contactViewModel

        binding.emailIv.setOnClickListener {
            startActivity(getEmailIntent())
        }
        binding.phoneIv.setOnClickListener {
            startActivity(getPhoneIntent())
        }
        binding.messageIv.setOnClickListener {
            startActivity(getSmsIntent())
        }

        return binding.root
    }

    // Creates an Intent for making a phone call
    private fun getPhoneIntent(): Intent = Intent(
        Intent.ACTION_DIAL, Uri.parse("tel:${contactViewModel.contactLiveData.value?.phone}")
    )

    // Creates an Intent for sending an email
    private fun getEmailIntent(): Intent = Intent(
        Intent.ACTION_SENDTO, Uri.fromParts("mailto", contactViewModel.contactLiveData.value?.email, null))

    // Creates an Intent for sending a text message
    private fun getSmsIntent(): Intent = Intent(
        Intent.ACTION_SENDTO, Uri.parse("smsto:${contactViewModel.contactLiveData.value?.phone}")
    )
}