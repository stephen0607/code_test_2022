package com.stephennn.codetest01

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.stephennn.codetest01.databinding.ActivityDemoBinding
import com.stephennn.codetest01.fragment.CurrencyListFragment
import com.stephennn.codetest01.fragment.CurrencyViewModel
import com.stephennn.codetest01.fragment.SortMode


class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding
    private val vm: CurrencyViewModel by viewModels() //share viewModel with fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBtnOnClick()
        initObserver()
        supportFragmentManager.beginTransaction().add(R.id.fc_main, CurrencyListFragment::class.java, null).commit()
    }

    private fun initObserver() {
        vm.sortMode.observe(this, {
            it?.let {
                binding.btnSortList.text = when (it) {
                    /** update btn text when sort mode is change **/
                    SortMode.SORT_NAME_ALPHABETICALLY -> "sort\nby length"
                    SortMode.SORT_NAME_LENGTH -> "sort\nby alphabetically"
                }
            }
        })
    }

    private fun setBtnOnClick() {
        binding.btnGetData.setOnClickListener { vm.getDataList() }
        binding.btnSortList.setOnClickListener { vm.sortingJob() }
    }
}