package com.example.phonebook

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.phonebook.databinding.FragmentInfoDisplayBinding

class InfoDisplay : Fragment() {
    private lateinit var binding: FragmentInfoDisplayBinding
    private val displayData = Card("", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = InfoDisplayArgs.fromBundle(requireArguments())
        displayData.phone = args.phone
        displayData.email = args.email
        displayData.name = args.name

        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_info_display, container, false)
        // Variable for displaying data binding
        binding.displayData = displayData
        // Setting image URI
        if (args.image.isNotEmpty()) {
            println("uri: ${args.image}")
            binding.imageIv.setImageURI(Uri.parse(args.image))
        }

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