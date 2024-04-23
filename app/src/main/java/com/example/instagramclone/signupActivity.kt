package com.example.instagramclone

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramclone.Models.User
import com.example.instagramclone.Utils.USER_NODE
import com.example.instagramclone.Utils.USER_PROFILE_FOLDER
import com.example.instagramclone.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class signupActivity : AppCompatActivity() {
    val binding by lazy{
    ActivitySignupBinding.inflate(layoutInflater)
    }
   lateinit var user:User
   private val launcher= registerForActivityResult(ActivityResultContracts.GetContent()){
       uri->
       uri?.let{
            uploadImage(uri, USER_PROFILE_FOLDER){
                if(it==null){

                }else{
                    user.image=it
                    binding.profileImage.setImageURI(uri)
                }
            }
       }
   }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        val text="<font color=FF000000>Activity have an Account</font> <font color=#1E88E5>Login?</font>"
        binding.login.setText(Html.fromHtml(text))
        user=User()
       binding.button.setOnClickListener{
           if(binding.name.editText?.text.toString().equals("") or
               binding.email.editText?.text.toString().equals("") or
               binding.password.editText?.text.toString().equals("")){
               Toast.makeText(this@signupActivity, "Please fill the all Information", Toast.LENGTH_SHORT).show()
           }else{
               FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                   binding.email.editText?.text.toString(),
                   binding.password.editText?.text.toString()
               ).addOnCompleteListener {
                   result->
                   if(result.isSuccessful){
                       user.name=binding.name.editText?.text.toString()
                       user.password=binding.password.editText?.text.toString()
                       user.email=binding.email.editText?.text.toString()
                       Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener {
                         startActivity(Intent(this@signupActivity,HomeActivity::class.java))
                           finish()
                       }
                   }else{
                       Toast.makeText(this@signupActivity, result.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                   }
               }
           }
       }
        binding.addimage.setOnClickListener{
            launcher.launch("image/*")
        }
        binding.login.setOnClickListener{
            startActivity(Intent(this@signupActivity,LoginActivity::class.java))
            finish()
        }
    }
}