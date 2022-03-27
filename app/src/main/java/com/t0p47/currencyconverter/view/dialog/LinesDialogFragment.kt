package com.t0p47.currencyconverter.view.dialog

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.t0p47.currencyconverter.extension.FragmentInjectable
import com.t0p47.currencyconverter.extension.injectViewModel
import com.t0p47.currencyconverter.factory.ViewModelFactory
import javax.inject.Inject
import com.t0p47.currencyconverter.R
import com.t0p47.currencyconverter.databinding.LinesDialogFragmentBinding
import com.t0p47.currencyconverter.view.main.MainViewModel
import com.t0p47.currencyconverter.view.main.MainViewModel_Factory

class LinesDialogFragment: DialogFragment(), FragmentInjectable, View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: LinesDialogFragmentBinding

    private val handler = Handler()
    private val runnableClose = Runnable {
        super.dismiss()
    }

    companion object{
        fun newInstance() = LinesDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.lines_dialog_fragment, container, false)
        mainViewModel = injectViewModel(viewModelFactory)

        binding.btnTwoLines.setOnClickListener(this)
        binding.btnThreeLines.setOnClickListener(this)
        binding.btnFourLines.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnTwoLines -> {
                //handler.postDelayed(runnableClose, 3000)
                mainViewModel.changeLinesCount(1)
                super.dismiss()
            }

            R.id.btnThreeLines -> {
                //handler.postDelayed(runnableClose, 3000)
                mainViewModel.changeLinesCount(2)
                super.dismiss()
            }

            R.id.btnFourLines -> {
                //handler.postDelayed(runnableClose, 3000)
                mainViewModel.changeLinesCount(3)
                super.dismiss()
            }
        }
    }
}