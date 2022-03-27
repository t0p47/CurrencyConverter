package com.t0p47.currencyconverter.view.chart

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.t0p47.currencyconverter.R
import com.t0p47.currencyconverter.extension.FragmentInjectable

class ChartFragment : Fragment(), FragmentInjectable {

    companion object {
        fun newInstance() = ChartFragment()
    }

    private lateinit var viewModel: ChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chart_fragment, container, false)
    }

}
