package com.example.hangman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hangman.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.intellij.lang.annotations.Language

class MainActivity : AppCompatActivity() {
    lateinit var stringArray: Array<String>
    private lateinit var binding: ActivityMainBinding

    var languageGame=false
    var levelGame=1
    var mistakes=0
    private var word=""
    private var mystery_word=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        Log.i(localClassName,"onCreate")
        //val button1=findViewById<Button>(R.id.button)
        //val button2=findViewById<Button>(R.id.button2)
        //val actionButton=findViewById<FloatingActionButton>(R.id.floatingActionButton)
        stringArray = resources.getStringArray(R.array.words) //import words array
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       //endGame()

        //floating button
        binding.floatingActionButton.setOnClickListener {
            mistakes=0
            resetGame()
            startGame()
            updateImg(mistakes)


        }
        //letter button
        binding.button.setOnClickListener{
                checkChar()
                updateImg(mistakes)
                endGame()
            }
        //word button
        binding.button2.setOnClickListener {
                checkWord()
                updateImg(mistakes)
                endGame()
            }


    }
    //check letter that exist in word
    private fun checkChar()
    {
        val editText=findViewById<EditText>(R.id.editText)
        val textView=findViewById<TextView>(R.id.textView)
        var checkWord=true
        if(editText.text.length==1) {
            for(i in word.indices)
            {
                if(word[i]==editText.text[0])
                {
                    checkWord=false
                    mystery_word = mystery_word.substring(0, i) + editText.text[0] + mystery_word.substring(i + 1)


                }
            }
            if(checkWord)
            {
                    mistakes += 1

            }
            textView.text = mystery_word
            editText.text.clear()
        }



    }
    //check word
    private fun checkWord()
    {

            val editText = findViewById<EditText>(R.id.editText)
            val textView = findViewById<TextView>(R.id.textView)
            //textView.text = editText.text
            var eql = false


            if (editText.text.length == word.length) {
                eql = true
                for (i in word.indices) {

                    if (word[i] != editText.text[i]) {
                        eql = false
                    }
                }

            }
            if (eql) {
                mystery_word = word
            } else {
                if(editText.text.isNotEmpty())
                    mistakes += 1


            }



            editText.text.clear()

    }
    //end game
    private fun endGame()
    {
        binding.floatingActionButton.isClickable=false
        binding.button.isClickable=false
        binding.button2.isClickable=false
        binding.editText.isClickable=false

        if(mystery_word==word)
        {
            Snackbar.make(
                binding.root,
                "WINNER!! CORRECT WORD $word",

                Snackbar.LENGTH_INDEFINITE
            ).setAction("Next?") {
                resetGame()
                startGame()
                updateImg(mistakes)
            }.show()
            //win
        }else if(mistakes>10)
        {
            Snackbar.make(
                binding.root,
                "LOSER CORRECT WORD $word",

                Snackbar.LENGTH_INDEFINITE
            ).setAction("Try Again") {
                resetGame()
                startGame()
                updateImg(mistakes)
            }.show()
        }
        else
        {
            binding.floatingActionButton.isClickable=true
            binding.button.isClickable=true
            binding.button2.isClickable=true
            binding.editText.isClickable=true
        }

    }
    //reset game
    private fun resetGame()
    {

        mistakes=0
        word=""
        mystery_word=""
        binding.floatingActionButton.isClickable=true
        binding.button.isClickable=true
        binding.button2.isClickable=true
        binding.editText.isClickable=true

    }


    //random word
    private fun getRandomWord():String{
        val number=(0..39).random()
        var variable=0
        if(languageGame) {
            variable = 1 * 120 + (levelGame - 1) * 40 + number
        }
        else {
            variable = (levelGame - 1) * 40 + number
        }


            return stringArray[variable]


    }
    //start game
    private fun startGame()
    {
        word=""
        mystery_word=""
        word=getRandomWord()
        val editText=findViewById<EditText>(R.id.editText)
        editText.text.clear()
        var zmienna=""
        val textView=findViewById<TextView>(R.id.textView)
        for(i in word.indices)
        {
            mystery_word+="*"
        }
        textView.text=mystery_word


    }
    //change images
    fun resolveDrawable(value: Int):Int{
        return when (value)
        {
            0->R.drawable.hangman0
            1->R.drawable.hangman1
            2->R.drawable.hangman2
            3->R.drawable.hangman3
            4->R.drawable.hangman4
            5->R.drawable.hangman5
            6->R.drawable.hangman6
            7->R.drawable.hangman7
            8->R.drawable.hangman8
            9->R.drawable.hangman9
            10->R.drawable.hangman10
            else->R.drawable.hangman_gray
        }
    }
    //update images
    private fun updateImg(value: Int)
    {
        val hangman:ImageView=findViewById(R.id.imageView2)
        hangman.setImageResource(resolveDrawable(value))
    }

    override fun onStart() {
        super.onStart()
        Log.i(localClassName,"onStart")//It allows to display message in a console of LogCat
    }

    override fun onResume() {
        super.onResume()
        Log.i(localClassName,"onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.i(localClassName,"onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.i(localClassName,"onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(localClassName,"onDestroy")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> startSettingsActivity()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun startSettingsActivity() {
        val intent: Intent = Intent(this, SettingsActivity::class.java).apply {
            putExtra(getString(R.string.level_game_key), levelGame)
            putExtra(getString(R.string.language_game_key), languageGame)
        }
        launchSettingsActivity.launch(intent)
    }
    //launch settings activity
    private val launchSettingsActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.i(localClassName, "onActivityResult")
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    levelGame = data.getIntExtra(getString(R.string.level_game_key),1)
                    languageGame = data.getBooleanExtra(getString(R.string.language_game_key), false)
                }
                //applySettings()
                resetGame()
                if(languageGame)
                {
                    Snackbar.make(
                        binding.root,
                        "Current settings: levelGame: $levelGame, LanguageGame: English",
                        Snackbar.LENGTH_SHORT
                    ).show()

                }else
                {
                    Snackbar.make(
                        binding.root,
                        "Current settings: levelGame: $levelGame, LanguageGame: Polish",
                        Snackbar.LENGTH_SHORT
                    ).show()

                }


            }
        }


}