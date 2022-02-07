package com.example.phonebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phonebook.databinding.FragmentContactCardListBinding

val cardList = mutableListOf<Card>()
var initList = false

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
        val adapter = CardAdapter(cardList)
        val args = ContactCardListArgs.fromBundle(requireArguments())

        binding.contactCardList.layoutManager = LinearLayoutManager(context)
        binding.contactCardList.adapter = adapter
        binding.addContactButton.setOnClickListener{ view: View ->
            view.findNavController().navigate(ContactCardListDirections.actionContactCardListToAddContact())
        }
        args.name.let {
            if (!it.isNullOrEmpty() && initList)
                cardList.add(Card(it, args.phone!!, args.email!!, args.image!!))
            initList = true
        }

        return binding.root
    }
}