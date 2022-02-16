package com.example.phonebook.views.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phonebook.R
import com.example.phonebook.models.BitmapCropper
import com.example.phonebook.models.Contact

class ContactListAdapter(val context: Context): RecyclerView.Adapter<ContactListAdapter.CardHolder>() {
    var onItemClick: ((Contact, CardHolder) -> Unit)? = null
    var contact = mutableListOf<Contact>()

    inner class CardHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val nameTv: TextView = view.findViewById(R.id.name_tv)
        private val emailTv: TextView = view.findViewById(R.id.email_tv)
        private val phoneTv: TextView = view.findViewById(R.id.phone_tv)
        private val imageIv: ImageView = view.findViewById(R.id.image_iv)

        fun render(item: Contact) {
            view.setOnClickListener {
                onItemClick?.invoke(item, this)
            }
            nameTv.text = item.name
            emailTv.text = item.email
            phoneTv.text = item.phone
            if (item.image.isNotEmpty()) {
                val imageCrop = BitmapCropper.createBitmap(context, Uri.parse(item.image))
                imageIv.setImageBitmap(imageCrop)
            } else {
                imageIv.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_image_not_supported_24))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CardHolder(layoutInflater.inflate(R.layout.fragment_contact_list_item, parent, false))
    }

    // Renders the <position> item in the list
    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.render(contact[position])
    }

    // Return the size of the viewHolder
    override fun getItemCount(): Int = contact.size

    fun deleteItem(i: Int) {
        contact.removeAt(i)
        notifyDataSetChanged()
    }

    fun addItem(i: Int, element: Contact) {
        contact.add(i, element)
        notifyDataSetChanged()
    }

    fun getItem(i: Int) = contact[i]

    // Updates the contact list everytime ContactViewModel notifies a change
    fun updateList(newContacts: MutableList<Contact>) {
        contact = newContacts
        notifyDataSetChanged()
    }
}