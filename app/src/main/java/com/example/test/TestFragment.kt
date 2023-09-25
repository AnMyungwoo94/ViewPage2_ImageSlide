package com.example.test

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.test.databinding.FragmentTestBinding

class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    //뷰페이지2 이미지 슬라이드 연결
    private var sliderHandler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTestBinding.inflate(inflater, container, false)

        //이미지 슬라이드
        val images = listOf(R.drawable.shop_img, R.drawable.shop_img2, R.drawable.shop_img3)
        val sliderViewPager = binding.sliderViewPager
        sliderViewPager.offscreenPageLimit = 1
        sliderViewPager.adapter = ImageSliderAdapter(requireContext(), images)

        sliderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (isAdded) {
                    setCurrentIndicator(position)
                }else{
                    Log.e("setCurrentIndicator", "setCurrentIndicator 오류")
                }
            }
        })
        setupIndicators(images.size)
        // 자동 슬라이드 시작
        autoSlideViewPager2()
        Log.d("ViewPager2", "Initial Page: ${sliderViewPager.currentItem}")



        return binding.root

    }
    private fun setupIndicators(count: Int) {
        val indicators = arrayOfNulls<ImageView>(count)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(16, 8, 16, 8)
        }

        for (i in 0 until count) {
            indicators[i] = ImageView(requireContext()).apply {
                setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_indicator_inactive))
                layoutParams = params
                binding.layoutIndicators.addView(this)
            }
        }
        setCurrentIndicator(0)
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = binding.layoutIndicators.childCount
        val context = requireContext() // 컨텍스트를 미리 가져옵니다.

        for (i in 0 until childCount) {
            val imageView = binding.layoutIndicators.getChildAt(i) as? ImageView // 안전한 캐스팅
            if (imageView != null) {
                if (i == position) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bg_indicator_active))
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bg_indicator_inactive))
                }
            }
        }
    }
    private fun autoSlideViewPager2() {
        val sliderViewPager = binding.sliderViewPager
        val totalItemCount = sliderViewPager.adapter?.itemCount ?: 0

        runnable = Runnable {
            val nextItem: Int = (sliderViewPager.currentItem + 1) % totalItemCount //순서대로 가게끔 하려고
            sliderViewPager.setCurrentItem(nextItem, true)
            sliderHandler.postDelayed(runnable, 3000L)
        }

        // 처음 시작할 때의 딜레이 설정.
        sliderHandler.postDelayed(runnable, 3000L)
    }
}