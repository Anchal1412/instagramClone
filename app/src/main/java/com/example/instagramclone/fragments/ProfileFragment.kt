package com.example.instagramclone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.example.instagramclone.Utils.USER_NODE
import com.example.instagramclone.adeptors.ViewPager
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.example.instagramclone.signupActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater, container, false)
        binding.editProfile.setOnClickListener{
            val intent=Intent(activity, signupActivity::class.java)
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()
        }
        viewPager=ViewPager(requireActivity().supportFragmentManager)
        viewPager.addFragments(MyPostFragment(), "My Post")
        viewPager.addFragments(MyReelsFragment(), "My Reel")
        binding.viewPager.adapter=viewPager
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            val user: User = it.toObject(User::class.java)!!
            binding.name.text=user.name
            binding.bio.text=user.email
            if(!user.image.isNullOrBlank()){
                Picasso.get().load(user.image).into(binding.profileImage)
            }

        }
    }
}