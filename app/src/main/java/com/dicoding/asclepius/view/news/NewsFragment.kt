package com.dicoding.asclepius.view.news

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.FragmentHomeBinding
import com.dicoding.asclepius.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}