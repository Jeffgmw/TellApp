package com.teller.tellapp.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teller.tellapp.data.ReferralTrans
import com.teller.tellapp.network.RetrofitClient
import kotlinx.coroutines.launch


class ReferralsViewModel : ViewModel() {
    private val _referralsTrans = mutableStateOf<List<ReferralTrans>>(emptyList())
    val referralsTrans: State<List<ReferralTrans>> get() = _referralsTrans

    init {
        fetchReferrals()
    }

    private fun fetchReferrals() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getAllReferrals()
                if (response.isSuccessful) {
                    response.body()?.entity?.let {
                        _referralsTrans.value = it
                    }
                } else {
                    Log.e("ReferralsViewModel", "Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ReferralsViewModel", "Exception: ${e.message}")
            }
        }
    }
}


