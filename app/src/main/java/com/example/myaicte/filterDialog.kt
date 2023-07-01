package com.example.myaicte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment



class filterDialog: DialogFragment() {

    override fun getTheme(): Int {
        //return super.getTheme()
        return R.style.MyDialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.filterdialog,container, false)
        //filterDialog().dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)


        var cancelButton = rootView.findViewById<ImageView>(R.id.imageViewclose)
        var submitButton = rootView.findViewById<Button>(R.id.submitbtn)
        var resetButton = rootView.findViewById<Button>(R.id.resetbtn)

        cancelButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                dismiss()
                Toast.makeText(requireContext(),"Clicked Close", Toast.LENGTH_SHORT).show()
            }
        })
        submitButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(requireContext(),"Clicked Submit", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })
        resetButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(requireContext(),"Clicked Reset", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        })

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val languages = resources.getStringArray(R.array.entries)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropitem, languages)
        // get reference to the autocomplete text view
        val autocompleteTV = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)
    }


}