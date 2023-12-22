package com.example.edupath.view.tesminatbakat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestBuilder
import com.example.edupath.R
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import javax.security.auth.callback.Callback

class FormTesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_form_tes, container, false)

        val deskripsiEt = view.findViewById<TextInputEditText>(R.id.deskripsiEditText)
        val btnCari = view.findViewById<Button>(R.id.btn_cari)

        btnCari.setOnClickListener{
            if (deskripsiEt.text.toString() == "") {
                Toast.makeText(requireContext(), "Deskripsi tidak boleh kosong...", Toast.LENGTH_SHORT).show()
            } else {
                val deskripsi = deskripsiEt.text.toString()
                sendData(deskripsi)
            }
        }

        return view
    }

    private fun sendData(deskripsi: String) {
        val client = OkHttpClient()
        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, Gson().toJson(mapOf("text" to deskripsi)))
        val request = Request.Builder()
            .url("https://mlmodel-api-lgzqqjyyeq-et.a.run.app/predict")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback, okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                activity?.runOnUiThread{
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        val responseObject = Gson().fromJson(responseBody, JsonObject::class.java)
                        val dataObject = responseObject.getAsJsonObject("data")
                        val jurusan = dataObject.get("predicted_class").asString

                        val bundle = Bundle()
                        bundle.putString("predicted_class", jurusan)

                        findNavController().navigate(R.id.action_formTesFragment_to_resultFragment, bundle)
                    } else {
                        Toast.makeText(requireContext(), "error disini ${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

}

