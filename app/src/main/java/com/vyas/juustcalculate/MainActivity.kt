package com.vyas.juustcalculate

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vyas.juustcalculate.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastdot = false
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun onAllClearClick(view: View) {
        binding.dataTv.text =""
        binding.resultTv.text =""
        stateError = false
        lastdot = false
        lastNumeric = false
        binding.resultTv.visibility=View.GONE
    }

    fun onEqualClick(view: View) {
        onEqual()
        binding.dataTv.text=binding.resultTv.text.toString().drop(1)

    }

    fun onDigiClick(view: View) {
        if (stateError){
            binding.dataTv.text =(view as Button).text
             stateError = false
        }
        else{
            binding.dataTv.append((view as Button).text)
        }
        lastNumeric = true
         onEqual()
    }

    fun onopreaterClick(view: View) {
       if (!stateError && lastNumeric){
           binding.dataTv.append((view as Button).text)
           lastdot  = false
           lastNumeric = false
           onEqual()

       }
    }

    fun onBackClick(view: View) {
       binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
        try {
            val lastChar =binding.dataTv.text.toString().last()
            if (lastChar.isDigit()){
                onEqual()
            }
        }catch (e : Exception){
            binding.resultTv.text = ""
            binding.resultTv.visibility = View.GONE
            Log.e( "last thst error", e.toString())

        }
    }

    fun onClearClick(view: View) {
        binding.dataTv.text =""
        lastNumeric = false


    }


    fun onDotClick(view: View) {
        if (!stateError && !lastdot && lastNumeric) {
            binding.dataTv.append((view as Button).text)
            lastNumeric = false
            lastdot = true
            onEqual()
        }
    }


    fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = binding.dataTv.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {

                val result = expression.evaluate()
                binding.resultTv.visibility =View.VISIBLE
                binding.resultTv.text = "="+ result.toString()
            }catch (ex : ArithmeticException){

                Log.e("evaluate error", ex.toString())
                binding.resultTv.text="Error"
                stateError = true
                lastNumeric =false
            }
        }
    }
}




