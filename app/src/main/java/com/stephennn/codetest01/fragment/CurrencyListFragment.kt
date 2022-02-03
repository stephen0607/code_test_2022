package com.stephennn.codetest01.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stephennn.codetest01.adapter.CurrencyListRvAdapter
import com.stephennn.codetest01.databinding.FragmentCurrencyListBinding

class CurrencyListFragment : Fragment() {
    private lateinit var binding: FragmentCurrencyListBinding
    private val vm: CurrencyViewModel by activityViewModels() //share viewModel with activity
    private val currencyListRvAdapter = CurrencyListRvAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initObserver()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initObserver() {
        /** observe data changes and update ui **/
        vm.currencyDataList.observe(this, {
            it?.let {
                currencyListRvAdapter.setDataList(it)
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvCurrencyList.apply {
            currencyListRvAdapter.setOnItemClick(object :
                CurrencyListRvAdapter.RvOnItemClickListener {
                override fun onItemClick(position: Int) {
                    Toast.makeText(
                        this@CurrencyListFragment.activity, "You click item ${
                            vm.currencyDataList.value?.get(position)?.name
                        }", Toast.LENGTH_SHORT
                    ).show()
                }
            })
            adapter = currencyListRvAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }
}

