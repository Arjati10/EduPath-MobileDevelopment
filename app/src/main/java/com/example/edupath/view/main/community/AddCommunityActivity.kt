package com.example.edupath.view.main.community

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.edupath.data.pref.Community
import com.example.edupath.databinding.ActivityAddCommunityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddCommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCommunityBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private var imageURI: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        binding.ivGroup.setOnClickListener {
            showImageAttachMenu()
        }

        binding.btnAddCommunity.setOnClickListener {
            createCommunity()
        }
    }

    private fun showImageAttachMenu() {
        val popupMenu = PopupMenu(this, binding.ivGroup)
        popupMenu.menu.add(Menu.NONE, 0, 0, "Camera")
        popupMenu.menu.add(Menu.NONE, 1, 1, "Gallery")
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item->
            val id = item.itemId
            if (id == 0){
                pickImageCamera()
            } else if (id == 1){
                pickImageGallery()
            }

            true
        }
    }

    private fun pickImageCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp_Title")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Description")

        imageURI = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
        cameraActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{ result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data

                binding.ivGroup.setImageURI(imageURI)
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{ result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data
                imageURI = data!!.data
                binding.ivGroup.setImageURI(imageURI)
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )

    private fun createCommunity() {
        val namaCommunity = binding.namaCommunityEditText.text.toString()
        val lokasi = binding.lokasiEditText.text.toString()
        val type = binding.typeEditText.text.toString()
        val detail = binding.detailEditText.text.toString()

        if (namaCommunity.isNotEmpty() && lokasi.isNotEmpty() && type.isNotEmpty() && detail.isNotEmpty()) {
            val communityId = database.getReference("communities").push().key

            val members = listOf(
                firebaseAuth.currentUser!!.uid,
                firebaseAuth.currentUser!!.displayName,
                firebaseAuth.currentUser!!.email
            ).mapNotNull { it }

            val community = Community(
                namaCommunity = namaCommunity,
                lokasi = lokasi,
                type = type,
                detail = detail,
                photoGroup = "",
                communityId = communityId!!,
                members = members
            )

            uploadImage(communityId, imageURI, community)
        } else {
            Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show()
        }
    }
    private fun uploadImage(communityId: String, imageUri: Uri?, community: Community) {
        if (imageUri != null) {
            val filePathAndName = "community_images/$communityId"
            val imageRef = storage.getReference(filePathAndName)

            imageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uploadedImageUrl ->
                        val updatedCommunity = community.copy(photoGroup = uploadedImageUrl.toString()) // Now available here
                        database.getReference("communities").child(communityId).setValue(updatedCommunity)
                        Toast.makeText(this, "Community berhasil dibuat", Toast.LENGTH_SHORT).show()
                        finish()
                    }?.addOnFailureListener { e ->
                        Toast.makeText(this, "Gagal upload gambar ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("AddCommunity", "Gagal upload gambar: ${e.message}")
                    Toast.makeText(this, "Gagal upload gambar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            database.getReference("communities").child(communityId).setValue(community)
            Toast.makeText(this, "Community berhasil dibuat", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}