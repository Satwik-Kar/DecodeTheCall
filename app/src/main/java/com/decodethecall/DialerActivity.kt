package com.decodethecall

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DialerActivity : AppCompatActivity() {
    private lateinit var phoneNumberDisplay: TextView
    private val numberButtons = mutableListOf<MaterialButton>()
    private val specialButtons = mutableListOf<MaterialButton>()
    private lateinit var callButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialer)
        phoneNumberDisplay = findViewById(R.id.phoneNumberDisplay)

        initializeNumberButtons()
        initializeSpecialButtons()
    }

    private fun initializeNumberButtons() {
        val numberButtonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnStar, R.id.btnHash
        )
        numberButtonIds.forEach { buttonId ->
            val button = findViewById<MaterialButton>(buttonId)
            button.setOnClickListener {
                addToPhoneNumber(button.text.toString())
            }
            numberButtons.add(button)
        }
    }

    private fun initializeSpecialButtons() {
        val backspaceButton = findViewById<MaterialButton>(R.id.btnBackspace)
        backspaceButton.setOnClickListener {
            removeLastDigit()
        }
        backspaceButton.setOnLongClickListener {
            clearPhoneNumber()
            true
        }
        specialButtons.add(backspaceButton)

        callButton = findViewById(R.id.btnCall)
        callButton.setOnClickListener {
            performCall()
        }

        val addContactButton = findViewById<MaterialButton>(R.id.btnAddContact)
        addContactButton.setOnClickListener {
            addContact()
        }
        specialButtons.add(addContactButton)
    }

    private fun addToPhoneNumber(digit: String) {
        val currentNumber = phoneNumberDisplay.text.toString().replace(" ", "")
        if (currentNumber.length < 15) {
            val formattedNumber = formatPhoneNumber(currentNumber + digit)
            phoneNumberDisplay.text = formattedNumber
        }
    }

    private fun removeLastDigit() {
        val currentNumber = phoneNumberDisplay.text.toString().replace(" ", "")
        if (currentNumber.isNotEmpty()) {
            val newNumber = currentNumber.substring(0, currentNumber.length - 1)
            phoneNumberDisplay.text = formatPhoneNumber(newNumber)
        }
    }

    private fun clearPhoneNumber() {
        phoneNumberDisplay.text = ""
    }

    private fun formatPhoneNumber(number: String): String {
        return when {
            number.length <= 5 -> number
            number.length <= 10 -> {
                val firstGroup = number.substring(0, 5)
                val secondGroup = number.substring(5)
                "$firstGroup $secondGroup"
            }
            else -> number
        }
    }

    private fun performCall() {
        val phoneNumber = phoneNumberDisplay.text.toString().replace(" ", "")
        if (phoneNumber.isNotEmpty()) {
            android.widget.Toast.makeText(
                this,
                "Calling: $phoneNumber",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun addContact() {
        val phoneNumber = phoneNumberDisplay.text.toString().replace(" ", "")
        if (phoneNumber.isNotEmpty()) {
            android.widget.Toast.makeText(
                this,
                "Add contact: $phoneNumber",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }
}