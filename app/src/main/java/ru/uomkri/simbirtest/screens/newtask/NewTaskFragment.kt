package ru.uomkri.simbirtest.screens.newtask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.navigation.findNavController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.buttons
import com.vanpra.composematerialdialogs.datetime.datepicker.datepicker
import com.vanpra.composematerialdialogs.datetime.datetimepicker
import ru.uomkri.simbirtest.base.BaseApplication
import ru.uomkri.simbirtest.screens.details.DetailsViewModel
import ru.uomkri.simbirtest.utils.RawDate
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject


class NewTaskFragment: Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var name = ""
    private var description = ""
    private lateinit var datetime: RawDate

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: NewTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val app = requireActivity().application as BaseApplication
        app.appComponent.inject(this)

        setupViewModel()

        return ComposeView(requireContext()).apply {
            ViewTreeLifecycleOwner.set(this, viewLifecycleOwner)
            setContent {
                Column {
                    Header()
                    Body()
                }
            }
        }
    }

    @Composable
    fun Header() {
        Text(
            "Add a task",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    @Composable
    fun Body() {
        var pName by remember { mutableStateOf("")}
        var pDescription by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = pName,
                onValueChange = {
                    pName = it
                    name = it
                },
                label = { Text(text = ("Name")) }
            )
            OutlinedTextField(
                value = pDescription,
                onValueChange = {
                    pDescription = it
                    description = it
                },
                label = { Text("Description") }
            )
            DateTimeButton()
            ApplyButton()
        }
    }

    @Composable
    fun DateTimeButton() {
        Button(
            onClick = {
                pickDateTime()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Select Date and Time")
        }
    }

    @Composable
    fun ApplyButton() {
        Button(
            onClick = {
                apply()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Apply")
        }
    }

    private fun pickDateTime() {
        val zdt = Instant.now().atZone(ZoneId.of("GMT"))
        val dateDialog = DatePickerDialog(requireContext(), this, zdt.year, zdt.monthValue, zdt.dayOfMonth)
        dateDialog.show()
    }

    private fun apply() {
        if (name.isNotEmpty() && description.isNotEmpty() && this::datetime.isInitialized && datetime.hour != null) {
            viewModel.apply(name, description, datetime)
            requireView().findNavController().popBackStack()
        } else {
            Toast.makeText(requireContext(), "Please fill out all the details", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Log.e("y", "y")
        val zdt = Instant.now().atZone(ZoneId.of("GMT"))
        val timeDialog = TimePickerDialog(requireContext(), this, zdt.hour, zdt.minute, true)
        datetime = RawDate(
            year = year,
            month = month + 1,
            day = day,
            hour = null,
            minute = null
        )
        timeDialog.show()
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        datetime.hour = hour
        datetime.minute = minute
        Log.e("dt", datetime.toString())
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[NewTaskViewModel::class.java]

    }
}
