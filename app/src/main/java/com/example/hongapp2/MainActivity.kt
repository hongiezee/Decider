package com.example.hongapp2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
//import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView




class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text1 = findViewById<TextInputEditText>(R.id.option1)
        val text2 = findViewById<TextInputEditText>(R.id.option2)
        val text3 = findViewById<TextInputEditText>(R.id.option3)
        val text4 = findViewById<TextInputEditText>(R.id.option4)

        val mainButton = findViewById<Button>(R.id.button)
        val mainLayout = findViewById<View>(R.id.mainLayout)
        mainLayout.setOnTouchListener { v, _ ->
            hideKeyboard(v)
            false
        }
        mainButton.setOnClickListener {
            //another option is to have result on a new activity
//            val myIntent = Intent(this,ResultActivity::class.java)
//            startActivity(myIntent)

            //get number of nonempty fields
//            val option1text = text1.text.toString()

            val textFields = listOf(text1, text2, text3, text4)
            val numNonEmptyFields = textFields.count {
                it.text.toString().isNotBlank()
            }

            //insufficient fields
            if (numNonEmptyFields < 2) {
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                    .setTitle("Not enough options!")
                    .setMessage("Enter at least 2 options")
                    .setPositiveButton("OK") { dialog, _ ->
                        // Dismiss the dialog when the "OK" button is clicked
                        dialog.dismiss()
                    }
                    .create()
                alertDialog.show()
            }

            //2 or more options given
            else {
                val randomNumber = Random.nextInt(1, numNonEmptyFields + 1)
                var winnerText: String = ""

                when (randomNumber) {
                    1 -> winnerText = text1.text.toString()
                    2 -> winnerText = text2.text.toString()
                    3 -> winnerText = text3.text.toString()
                    4 -> winnerText = text4.text.toString()
                    else -> "Error"
                }

                //create custom dialog
                val dialogView =
                    LayoutInflater.from(this).inflate(R.layout.custom_dialog2, null, false)

                val titleTextView = dialogView.findViewById<TextView>(R.id.customTitle)
                titleTextView.text = "Option $randomNumber:\n$winnerText"

                val alertDialog = AlertDialog.Builder(this@MainActivity)
                    .setView(dialogView)
//                    .setTitle("WINNER")
//                    .setMessage("Option $randomNumber: $winnerText")
                    .setPositiveButton("OK") { dialog, _ ->
                        // Dismiss the dialog when the "OK" button is clicked
                        dialog.dismiss()
                    }
                    .create()
                alertDialog.show()
            }


        }
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}
