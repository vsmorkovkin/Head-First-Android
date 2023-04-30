package com.hfad.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hfad.tasks.databinding.FragmentTasksBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application // consider that application isn't null
        val dao = TaskDatabase.getInstance(application).taskDao // get dao (create database if it doesn't exist)
        val viewModelFactory = TaskViewModelFactory(dao) // create viewModelFactory (it is needed because we pass argument to viewModel)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TaskViewModel::class.java) // get a ref to viewModel by ViewModelProvider

        // assign viewModel to viewModel layout's variable
        binding.viewModel = viewModel

        // set the layout's lifecycleOwner so that it can respond to live data updates
        binding.lifecycleOwner = viewLifecycleOwner

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}