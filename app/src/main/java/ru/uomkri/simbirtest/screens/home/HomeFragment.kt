package ru.uomkri.simbirtest.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import ru.uomkri.simbirtest.base.BaseApplication
import ru.uomkri.simbirtest.databinding.FragmentHomeBinding
import ru.uomkri.simbirtest.screens.home.item.HeaderItem
import ru.uomkri.simbirtest.screens.home.item.TaskItem
import ru.uomkri.simbirtest.utils.HourItem
import ru.uomkri.simbirtest.utils.Utils
import javax.inject.Inject

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var utils: Utils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentHomeBinding.inflate(inflater)

        val app = requireActivity().application as BaseApplication
        app.appComponent.inject(this)

        setupViewModel()

        binding.fab.setOnClickListener {
            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewTaskFragment())
        }

        return binding.root
    }

    private fun setupRecyclerView(data: Map<HeaderItem, List<TaskItem>>) {
        val mLayoutManager = LinearLayoutManager(requireContext())

        val mAdapter = GroupieAdapter()

        for (item in data) {
            val section = Section()
            section.apply {
                setHeader(item.key)
                addAll(item.value)
            }
            mAdapter.add(section)
        }

        binding.recyclerView.apply {
            layoutManager = mLayoutManager
            adapter = mAdapter
        }
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        viewModel.getJsonData()

        viewModel.jsonData.observe(viewLifecycleOwner, {
            viewModel.updateRealmFromJson()
        })

        viewModel.selectedDayTasks.observe(viewLifecycleOwner, {
            setupRecyclerView(viewModel.toItems(it))
        })

        binding.calendarView.setOnDayClickListener {
            viewModel.updateSelectedDayTasks(it.calendar.toInstant())
            Log.e("ms", it.calendar.toInstant().toEpochMilli().toString())
        }
    }
}