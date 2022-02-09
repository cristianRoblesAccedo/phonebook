package com.example.phonebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phonebook.databinding.FragmentContactCardListBinding
import com.google.android.material.snackbar.Snackbar

val cardList = mutableListOf<Card>()

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

        adapter.onItemClick = { contact ->
            findNavController().navigate(ContactCardListDirections.actionContactCardListToInfoDisplay(
                contact.name, contact.phone, contact.email, contact.image
            ))
        }

        //
        val swipeGesture = object : SwipeToRemoveGesture() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                super.onSwiped(viewHolder, direction)
                val tmpContact = adapter.getItem(viewHolder.absoluteAdapterPosition)
                adapter.deleteItem(viewHolder.absoluteAdapterPosition)
                println("viewHolder position: ${viewHolder.absoluteAdapterPosition}")
                Snackbar.make(binding.contactCardList, "Contact deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { _ ->
                        adapter.addItem(viewHolder.absoluteAdapterPosition + 1, tmpContact)
                    }
                    .show()
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.contactCardList)

        // Layout manager and adapter are synced to RecyclerView
        binding.contactCardList.layoutManager = LinearLayoutManager(context)
        binding.contactCardList.adapter = adapter

        // Navigates to AddContact fragment when button is pressed
        binding.addContactButton.setOnClickListener{ view: View ->
            view.findNavController().navigate(ContactCardListDirections.actionContactCardListToAddContact())
        }
        // If a new user is created then we create a new element in cardList
        if (!args.name.isNullOrEmpty()) {
            val tmpCard = Card(args.name!!, args.phone!!, args.email!!, args.image!!)
            if (cardList.size == 0)
                cardList.add(tmpCard)
            else if (cardList[cardList.size - 1] != tmpCard)
                cardList.add(tmpCard)
        }
        // Displays msg on screen if cardList is empty
        binding.emptyListTv.visibility = if (cardList.size == 0) View.VISIBLE else View.GONE

        return binding.root
    }
}