package com.randtalk.yonaa.basiccalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {


    private var usedDeci = false
    private var firstNumUsed = false
    private var secondNumUsed = false

    private var numberOne = 0.0
    private var numberTwo = 0.0
    private var arithmeticOp = ""
    private var flag= false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun clickListener(buttonClick: View) {

        val textBox =  findViewById<TextView>(R.id.textView)
        val image = findViewById<ImageView>(R.id.imageView)

        shakeImage(image)

        if (buttonClick is Button) {

            //String from button pressed
            var string = buttonClick.text.toString()
            //println(string)

            if (usedDeci && string == ".") return

            if (isArithmeticOp(string)){

                //If firstNumUsed is true, this means first number was already created
                // so the second number variable should be used. Else the first number
                if (firstNumUsed){
                    numberTwo = getNumber()
                    if (secondNumUsed){
                        processCalculation()
                        secondNumUsed = false
                    }
                    secondNumUsed = true
                } else {
                    numberOne = getNumber()
                }

                //If text box text is blank, make it equal to the number one string
                textBox.text = numberOne.toString()

                // Indicates we used t
                flag = true
                //

                firstNumUsed = true



                usedDeci = false
                arithmeticOp = string
                return
            }




            if (string == "="){

                if (firstNumUsed){
                    numberTwo = getNumber()
                    if (secondNumUsed){
                        processCalculation()
                        secondNumUsed = false
                    }
                    secondNumUsed = true
                } else {
                    textBox.text = numberOne.toString()
                }


                numberTwo = getNumber()
                processCalculation()
            }

            if (string.toIntOrNull() != null || string == ".") {

                if(flag) clearTextBox()

                if (textBox.text.isBlank() && string == "."){
                    textBox.text = "0"
                }

                if (!usedDeci){
                    textBox.text = textBox.text.toString() + string
                    //When user uses the decimal boolean for a number
                    usedDeci = (string == ".")


                } else{
                    if(string == "."){
                        // do nothing if decimal used again for
                        // the same number
                        return
                    }else{

                        textBox.text = textBox.text.toString() + string
                    }
                }


                return
            }



            if(string == "Reset"){
                reset()
                return
            }

        }


    }

    private fun isArithmeticOp(op: String):Boolean{

        return when(op){
            "+" -> true
            "-" -> true
            "*" -> true
            "/" -> true
            else -> false
        }
    }

    fun reset(){
        usedDeci = false
        firstNumUsed = false
        numberOne = 0.0
        numberTwo = 0.0
        flag = false
        clearTextBox()
    }


    private fun clearTextBox():Unit{
        val textBox =  findViewById<TextView>(R.id.textView)
        flag = false
        textBox.text = ""
    }


    private fun getNumber():Double {
        var text = findViewById<TextView>(R.id.textView).text.toString()
        var number = text.toDoubleOrNull()

        if ( number!= null){
            return number
        }

        return 0.0
    }


    private fun calculate(a: Double, b:Double, operation:String):Double{

        return when(operation){
            "+" -> (a+b)
            "-" -> (a-b)
            "/" -> (a/b)
            "*" -> (a*b)
            else -> 0.0

        }
    }

    private fun processCalculation():Unit{

        var textBox = findViewById<TextView>(R.id.textView)



        numberOne = calculate(numberOne,numberTwo,arithmeticOp)
        arithmeticOp = ""
        numberTwo = 0.0
        usedDeci = false

        textBox.text = numberOne.toString()


    }

    private fun shakeImage(view: View){
        var shake = AnimationUtils.loadAnimation(view.context,R.anim.shake)
        view.startAnimation(shake)
    }








}
