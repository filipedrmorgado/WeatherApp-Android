package com.filipemorgado.weatherapp_android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipemorgado.weatherapp_android.databinding.FragmentMainBinding
import com.filipemorgado.weatherapp_android.ui.adapters.MultipleDaysRecyclerView
import com.filipemorgado.weatherapp_android.ui.viewmodels.WeatherViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    private lateinit var multipleDaysRecyclerView: MultipleDaysRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        // Setting up the fragment
        setupUI()
        setupObservers()
        return binding.root
    }

    /**
     * Setup initial UI
     */
    private fun setupUI() {
        initializeRecyclerView()
    }

    /**
     * Initialize Recycler View
     */
    private fun initializeRecyclerView() {
        multipleDaysRecyclerView = MultipleDaysRecyclerView()
        val mLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.contentMainLayout.recyclerView.apply {
            layoutManager = mLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = multipleDaysRecyclerView
        }
    }

    /**
     * Observe changes in order to update the screen data
     */
    private fun setupObservers() {

    }

}