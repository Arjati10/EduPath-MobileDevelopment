    package com.example.edupath.view.onboarding

    import android.os.Bundle
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.viewpager2.widget.ViewPager2
    import com.example.edupath.R
    import com.example.edupath.view.onboarding.screen.FirstScreen
    import com.example.edupath.view.onboarding.screen.SecondScreen
    import com.example.edupath.view.onboarding.screen.ThirdScreen
    import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator

    class OnBoardingFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_onboarding, container, false)

            val fragmentList = arrayListOf<Fragment>(
                FirstScreen(),
                SecondScreen(),
                ThirdScreen()
            )

            val adapter  = OnBoardingAdapter(
                fragmentList,
                requireActivity().supportFragmentManager,
                lifecycle
            )

            val viewPager = view.findViewById<ViewPager2>(R.id.onBoarding)

            viewPager.adapter = adapter
            val indicator = view.findViewById<SpringDotsIndicator>(R.id.dots_indicator)

            indicator.attachTo(viewPager)

            return view
        }
    }