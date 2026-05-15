package com.nammaskill.app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.nammaskill.app.data.FakeData
import com.nammaskill.app.data.prefs.UserPrefs
import com.nammaskill.app.databinding.FragmentProfileBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFavoriteTradeChips()
        preloadProfile()

        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }
    }

    private fun setupFavoriteTradeChips() {
        binding.favChipGroup.removeAllViews()
        FakeData.trades.forEach { trade ->
            val chip = Chip(requireContext()).apply {
                text = trade
                isCheckable = true
                isClickable = true
                setEnsureMinTouchTargetSize(true)
            }
            binding.favChipGroup.addView(chip)
        }
    }

    private fun preloadProfile() {
        lifecycleScope.launch {
            val profile = UserPrefs(requireContext()).profileFlow.first()
            binding.etName.setText(profile.name)
            binding.etPhone.setText(profile.phone)
            binding.etVillage.setText(profile.village)

            // restore favorites
            for (i in 0 until binding.favChipGroup.childCount) {
                val chip = binding.favChipGroup.getChildAt(i) as? Chip ?: continue
                chip.isChecked = chip.text.toString() in profile.favoriteTrades
            }
        }
    }

    private fun saveProfile() {
        lifecycleScope.launch {
            val name = binding.etName.text?.toString().orEmpty().trim()
            val phone = binding.etPhone.text?.toString().orEmpty().trim()
            val village = binding.etVillage.text?.toString().orEmpty().trim()

            val selectedTrades = mutableSetOf<String>()
            for (i in 0 until binding.favChipGroup.childCount) {
                val chip = binding.favChipGroup.getChildAt(i) as? Chip ?: continue
                if (chip.isChecked) selectedTrades += chip.text.toString()
            }

            UserPrefs(requireContext()).saveProfile(name, phone, village, selectedTrades)
            Toast.makeText(requireContext(), "Profile saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

