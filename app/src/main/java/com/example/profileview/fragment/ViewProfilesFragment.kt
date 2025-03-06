package com.example.profileview.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.profileview.adapter.PersonListAdapter
import com.example.profileview.databinding.FragmentViewProfilesBinding
import com.example.profileview.viewmodel.PersonListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewProfilesFragment : Fragment() {

    private var _binding: FragmentViewProfilesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonListViewModel by viewModels()
    private lateinit var personListAdapter: PersonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewProfilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        checkNetworkAndShowMessage()
        observeData()
        setupSearch()
        viewModel.showAllData()
        observeToastMessages()
    }

    private fun setupRecyclerView() {
        personListAdapter = PersonListAdapter { person ->
            // Navigate to DetailFragment using Safe Args
            navigateToDetailFragment(person.id)
        }

        binding.recyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
            adapter = personListAdapter
        }

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest { pagingData ->
                personListAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSearchQuery(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun checkNetworkAndShowMessage() {
        if (isNetworkAvailable(requireContext())) {
            binding.networkStatusTextView.visibility = View.GONE
        } else {
            binding.networkStatusTextView.visibility = View.VISIBLE
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.filteredPeople.collectLatest { filteredPeople ->
                personListAdapter.submitData(filteredPeople)
            }
        }
    }

    private fun observeToastMessages() {
        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    // Navigation using Safe Args
    private fun navigateToDetailFragment(personId: Int) {
        val action = ViewProfilesFragmentDirections.actionViewProfilesFragmentToDetailFragment(personId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
