package com.example.phonebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(val card: MutableList<Card>): RecyclerView.Adapter<CardAdapter.CardHolder>() {

    inner class CardHolder(val view: View): RecyclerView.ViewHolder(view) {
        val nameTv: TextView = view.findViewById(R.id.name_tv)
        val emailTv: TextView = view.findViewById(R.id.email_tv)
        val phoneTv: TextView = view.findViewById(R.id.phone_tv)
        val imageIv: ImageView = view.findViewById(R.id.image_iv)

        fun render(item: Card) {
            nameTv.text = item.name
            emailTv.text = item.email
            phoneTv.text = item.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CardHolder(layoutInflater.inflate(R.layout.fragment_contact_card_item, parent, false))
    }

    // Renders the <position> item in the list
    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.render(card[position])
    }

    // Return the size of the viewHolder
    override fun getItemCount(): Int = card.size
}