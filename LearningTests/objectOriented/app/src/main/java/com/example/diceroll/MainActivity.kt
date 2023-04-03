package com.example.diceroll

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Saved a reference to the button object
        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener {
            rollDice()
            val toast = Toast.makeText(this, "Dice Rolled", Toast.LENGTH_SHORT)
            toast.show()
        }

        // Lancio una volta il dado per mostrarlo subito a schermo
        rollDice()
    }

    private fun rollDice() {
        //D6
        val diceImage: ImageView = findViewById(R.id.imageView)
        val dice6 = Dice(6)

        val choice = when (dice6.roll()) {
            1 -> R.drawable.dice_1
            3 -> R.drawable.dice_3
            6 -> R.drawable.dice_6
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_2
        }

        diceImage.setImageResource(choice)
        diceImage.contentDescription = choice.toString()
    }
}