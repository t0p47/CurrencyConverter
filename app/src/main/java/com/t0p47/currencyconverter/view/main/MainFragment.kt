package com.t0p47.currencyconverter.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator

import com.t0p47.currencyconverter.R
import com.t0p47.currencyconverter.adapter.CurrencyLineRecyclerAdapter
import com.t0p47.currencyconverter.databinding.MainFragmentBinding
import com.t0p47.currencyconverter.databinding.MainFragmentNewBinding
import com.t0p47.currencyconverter.extension.FragmentInjectable
import com.t0p47.currencyconverter.extension.injectSharedViewModel
import com.t0p47.currencyconverter.extension.injectViewModel
import com.t0p47.currencyconverter.factory.ViewModelFactory
import com.t0p47.currencyconverter.model.enums.CurrencyType
import com.t0p47.currencyconverter.utils.PreferenceHelper
import com.t0p47.currencyconverter.utils.TAG
import com.t0p47.currencyconverter.view.NavViewModel
import com.t0p47.currencyconverter.view.settings.SettingsActivity
import java.util.*
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.floor

class MainFragment : Fragment(), FragmentInjectable, View.OnClickListener {

    private lateinit var binding: MainFragmentBinding

    @Inject
    lateinit var sharPref: PreferenceHelper

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var navViewModel: NavViewModel

    private var adapter: CurrencyLineRecyclerAdapter? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG,"MainFragment: onCreateView")
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        viewModel = injectSharedViewModel(viewModelFactory)
        navViewModel = ViewModelProvider(requireActivity()).get(NavViewModel::class.java)

        val inputCurrencyType = sharPref.restoreInputCurrencyType()

        Log.d(TAG,"MainFragment: onCreateView: inputCurrencyType: $inputCurrencyType")

        binding.smallName = inputCurrencyType.currencyNameRes
        binding.bigName = inputCurrencyType.name
        //binding.imgCurrency.setImageResource(resources.getIdentifier(it.name.toLowerCase(), "mipmap", context?.packageName))
        binding.imgCurrency.setImageResource(resources.getIdentifier(inputCurrencyType.name.toLowerCase(
            Locale.getDefault()), "mipmap", context?.packageName))

        val currencyAndroid = Currency.getInstance(inputCurrencyType.name.toLowerCase(Locale.getDefault()))
        binding.tvCurrencySymbol.text = currencyAndroid.symbol





        //TODO: Make input from SharedPreferences
        if(viewModel.watchedCurrencyTypes.value == null){
            val inputValue = sharPref.restoreInputValue()

            Log.d(TAG,"MainFragment: onCreateView: viewModel: inputValue: $inputValue")

            if(ceil(inputValue) == floor(inputValue)){
                binding.tvCurrencyRate.text = inputValue.toInt().toString()
            }else{
                binding.tvCurrencyRate.text = inputValue.toString()
            }


            viewModel.initCurrencies(sharPref.restoreCalculateCurrencyTypes(), inputValue, inputCurrencyType)
            Log.d(TAG,"MainFragment: onCreateView: initCurrencies firstTime: $inputValue, inpCurrencyType ${sharPref.restoreInputCurrencyType()}")

        }else{
            if(ceil(viewModel.inputValue) == floor(viewModel.inputValue)){
                binding.tvCurrencyRate.text = viewModel.inputValue.toInt().toString()
            }else{
                binding.tvCurrencyRate.text = viewModel.inputValue.toString()
            }
        }


        //binding.rlFirstLine.measure(0,0)
        //Log.d(TAG,"MainFragment: onCreate: rlFirstLine height: ${binding.rlFirstLine.height}, measuredHeight: ${binding.rlFirstLine.measuredHeight}")

        //(SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        (binding.rvLines.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.rlFirstLine.setOnClickListener(this)
        //binding.imgCurrency.setOnClickListener(this)


        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btnDel.setOnClickListener(this)
        binding.btnDot.setOnClickListener(this)
        binding.btnChange.setOnClickListener(this)
        binding.btnLines.setOnClickListener(this)
        binding.btnSettings.setOnClickListener(this)

        observeLiveData()

        return binding.root
    }

    private fun observeLiveData(){
        viewModel.watchedCurrencyTypes.observe(viewLifecycleOwner, Observer {
            val isRVNull = binding.rvLines.adapter == null
            Log.d(TAG,"MainFragment: observeLiveData: watchedCurrencyTypes: currencyType count: ${it.size}, isRVNull: $isRVNull")

            if(adapter == null){
                Log.d(TAG,"MainFragment: observeLiveData: watchedCurrencyTypes: adapter is null")
                adapter = CurrencyLineRecyclerAdapter(viewModel, navViewModel, binding.rlFirstLine.measuredHeight, requireContext())
                binding.rvLines.adapter = adapter
                adapter!!.submitList(it)
            }else{
                Log.d(TAG,"MainFragment: observeLiveData: watchedCurrencyTypes: adapter NOT null")
                if(binding.rvLines.adapter == null){
                    adapter = CurrencyLineRecyclerAdapter(viewModel, navViewModel, binding.rlFirstLine.measuredHeight, requireContext())
                    binding.rvLines.adapter = adapter
                }
                adapter!!.submitList(it)
                //binding.rvLines.adapter = adapter
            }



            //binding.smallName = viewModel.inputCurrencyEntity.currency.currencyNameRes
            //binding.bigName = viewModel.inputCurrencyEntity.currency.name
        })

        viewModel.changeCurrencyLive.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"MainFragment: observeLiveData: changeCurrencyLive: currency: $it, line: ${viewModel.changeLine}")
            if(viewModel.changeLine==1){
                Log.d(TAG,"MainFragment: observeLiveData: changeCurrencyLive: firstLine: $it")
                binding.bigName = it.name
                binding.smallName = it.currencyNameRes
                val currencyAndroid = Currency.getInstance(it.name.toLowerCase(Locale.getDefault()))
                binding.tvCurrencySymbol.text = currencyAndroid.symbol

                binding.imgCurrency.setImageResource(resources.getIdentifier(it.name.toLowerCase(Locale.getDefault()), "mipmap", context?.packageName))

                /*
                * context.resources.getIdentifier(currencyLine.currencyEntity.currency.name.toLowerCase(), "mipmap", context.packageName)

                * */

                viewModel.calculate(binding.tvCurrencyRate.text.toString().toFloat())
            }else{
                Log.d(TAG,"MainFragment: observeLiveData: changeCurrencyLive: input: ${binding.tvCurrencyRate.text.toString().toFloat()}")
                viewModel.changeCalculateLine(binding.tvCurrencyRate.text.toString().toFloat())
            }




            //viewModel.calculate(binding.tvCurrencyRate.text.toString().toFloat())
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnChart -> {
                //MainFragmentDirections.
            }
            R.id.btnLines -> {
                navViewModel.setNewDestination(MainFragmentDirections.actionMainFragmentToLinesDialogFragment().actionId)
            }
            R.id.btnChange -> {
                viewModel.shuffleLines()
            }
            R.id.btnSettings -> {
                Log.d(TAG,"MainFragment: onClick: settings")
                startActivity(Intent(requireContext(), SettingsActivity::class.java))
            }


            R.id.rlFirstLine -> {
                viewModel.changeLine = 1
                navViewModel.setNewDestination(MainFragmentDirections.actionMainFragmentToSelectCurrencyFragment().actionId)
            }


            R.id.btn0 -> {
                if(binding.tvCurrencyRate.text.length > 9){
                    return
                }
                Log.d(TAG,"MainFragment: btn0 click")
                binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}0"
                val input = binding.tvCurrencyRate.text.toString().toFloat()
                sharPref.storeInputValue(input)
                viewModel.calculate(input)
            }
            R.id.btn1 -> {
                if(binding.tvCurrencyRate.text.length > 9){
                    return
                }
                Log.d(TAG,"MainFragment: btn1 click")
                if(binding.tvCurrencyRate.text == "0"){
                    binding.tvCurrencyRate.text = "1"
                }else{
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}1"
                }

                val input = binding.tvCurrencyRate.text.toString().toFloat()
                sharPref.storeInputValue(input)
                viewModel.calculate(input)
            }

            R.id.btn2 -> {
                if(binding.tvCurrencyRate.text.length > 9){
                    return
                }
                Log.d(TAG,"MainFragment: btn2 click")
                if(binding.tvCurrencyRate.text == "0"){
                    binding.tvCurrencyRate.text = "2"
                }else{
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}2"
                }
                val input = binding.tvCurrencyRate.text.toString().toFloat()
                sharPref.storeInputValue(input)
                viewModel.calculate(input)
            }

            R.id.btn3 -> {
                if(binding.tvCurrencyRate.text.length > 9){
                    return
                }
                Log.d(TAG,"MainFragment: btn3 click")
                if(binding.tvCurrencyRate.text == "0"){
                    binding.tvCurrencyRate.text = "3"
                }else{
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}3"
                }
                val input = binding.tvCurrencyRate.text.toString().toFloat()
                sharPref.storeInputValue(input)
                viewModel.calculate(input)
            }

            R.id.btn4 -> {
                if(binding.tvCurrencyRate.text.length > 9){
                    return
                }
                Log.d(TAG,"MainFragment: btn4 click")
                if(binding.tvCurrencyRate.text == "0"){
                    binding.tvCurrencyRate.text = "4"
                }else{
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}4"
                }
                val input = binding.tvCurrencyRate.text.toString().toFloat()
                sharPref.storeInputValue(input)
                viewModel.calculate(input)
            }
            R.id.btn5 -> {
                if(binding.tvCurrencyRate.text.length > 9){
                    return
                }
                Log.d(TAG,"MainFragment: btn5 click")
                if(binding.tvCurrencyRate.text == "0"){
                    binding.tvCurrencyRate.text = "5"
                }else{
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}5"
                }
                val input = binding.tvCurrencyRate.text.toString().toFloat()
                sharPref.storeInputValue(input)
                viewModel.calculate(input)
            }
            R.id.btn6 -> {
                if(binding.tvCurrencyRate.text.length > 9){
                    return
                }
                Log.d(TAG,"MainFragment: btn6 click")
                if(binding.tvCurrencyRate.text == "0"){
                    binding.tvCurrencyRate.text = "6"
                }else{
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}6"
                }
                val input = binding.tvCurrencyRate.text.toString().toFloat()
                sharPref.storeInputValue(input)
                viewModel.calculate(input)
            }
            R.id.btn7 -> {
                if(binding.tvCurrencyRate.text.length > 9){
                    return
                }
                Log.d(TAG,"MainFragment: btn7 click")
                if(binding.tvCurrencyRate.text == "0"){
                    binding.tvCurrencyRate.text = "7"
                }else{
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}7"
                }
                val input = binding.tvCurrencyRate.text.toString().toFloat()
                sharPref.storeInputValue(input)
                viewModel.calculate(input)
            }
            R.id.btn8 -> {
                if(binding.tvCurrencyRate.text.length > 9){
                    return
                }
                Log.d(TAG,"MainFragment: btn8 click")
                if(binding.tvCurrencyRate.text == "0"){
                    binding.tvCurrencyRate.text = "8"
                }else{
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}8"
                }
                val input = binding.tvCurrencyRate.text.toString().toFloat()
                sharPref.storeInputValue(input)
                viewModel.calculate(input)
            }
            R.id.btn9 -> {
                if(binding.tvCurrencyRate.text.length > 9){
                    return
                }
                Log.d(TAG,"MainFragment: btn9 click")
                if(binding.tvCurrencyRate.text == "0"){
                    binding.tvCurrencyRate.text = "9"
                }else{
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}9"
                }
                val input = binding.tvCurrencyRate.text.toString().toFloat()
                sharPref.storeInputValue(input)
                viewModel.calculate(input)
            }
            R.id.btnDel -> {
                Log.d(TAG,"MainFragment: btnDel: ${binding.tvCurrencyRate.text}, after: ${binding.tvCurrencyRate.text.subSequence(0, binding.tvCurrencyRate.text.length-1)}")
                if(binding.tvCurrencyRate.text.length > 1){
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text.subSequence(0, binding.tvCurrencyRate.text.length-1)}"
                    val input = binding.tvCurrencyRate.text.toString().toFloat()
                    sharPref.storeInputValue(input)
                    viewModel.calculate(input)
                }else{
                    binding.tvCurrencyRate.text = "0"
                    val input = binding.tvCurrencyRate.text.toString().toFloat()
                    sharPref.storeInputValue(input)
                    viewModel.calculate(input)
                }

            }
            R.id.btnDot -> {
                if(!binding.tvCurrencyRate.text.contains('.')){
                    Log.d(TAG,"MainFragment: btnDot: before: ${binding.tvCurrencyRate.text}")
                    binding.tvCurrencyRate.text = "${binding.tvCurrencyRate.text}."
                    Log.d(TAG,"MainFragment: btnDot: after: ${binding.tvCurrencyRate.text.toString().toFloat()}")
                    viewModel.calculate(binding.tvCurrencyRate.text.toString().toFloat())
                }
            }
        }
    }
}
