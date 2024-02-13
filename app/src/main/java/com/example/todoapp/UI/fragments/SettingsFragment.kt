package com.example.todoapp.UI.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSettingsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

@Suppress("DEPRECATION")
class SettingsFragment : Fragment() {

    private lateinit var selectedItem: String
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.autoCompleteTVLanguages.setOnItemClickListener { parent, view, position, id ->
            selectedItem = parent.getItemAtPosition(position) as String
            setLocale()
        }

        binding.autoCompleteTVModes.setOnItemClickListener { parent, view, position, id ->
            selectedItem = parent.getItemAtPosition(position) as String
            setMode()
        }

    }

    private fun setMode() {
        val selectedTheme: Int = when (selectedItem) {
            "Dark Mode" -> {
                AppCompatDelegate.MODE_NIGHT_YES
            }

            "Light Mode" -> {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            "System Mode" -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }

            "مظلم" -> {
                AppCompatDelegate.MODE_NIGHT_YES
            }

            "فاتح" -> {
                AppCompatDelegate.MODE_NIGHT_NO
            }

            "وضع النظام" -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

            }


            else -> {
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        }
        AppCompatDelegate.setDefaultNightMode(selectedTheme)
        activity?.recreate()
        selectTasksFragment()
    }

    private fun setLocale() {
        when (selectedItem) {
            "English" -> {
                updateLocale(requireContext(), "en")
            }

            "Arabic" -> {
                updateLocale(requireContext(), "ar")
            }

            "الإنجليزية" -> {
                updateLocale(requireContext(), "en")
            }

            "العربية" -> {
                updateLocale(requireContext(), "ar")
            }

        }
    }

    override fun onResume() {
        super.onResume()
        val languages = resources.getStringArray(R.array.languages)
        val modes = resources.getStringArray(R.array.modes)

        val languagesArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.drop_down_item, languages)
        val modesArrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, modes)
        binding.autoCompleteTVLanguages.setAdapter(languagesArrayAdapter)
        binding.autoCompleteTVModes.setAdapter(modesArrayAdapter)
    }

    private fun updateLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        requireActivity().recreate()
        selectTasksFragment()

    }

    fun selectTasksFragment() {
        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView?.selectedItemId = R.id.tasks

    }
}