package com.nammaskill.app.ui.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nammaskill.app.data.FakeData
import com.nammaskill.app.databinding.FragmentMapBinding

/**
 * External map launcher (no in-app Google Maps SDK key needed).
 *
 * Why: Showing map tiles INSIDE the app uses Google Maps SDK for Android which needs
 * a Google Maps Platform API key (and billing). To avoid that, we open the user's
 * installed Google Maps app (or browser) directly.
 */
class CenterMapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOpenMaps.setOnClickListener { openGoogleMapsSearch() }

        // Auto-redirect as requested.
        // Comment this line if you prefer a manual button only.
        openGoogleMapsSearch()
    }

    private fun openGoogleMapsSearch() {
        // Opens a Google Maps search with your centers listed in the query.
        val centersText = FakeData.centers.joinToString(separator = " | ") { it.name }
        val query = Uri.encode("government skill center $centersText")
        val uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$query")

        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            // Prefer Google Maps app if installed, else fallback to browser
            setPackage("com.google.android.apps.maps")
        }

        try {
            startActivity(intent)
        } catch (_: Exception) {
            // Fallback: open in any browser if Maps app isn't available
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
