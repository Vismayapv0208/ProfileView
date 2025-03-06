package com.example.profileview.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.profileview.R
import com.example.profileview.adapter.ImageAdapter
import com.example.profileview.model.PersonDetailsResponse
import com.example.profileview.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var backButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var detailsLayout: LinearLayout
    private lateinit var myAdapter: ImageAdapter

    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.recyclerView)
        detailsLayout = view.findViewById(R.id.detailsLayout)
        backButton = view.findViewById(R.id.backButton)

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val personId = arguments?.getInt("person_id")
        if (personId == null) {
            Toast.makeText(requireContext(), "Invalid Person ID", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return
        }

        myAdapter = ImageAdapter(requireContext(), emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = myAdapter

        progressBar.visibility = View.VISIBLE

        viewModel.getPersonImages(personId)
        viewModel.getPersonDetails(personId)

        observeData()
    }

    private fun observeData() {
        viewModel.personImages.observe(viewLifecycleOwner) { images ->
            myAdapter = ImageAdapter(requireContext(), images)
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = myAdapter
        }

        viewModel.personDetails.observe(viewLifecycleOwner) { details ->
            populatePersonDetails(details)
            progressBar.visibility = View.GONE
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun populatePersonDetails(details: PersonDetailsResponse) {
        detailsLayout.removeAllViews()

        val fields = listOf(
            "Name" to details.name,
            "Biography" to details.biography,
            "Birthday" to details.birthday,
            "Deathday" to (details.deathday ?: "N/A"),
            "Gender" to formatGender(details.gender),
            "Place of Birth" to details.place_of_birth,
            "Known For" to details.known_for_department,
            "Popularity" to details.popularity.toString()
        )

        for ((label, value) in fields) {
            val labelTextView = TextView(requireContext()).apply {
                text = "$label:"
                textSize = 16f
                setPadding(0, 16, 0, 4)
                setTextColor(ContextCompat.getColor(context, R.color.purple_700))
            }
            detailsLayout.addView(labelTextView)

            val valueTextView = TextView(requireContext()).apply {
                text = value
                textSize = 14f
                setPadding(0, 0, 0, 16)
                setTextColor(ContextCompat.getColor(context, R.color.purple_200))
            }
            detailsLayout.addView(valueTextView)
        }
    }

    private fun formatGender(gender: Int): String {
        return when (gender) {
            1 -> "Female"
            2 -> "Male"
            else -> "Not specified"
        }
    }
}
