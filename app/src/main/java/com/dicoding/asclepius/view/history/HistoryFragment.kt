package com.dicoding.asclepius.view.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.databinding.FragmentHomeBinding

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}