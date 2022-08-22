package com.example.devops_matheus.ui.screens.quote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.devops_matheus.R
import com.example.devops_matheus.databinding.FragmentQuoteBinding
import com.example.devops_matheus.ui.database.quote.QuoteDatabase
import com.example.devops_matheus.ui.database.users.UserDatabase
import com.example.devops_matheus.ui.screens.profile.ProfileViewModel
import com.example.devops_matheus.ui.screens.profile.ProfileViewModelFactory

class QuoteFragment: Fragment() {

    private lateinit var binding: FragmentQuoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quote, container, false)

        val appContext = requireNotNull(this.activity).application
        val dataSource = QuoteDatabase.getInstance(appContext)

        val viewModelFactory = QuoteViewModelFactory(dataSource, appContext)
        val viewModel: QuoteViewModel by viewModels{viewModelFactory}

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}