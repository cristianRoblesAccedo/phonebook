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
        displayData.image = args.image
        println("datos: $displayData")
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_info_display, container, false)
        binding.displayData = displayData

        binding.emailDisplayTv.setOnClickListener { _ ->
            startActivity(getEmailIntent())
        }
        binding.phoneDisplayTv.setOnClickListener { _ ->
            startActivity(getPhoneIntent())
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
}