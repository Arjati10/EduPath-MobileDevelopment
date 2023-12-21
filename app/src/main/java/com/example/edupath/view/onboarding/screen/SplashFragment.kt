package com.example.edupath.view.onboarding.screen

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.edupath.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler(Looper.getMainLooper()).postDelayed({

            if (onBoardingIsFinished()){
                findNavController().navigate(R.id.action_splashFragment_to_signInActivity)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
            }
        }, 1500)

        val view = inflater.inflate(R.layout.fragment_splash, container, false)

//        val animTop = AnimationUtils.loadAnimation(view.context, androidx.transition.R.anim.abc_slide_in_top)
//        val animBottom = AnimationUtils.loadAnimation(view.context, androidx.transition.R.anim.abc_slide_in_bottom)
//
//        animTop.duration = 2000
//        animBottom.duration = 2000

        val tvSplash = view.findViewById<TextView>(R.id.tv_title)
        val tvSub1 = view.findViewById<TextView>(R.id.tv_sub1)
        val tvSub2 = view.findViewById<TextView>(R.id.tv_sub2)
        val imgSplash = view.findViewById<ImageView>(R.id.iv_logo)
//
//        tvSplash.animation = animBottom
//        tvSub1.animation = animBottom
//        tvSub2.animation = animBottom
//        imgSplash.animation = animTop

        return view
    }

    private fun onBoardingIsFinished(): Boolean{
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("finished", false)
    }
}