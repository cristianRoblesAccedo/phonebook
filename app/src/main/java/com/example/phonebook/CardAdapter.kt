package com.example.phonebook

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(val card: MutableList<Card>): RecyclerView.Adapter<CardAdapter.CardHolder>() {
    var onItemClick: ((Card) -> Unit)? = null

    inner class CardHolder(val view: View): RecyclerView.ViewHolder(view) {
        val nameTv: TextView = view.findViewById(R.id.name_tv)
        val emailTv: TextView = view.findViewById(R.id.email_tv)
        val phoneTv: TextView = view.findViewById(R.id.phone_tv)
        val imageIv: ImageView = view.findViewById(R.id.image_iv)

        fun render(item: Card) {
            view.setOnClickListener {
                onItemClick?.invoke(item)
            }
            nameTv.text = item.name
            emailTv.text = item.email
            phoneTv.text = item.phone
            imageIv.setImageURI(Uri.parse(item.image))
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