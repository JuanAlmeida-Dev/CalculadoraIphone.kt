package com.example.calculadora

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.GrayCustom
import com.example.calculadora.ui.theme.OrangeCustom
import java.math.RoundingMode

@Composable
fun CalculatorUI() {
    //Variaveis
    var expressao = remember { mutableStateOf("") }
    val buttonSpacing = 10.dp

    //Tela
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
                reverseLayout = true
            ){
                item {
                    //Expressão
                    Text(
                        text = expressao.value,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp, horizontal = 8.dp),
                        fontWeight = FontWeight.Light,
                        fontSize = 80.sp,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }
            //Dividir a expressão entre os Botões
            Divider(
                color = Color.Black,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            //Botões
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)) {

                NumberButton("C", expressao)
                NumberButton("(", expressao)
                NumberButton(")", expressao)
                NumberButton("/", expressao)

            }
            //Botões
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)) {
                NumberButton("1", expressao)
                NumberButton("2", expressao)
                NumberButton("3", expressao)
                NumberButton("X", expressao)
            }
            //Botões
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)) {
                NumberButton("4", expressao)
                NumberButton("5", expressao)
                NumberButton("6", expressao)
                NumberButton("-", expressao)
            }
            //Botões
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)) {
                NumberButton("7", expressao)
                NumberButton("8", expressao)
                NumberButton("9", expressao)
                NumberButton("+", expressao)
            }
            //Botões
            Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)) {
                NumberButton(",", expressao)
                NumberButton("0", expressao)
                NumberButton("AC", expressao)
                NumberButton("=", expressao)
            }
        }
    }
}

@Composable
fun NumberButton(number: String, expressao: MutableState<String>) {
    val model = Calculos()

    //Cores dos Botões
    val backgroundColor = when (number) {
        "C", "(", ")" -> GrayCustom
        "/", "+", "-", "X", "=" -> OrangeCustom
        else -> Color.DarkGray
    }

    //Cores dos textos Botões
    val textColor = if (number == "C" || number == "(" || number == ")") {
        Color.Black
    } else {
        Color.White
    }

    //Botão
    Button(
        onClick = {
            //Apagar toda a expressão
            if (number == "AC") {
                expressao.value = ""

            //Apagar um valor da expressão
            } else if (number == "C") {
                expressao.value = expressao.value.dropLast(1)

            //Calcular a expressão
            } else if (number == "=") {
                try{
                    //Resutado
                    val resultado = model.ValidarExpressao(expressao.value)
                    val arredondado = resultado?.toBigDecimal()?.setScale(5, RoundingMode.DOWN)?.toDouble()
                    expressao.value = arredondado.toString()

                } catch (erro: ArithmeticException){
                    expressao.value = ""
                }
            //Verificar se só um token está na expressão
            } else {
                if (number in listOf("+", "-", "X", "/", ",", "(", ")")) {
                    if (!expressao.value.contains(number)) {
                        expressao.value += number
                    }
                } else {
                    expressao.value += number
                }
            }
        },
        modifier = Modifier.size(80.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor)

    ) {
        //Design dos valores dos botões
        Text(
            text = number,
            style = TextStyle.Default,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorUI()
}