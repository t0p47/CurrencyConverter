package com.t0p47.currencyconverter.view.select_currency

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.t0p47.currencyconverter.R
import com.t0p47.currencyconverter.adapter.CurrencyListRecyclerAdapter
import com.t0p47.currencyconverter.databinding.SelectCurrencyFragmentBinding
import com.t0p47.currencyconverter.extension.FragmentInjectable
import com.t0p47.currencyconverter.extension.injectSharedViewModel
import com.t0p47.currencyconverter.factory.ViewModelFactory
import com.t0p47.currencyconverter.room.entity.CurrencyModelEntity
import com.t0p47.currencyconverter.utils.TAG
import com.t0p47.currencyconverter.view.main.MainViewModel
import java.util.*
import javax.inject.Inject


class SelectCurrencyFragment : Fragment(), FragmentInjectable {

    private lateinit var binding: SelectCurrencyFragmentBinding
    private lateinit var adapter: CurrencyListRecyclerAdapter

    private var searchView: SearchView? = null
    private lateinit var queryTextListener: SearchView.OnQueryTextListener

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    //private lateinit var viewModel: SelectCurrencyViewModel
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = SelectCurrencyFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.select_currency_fragment,
            container,
            false
        )

        viewModel = injectSharedViewModel(viewModelFactory)

        Log.d(TAG, "SelectCurrencyFragment: onCreateView: viewModel: ${viewModel.hashCode()}")

        viewModel.getAllCurrenciesInfo()
        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvCurrencies.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        observeLiveData()

        /*(activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.title = "TOOLBAR"*/

        return binding.root
    }

    private fun observeLiveData(){

        viewModel.currencyModelEntities.observe(viewLifecycleOwner, Observer {
            adapter = CurrencyListRecyclerAdapter(
                it as MutableList<CurrencyModelEntity>,
                viewModel,
                requireContext()
            )
            binding.rvCurrencies.adapter = adapter
            setRecentCurrencyTypes()
        })

        viewModel.newCurrencySelected.observe(viewLifecycleOwner, Observer {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
            findNavController().navigateUp()
        })

        /*viewModel.newFavoriteCurrency.observe(viewLifecycleOwner, Observer {
            binding.rvCurrencies.adapter?.notifyDataSetChanged()
        })*/

    }

    override fun onPause() {
        super.onPause()

        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun setRecentCurrencyTypes(){

        Log.d("LOG_TAG","SelectCurrencyFragment: setRecentCurrencyTypes: size: ${viewModel.recentCurrencyTypes.size}")
        //val intDrawableId = context.resources.getIdentifier("usd", "mipmap", context.packageName)

        if(viewModel.recentCurrencyTypes.isEmpty()){
            binding.recentCurrencyGroup.visibility = View.GONE
            return
        }

        if(viewModel.recentCurrencyTypes.size > 0){
            val intDrawableIdOne = context?.resources?.getIdentifier(
                viewModel.recentCurrencyTypes[0].name.toLowerCase(Locale.getDefault()),
                "mipmap",
                requireContext().packageName
            )
            if (intDrawableIdOne != null) {

                binding.imgRecentOne.visibility = View.VISIBLE
                binding.tvRecentOne.visibility = View.VISIBLE

                binding.imgRecentOne.setImageResource(intDrawableIdOne)
                binding.tvRecentOne.text = viewModel.recentCurrencyTypes[0].name
                binding.imgRecentOne.setOnClickListener {
                    viewModel.selectCurrency(viewModel.recentCurrencyTypes[0])
                    Log.d(
                        "LOG_TAG",
                        "SelectCurrencyFragment: setRecentCurrencyTypes: select first currency"
                    )
                }
            }
        }



        if(viewModel.recentCurrencyTypes.size > 1){
            val intDrawableIdTwo = context?.resources?.getIdentifier(
                viewModel.recentCurrencyTypes[1].name.toLowerCase(Locale.getDefault()),
                "mipmap",
                requireContext().packageName
            )
            if (intDrawableIdTwo != null) {

                binding.imgRecentTwo.visibility = View.VISIBLE
                binding.tvRecentTwo.visibility = View.VISIBLE

                binding.imgRecentTwo.setImageResource(intDrawableIdTwo)
                binding.tvRecentTwo.text = viewModel.recentCurrencyTypes[1].name
                binding.imgRecentTwo.setOnClickListener {
                    viewModel.selectCurrency(viewModel.recentCurrencyTypes[1])
                }
            }
        }



        if(viewModel.recentCurrencyTypes.size > 2){
            val intDrawableIdThree = context?.resources?.getIdentifier(
                viewModel.recentCurrencyTypes[2].name.toLowerCase(Locale.getDefault()),
                "mipmap",
                requireContext().packageName
            )
            if (intDrawableIdThree != null) {

                binding.imgRecentThree.visibility = View.VISIBLE
                binding.tvRecentThree.visibility = View.VISIBLE

                binding.imgRecentThree.setImageResource(intDrawableIdThree)
                binding.tvRecentThree.text = viewModel.recentCurrencyTypes[2].name
                binding.imgRecentThree.setOnClickListener {
                    viewModel.selectCurrency(viewModel.recentCurrencyTypes[2])
                }
            }
        }



        if(viewModel.recentCurrencyTypes.size > 3){
            val intDrawableIdFour = context?.resources?.getIdentifier(
                viewModel.recentCurrencyTypes[3].name.toLowerCase(Locale.getDefault()),
                "mipmap",
                requireContext().packageName
            )
            if (intDrawableIdFour != null) {

                binding.imgRecentFour.visibility = View.VISIBLE
                binding.tvRecentFour.visibility = View.VISIBLE

                binding.imgRecentFour.setImageResource(intDrawableIdFour)
                binding.tvRecentFour.text = viewModel.recentCurrencyTypes[3].name
                binding.imgRecentFour.setOnClickListener {
                    viewModel.selectCurrency(viewModel.recentCurrencyTypes[3])
                }
            }
        }

        binding.recentCurrencyGroup.visibility = View.VISIBLE

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = searchItem.actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        queryTextListener = object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }

        }
        searchView?.setOnQueryTextListener(queryTextListener)

        /*searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }

        })*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_search ->
                return false
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    private fun search(s: String?){
        Log.d("LOG_TAG", "SelectCurrencyFragment: search: query: $s")

        adapter.filterList(s)



        /*adapter.search(s){
            Toast.makeText(context, "Nothing Found", Toast.LENGTH_SHORT).show()
            Log.d("LOG_TAG","SelectCurrencyFragment: search: nothing found")
        }*/
    }
}
