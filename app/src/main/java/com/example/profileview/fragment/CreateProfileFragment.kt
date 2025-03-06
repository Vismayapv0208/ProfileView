package com.example.profileview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.profileview.R
import com.example.profileview.adapter.ProfileAdapter
import com.example.profileview.databinding.FragmentCreateProfileBinding
import com.example.profileview.databinding.ProfileDialogBinding
import com.example.profileview.model.Profile
import com.example.profileview.viewmodel.CreateProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateProfileFragment : Fragment() {

    private var _binding: FragmentCreateProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateProfileViewModel by viewModels()
    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()
        observeProfiles()
    }

    private fun setupRecyclerView() {
        profileAdapter = ProfileAdapter { profile ->
            showProfileDialog(profile)
        }
        profileAdapter.setClickListener(object : ProfileAdapter.ProfileClickListener {
            override fun onDeleteClick(profile: Profile) {
                showDeleteConfirmationDialog(profile)
            }

            override fun onEditClick(profile: Profile) {
                showProfileDialog(profile)
            }
        })
        binding.profilesRecyclerView.apply {
            adapter = profileAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupFab() {
        binding.addProfileFab.setOnClickListener {
            showProfileDialog(null)
        }
    }

    private fun observeProfiles() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profiles.collect { profiles ->
                    profileAdapter.submitList(profiles)
                }
            }
        }
    }

    private fun showProfileDialog(profile: Profile?) {
        val dialogBinding = ProfileDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
        val dialog = builder.create()

        if (profile != null) {
            dialogBinding.dialogTitleTextView.text = getString(R.string.edit_profile)
            dialogBinding.nameEditText.setText(profile.name)
            dialogBinding.professionEditText.setText(profile.profession)
            dialogBinding.addressEditText.setText(profile.address)
            dialogBinding.deleteButton.visibility = View.VISIBLE
            dialogBinding.deleteButton.setOnClickListener {
                showDeleteConfirmationDialog(profile)
                dialog.dismiss()
            }
        } else {
            dialogBinding.dialogTitleTextView.text = getString(R.string.add_profile)
            dialogBinding.deleteButton.visibility = View.GONE
        }

        dialogBinding.doneButton.setOnClickListener {
            val name = dialogBinding.nameEditText.text.toString()
            val profession = dialogBinding.professionEditText.text.toString()
            val address = dialogBinding.addressEditText.text.toString()

            if (name.isNotEmpty() && profession.isNotEmpty() && address.isNotEmpty()) {
                if (profile != null) {
                    val updatedProfile = profile.copy(name = name, profession = profession, address = address)
                    viewModel.updateProfile(updatedProfile)
                } else {
                    val newProfile = Profile(name = name, profession = profession, address = address)
                    viewModel.insertProfile(newProfile)
                }
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun showDeleteConfirmationDialog(profile: Profile) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Profile")
            .setMessage("Are you sure you want to delete ${profile.name}?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteProfile(profile)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}