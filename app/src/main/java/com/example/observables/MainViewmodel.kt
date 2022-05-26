package com.example.observables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    /**
     * The stateFlow and the Livedata works most likely same way, only that we observe to get a livedata and since
     * flow is a coroutine property we launch a coroutine and collect a flow, all flow functions like collect, emit must be called in a coroutine scope
     */
    private val _liveData = MutableLiveData("livedata1")
    val liveData : LiveData<String> = _liveData


    /**
     * HOT FLOW
     * This is just like the LiveData, it saves states for configuration change and
     * only that the value is gotten with a collect or collectlatest function
     * To add value to a state floe we use .value just like the livedata
     */
    private val _stateFlow = MutableStateFlow("flow state")
    val stateFlow  = _stateFlow.asStateFlow()

    /**
     * HOT FLOW
     * The value of shared state is not save once emitted, emit once and it get deleted, to added data to a state flow we need to use the .emit() function
     * unlike the Stateflow where we use the .value
     */

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    /**
     * COLD FLOW
     * This Flow is a Stream of data pipe that collected data and can be gotten through a coroutine scope with Collect or collectlatest
     */


    fun triggerFlow() : Flow<String>{
        return flow{
            repeat(5){
                emit(" Item $it")
                delay(500)
            }
        }
    }

    fun triggerStateFlow(){
        _stateFlow.value = "stateFlow_sent"
    }

    fun triggerLiveData(){
        _liveData.value = "liveData_sent"
    }



    fun triggerSharedFlow(){
        viewModelScope.launch {
            _sharedFlow.emit("not working")
        }
    }

}