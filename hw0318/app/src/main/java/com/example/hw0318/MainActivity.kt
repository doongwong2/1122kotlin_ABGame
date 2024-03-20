package com.example.hw0318

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    var result by mutableStateOf("")
    var input by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ABGame(this@MainActivity)
        }
        generateAnswer()

        // Toast.makeText(this, "" + answer[0] +
        //    answer[1] + answer[2] + answer[3],
        //    Toast.LENGTH_LONG).show()
    }

    @Composable
    fun ABGame(context: Context) {

        Column{
            Text(text = stringResource(id = R.string.input_hint),
                fontSize = 24.sp)
            TextField(value = input,
                onValueChange = {input = it},
                keyboardOptions =
                KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Row{
                Button(onClick = {
                    result = buttonClicked(input)
                    input = ""
                }) {
                    Text(text = stringResource(id = R.string.calculate_button))
                }

                Button(onClick = {
                    Toast.makeText(this@MainActivity,
                        "${answer[0]}" + "${answer[1]}" + "${answer[2]}" +"${answer[3]}",
                        Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = stringResource(id = R.string.cheat_button))
                }
                Button(onClick = {
                    generateAnswer()
                    Toast.makeText(this@MainActivity,
                        "Answer has been shuffled",
                        Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = stringResource(id = R.string.shuffle))
                }
            }
            Text(text = result,
                fontSize = 24.sp)

            //history
            Text(text = result,
                fontSize = 24.sp,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
                    .height(200.dp))

        }
    }


    fun buttonClicked(input: String): String {

        if (input.length != 4) {
            Toast.makeText(this@MainActivity,
                R.string.input_error,
                Toast.LENGTH_LONG).show()
            return ""
        }
        return compare(input)
    }

    var answer: IntArray = intArrayOf(0, 0, 0, 0)


    fun generateAnswer()
    {
        var genArray: IntArray = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

        for(i in 0..3)
        {
            var j = ((Math.random()*10).toInt()) % 10
            var temp = genArray[j]
            genArray[j] = genArray[i]
            genArray[i] = temp
        }

        for(i in 0..3)
        {
            answer[i] = genArray[i]
        }

    }


    //teacher's example
//    fun generateAnswer() {
//        for (i in 0..3) {
//            var breakflag: Boolean = true
//            do {
//                breakflag = true
//                answer[i] = (Math.random()*10).toInt()
//                for (j in 0..(i-1)) {
//                    if (answer[i] == answer[j]) {
//                        breakflag = false
//                        break
//                    }
//                }
//            } while (!breakflag)
//        }
//    }

    fun compare(input_str: String): String {

        var guess: Int = 0
        try {
            guess = input_str.toInt()
        } catch (e: NumberFormatException) {
            return this.getString(R.string.input_error)
        }


        var guessarray: IntArray = intArrayOf(0, 0, 0, 0)
        var x: Int = 10000
        for (i in 0..3) {
            guessarray[i] = (guess%x)/(x/10)
            x = x/10
        }

        for(i in 0..3)
            for(j in 0..3)
            {
                if(guessarray[i] == guessarray[j])
                    return this.getString(R.string.repeated_digits)

            }

        var a: Int = 0
        var b: Int = 0

        for (i in 0..3) {
            if (guessarray[i] == answer[i]) {
                a += 1
            }

            for (j in 0..3) {
                if (i == j) continue
                if (guessarray[i] == answer[j]) {
                    b = b + 1
                }
            }
        }
        return (this.getString(R.string.result_hint) + " " + a + "A" + b + "B")
    }

}