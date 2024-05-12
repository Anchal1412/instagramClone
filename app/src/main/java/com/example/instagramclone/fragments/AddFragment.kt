package com.example.instagramclone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentAddBinding
import com.example.instagramclone.posts.PostActivity
import com.example.instagramclone.posts.ReelsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentAddBinding
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAddBinding.inflate(inflater, container, false)
        binding.post.setOnClickListener{
          activity?.startActivity(Intent(requireContext(),PostActivity::class.java))
        }
        binding.reel.setOnClickListener{
            activity?.startActivity(Intent(requireContext(),ReelsActivity::class.java))
        }
        return binding.root
    }

    companion object {

    }
}