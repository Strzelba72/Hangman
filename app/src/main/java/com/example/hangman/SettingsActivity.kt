package com.example.hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangman.databinding.ActivitySettingsBinding

//seting activity
class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val levelGame = intent.getIntExtra(getString(R.string.level_game_key), 1)
        val languageGame = intent.getBooleanExtra(getString(R.string.language_game_key), false)

        binding.switchSettings.isChecked = languageGame
        if(levelGame in 1 .. 3)
            binding.hangmanSpinnerSettings.setSelection(levelGame-1)

        binding.button3.setOnClickListener{

            val spinnerSelection = binding.hangmanSpinnerSettings.selectedItem.toString().toInt()
            val holdEnable = binding.switchSettings.isChecked

            //przekazanie result
            val result = Intent().apply {
                putExtra(getString(R.string.level_game_key), spinnerSelection)
                putExtra(getString(R.string.language_game_key), holdEnable)
            }
            setResult(RESULT_OK, result)
            finish()
        }

    }
}