package com.passwordstrenghtcalculator.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var strength_level_txt: TextView
    private lateinit var strength_level_indicator: View

    private lateinit var lowerCase_img: ImageView
    private lateinit var lowerCase_txt: TextView

    private lateinit var upperCase_img: ImageView
    private lateinit var upperCase_txt: TextView

    private lateinit var digit_img: ImageView
    private lateinit var digit_txt: TextView

    private lateinit var specialChar_img: ImageView
    private lateinit var specialChar_txt: TextView

    private var color: Int = R.color.weak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        strength_level_txt = findViewById(R.id.strength_level_txt)
        strength_level_indicator = findViewById(R.id.strength_level_indicator)

        lowerCase_img = findViewById(R.id.lowerCase_img)
        lowerCase_txt = findViewById(R.id.lowerCase_txt)

        upperCase_img = findViewById(R.id.upperCase_img)
        upperCase_txt = findViewById(R.id.upperCase_txt)

        digit_img = findViewById(R.id.digit_img)
        digit_txt = findViewById(R.id.digit_txt)

        specialChar_img = findViewById(R.id.specialChar_img)
        specialChar_txt = findViewById(R.id.specialChar_txt)

        button = findViewById(R.id.button)

        val passwordStrengthCalculator = PasswordStrengthCalculator()
        val password_input = findViewById<EditText>(R.id.password_input)
        password_input.addTextChangedListener(passwordStrengthCalculator)

        // Observers
        passwordStrengthCalculator.strengthLevel.observe(this, Observer {strengthLevel ->
            displayStrengthLevel(strengthLevel)
        })
        passwordStrengthCalculator.strengthColor.observe(this, Observer {strengthColor ->
            color = strengthColor
        })

        passwordStrengthCalculator.lowerCase.observe(this, Observer {value ->
            displayPasswordSuggestions(value, lowerCase_img, lowerCase_txt)
        })
        passwordStrengthCalculator.upperCase.observe(this, Observer {value ->
            displayPasswordSuggestions(value, upperCase_img, upperCase_txt)
        })
        passwordStrengthCalculator.digit.observe(this, Observer {value ->
            displayPasswordSuggestions(value, digit_img, digit_txt)
        })
        passwordStrengthCalculator.specialChar.observe(this, Observer {value ->
            displayPasswordSuggestions(value, specialChar_img, specialChar_txt)
        })
    }

    private fun displayPasswordSuggestions(value: Int, imageView: ImageView, textView: TextView) {
        if(value == 1){
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.bulletproof))
            textView.setTextColor(ContextCompat.getColor(this, R.color.bulletproof))
        }else{
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.darkGray))
            textView.setTextColor(ContextCompat.getColor(this, R.color.darkGray))
        }
    }

    private fun displayStrengthLevel(strengthLevel: StrengthLevel) {
        button.isEnabled = strengthLevel == StrengthLevel.BULLETPROOF

        strength_level_txt.text = strengthLevel.name
        strength_level_txt.setTextColor(ContextCompat.getColor(this, color))
        strength_level_indicator.setBackgroundColor(ContextCompat.getColor(this, color))
    }
}