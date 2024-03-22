package com.dicoding.asclepius.view.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.asclepius.databinding.FragmentNewsBinding
import com.dicoding.asclepius.util.ViewModelFactory

class NewsFragment : Fragment() {

   
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNews().observe(viewLifecycleOwner) {
            println(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}