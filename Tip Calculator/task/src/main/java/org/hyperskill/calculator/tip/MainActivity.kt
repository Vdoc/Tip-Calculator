package org.hyperskill.calculator.tip

import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.TextWatcherAdapter
import com.google.android.material.slider.Slider
import java.math.BigDecimal
import java.math.RoundingMode


class MainActivity : AppCompatActivity() {
    private val BD_HUNDRED = BigDecimal("100")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val billEditText = findViewById<EditText>(R.id.edit_text)
        val tipPercentSlider = findViewById<Slider>(R.id.slider)
        val tipTextView = findViewById<TextView>(R.id.text_view)


        billEditText.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                tipTextView.text =
                        if (s.isBlank())
                            ""
                        else
                            toTipStr(s.toString().toBigDecimal(), tipPercentSlider.value)
            }
        })

        tipPercentSlider.addOnChangeListener { _, value, _ ->
            tipTextView.text =
                    if (billEditText.text.isNullOrBlank())
                        ""
                    else
                        toTipStr(billEditText.text.toString().toBigDecimal(), value)
        }

    }

    private fun calcTip(bill: BigDecimal, percentage: Float, scale: Int = 2): BigDecimal {
        return (bill * percentage.toBigDecimal()).divide(BD_HUNDRED, scale, RoundingMode.HALF_EVEN)
    }

    private fun toTipStr(bill: BigDecimal, percentage: Float): String {
        return "Tip amount: ${calcTip(bill, percentage)}"
    }
}