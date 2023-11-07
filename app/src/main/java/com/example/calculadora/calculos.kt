package com.example.calculadora

class Calculos {
    // Função para validar a expressão
    fun ValidarExpressao(expressao: String): Double? {
        // Substitui vírgulas por pontos na expressão
        val expressaoCorrigida = expressao.replace(',', '.')

        //Lista de Valoreres e Operadores
        val valores = mutableListOf<Double>()
        val operadores = mutableListOf<Char>()

        // Precedencia das Operações
        val operatorPrecedence = mapOf('+' to 1, '-' to 1, 'X' to 2, '/' to 2)

        // Função para calcular e adicionar na lista
        fun aplicarOperador() {
            if (operadores.isNotEmpty() && valores.size >= 2) {
                val b = valores.removeAt(valores.size - 1)
                val a = valores.removeAt(valores.size - 1)
                val operador = operadores.removeAt(operadores.size - 1)
                when (operador) {
                    '+' -> valores.add(a + b)
                    '-' -> valores.add(a - b)
                    'X' -> valores.add(a * b)
                    '/' -> { if (b == 0.0) {
                            throw ArithmeticException("Divisão por zero")
                        }
                        valores.add(a / b)
                    }
                }
            }
        }
        // Verificações
        var tempNumber = ""
        for (char in expressaoCorrigida) {
            // Verificação para considerar o ponto e fazer a conta com números decimais
            when {
                char.isDigit() || char == '.' -> {
                    tempNumber += char
                }
                // Verificação para considerar os operadores
                char in "+-X/" -> {
                    if (tempNumber.isNotEmpty()) {
                        valores.add(tempNumber.toDouble())
                        tempNumber = ""
                    }
                    // Precedencia das Operações
                    while (operadores.isNotEmpty() && operatorPrecedence.getOrDefault(char, 0)
                        <= operatorPrecedence.getOrDefault(operadores.last(), 0)) {
                        aplicarOperador()
                    }
                    operadores.add(char)
                }
                char == '(' -> operadores.add(char)
                char == ')' -> {
                    if (tempNumber.isNotEmpty()) {
                        valores.add(tempNumber.toDouble())
                        tempNumber = ""
                    }
                    while (operadores.isNotEmpty() && operadores.last() != '(') {
                        aplicarOperador()
                    }
                    if (operadores.isNotEmpty() && operadores.last() == '(') {
                        operadores.removeAt(operadores.size - 1)
                    } else {
                        return null // Expressão inválida (parênteses desbalanceados)
                    }
                }
                char != ' ' -> return null // Caractere inválido
            }
        }
        if (tempNumber.isNotEmpty()) {
            valores.add(tempNumber.toDouble())
        }
        while (operadores.isNotEmpty()) {
            if (operadores.last() == '(' || operadores.last() == ')') return null // Parênteses desbalanceados
            aplicarOperador()
        }
        return if (valores.size == 1 && operadores.isEmpty()) valores[0] else null // Expressão válida
    }
}

