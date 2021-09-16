package ru.uomkri.simbirtest.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.uomkri.simbirtest.base.BaseApplication
import ru.uomkri.simbirtest.databinding.FragmentDetailsBinding
import ru.uomkri.simbirtest.utils.Utils
import javax.inject.Inject

class DetailsFragment: Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding

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

        binding = FragmentDetailsBinding.inflate(inflater)
        val app = requireActivity().application as BaseApplication
        app.appComponent.inject(this)

        setupViewModel()

        return binding.root
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]

        viewModel.getTask(args.id)

        viewModel.task.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.mane.text = it.name
                binding.description.text = it.description
                binding.datetime.text = viewModel.formatDatetime(it.dateStart)
            }
        })
    }
}