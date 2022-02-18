package com.example.phonebook.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.phonebook.R
import com.example.phonebook.databinding.FragmentSelectImageListBinding
import com.example.phonebook.viewmodels.RetrofitViewModel
import com.example.phonebook.views.adapters.SelectImageListAdapter

class SelectImageList : Fragment() {
    lateinit var binding: FragmentSelectImageListBinding
    private val retrofitViewModel: RetrofitViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = SelectImageListAdapter()
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_image_list, container, false)
        binding.lifecycleOwner = this

        binding.imageListRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.imageListRv.adapter = adapter

        retrofitViewModel.personListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })

        return binding.root
    }
}