package com.example.observables

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.observables.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MainActivityView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = MainViewModel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activateClicks()
        subscribeToLivedata()
    }


    override fun onLivedataClicked() {

        binding.livedataBtn.setOnClickListener {
            mainViewModel.triggerLiveData()

        }

    }

    override fun onFlowClicked() {
        binding.flowBtn.setOnClickListener {
        lifecycleScope.launch {
            mainViewModel.triggerFlow().collectLatest {
                binding.flow.text  = it
            }
        }
        }
    }
    override fun onStateFlowClicked() {
        binding.stateFlowBtn.setOnClickListener {
            mainViewModel.triggerStateFlow()

        }
    }

    override fun onSharedFlowClicked() {
        binding.sharedFlowBtn.setOnClickListener {
            mainViewModel.triggerSharedFlow()
        }

        lifecycleScope.launch {
            mainViewModel.sharedFlow.collectLatest {
                Snackbar.make(binding.root,it ,Snackbar.LENGTH_LONG).show()

            }

        }
    }

    override fun activateClicks() {
        onLivedataClicked()
        onFlowClicked()
        onStateFlowClicked()
        onSharedFlowClicked()

    }

    private fun subscribeToLivedata() {
        mainViewModel.liveData.observe(this) {
            binding.livedata.text = it
        }

        lifecycleScope.launchWhenCreated {
           mainViewModel.stateFlow.collectLatest {
               binding.stateFlow.text = it
               Snackbar.make(binding.root,it ,Snackbar.LENGTH_LONG).show()
           }
        }
    }


}