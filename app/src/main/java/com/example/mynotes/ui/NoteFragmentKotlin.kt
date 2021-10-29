package com.example.mynotes.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.example.mynotes.R
import com.example.mynotes.data.NoteDataKotlin
import com.example.mynotes.utils.Publisher
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class NoteFragmentKotlin : Fragment() {

    companion object {
        const val ARG_NOTE_DATA = "Param_NoteData"

        fun newInstance(noteData: NoteDataKotlin?): NoteFragmentKotlin {
            val fragment = NoteFragmentKotlin()
            val args = Bundle()
            args.putParcelable(ARG_NOTE_DATA, noteData)
            fragment.arguments = args
            return fragment
        }
    }

    private var noteData: NoteDataKotlin? = null
    private var publisher: Publisher? = null
    private var title: TextInputEditText? = null
    private var description: TextInputEditText? = null
    private var executed: CheckBox? = null
    private var datePicker: DatePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            noteData = requireArguments().getParcelable(ARG_NOTE_DATA)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = context as MainActivity
        publisher = activity.publisher
    }

    override fun onDetach() {
        publisher = null
        super.onDetach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)
        initView(view)
        // если cardData пустая, то это добавление
        if (noteData != null) {
            populateView()
        }
        return view
    }

    // Здесь соберём данные из views
    override fun onStop() {
        super.onStop()
        noteData = collectNoteData()
    }

    // Здесь передадим данные в паблишер
    override fun onDestroy() {
        super.onDestroy()
        publisher!!.notifySingle(noteData)
    }

    private fun collectNoteData(): NoteDataKotlin {
        val title = Objects.requireNonNull(title!!.text).toString()
        val description = Objects.requireNonNull(description!!.text).toString()
        val date = dateFromDatePicker
        val executed = executed!!.isChecked
        return if (noteData != null) {
            val answer = NoteDataKotlin(title, description, executed, date)
            answer.id = noteData!!.id
            answer
        } else {
            NoteDataKotlin(title, description, false, date)
        }
    }

    // Получение даты из DatePicker
    private val dateFromDatePicker: Date
        get() {
            val cal = Calendar.getInstance()
            cal[Calendar.YEAR] = datePicker!!.year
            cal[Calendar.MONTH] = datePicker!!.month
            cal[Calendar.DAY_OF_MONTH] = datePicker!!.dayOfMonth
            return cal.time
        }

    private fun initView(view: View) {
        title = view.findViewById(R.id.inputTitle)
        description = view.findViewById(R.id.inputDescription)
        datePicker = view.findViewById(R.id.inputDate)
        executed = view.findViewById(R.id.executed)
    }

    private fun populateView() {
        title!!.setText(noteData!!.title)
        description!!.setText(noteData!!.description)
        executed!!.isChecked = noteData!!.isExecuted
        noteData!!.date?.let { initDatePicker(it) }
    }

    // Установка даты в DatePicker
    private fun initDatePicker(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        datePicker!!.init(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH],
            null
        )
    }
}