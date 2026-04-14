package com.vedicmath.app.models

internal fun solveOneLineCube(n: Int): CalculationResult {
    return if (n < 100) {
        val a = n / 10
        val b = n % 10
        val c0 = a * a * a
        val c1 = 3 * a * a * b
        val c2 = 3 * a * b * b
        val c3 = b * b * b
        val result = n * n * n

        val steps = mutableListOf<String>()
        steps += "Method: One-Line 1|6|12|8"
        steps += "$n = 10×$a + $b"
        steps += "Use coefficient row 1 : 3 : 3 : 1"
        steps += "Blocks = $c0 | $c1 | $c2 | $c3"

        val blocks = listOf(c0, c1, c2, c3)
        val written = mutableListOf<Int>()
        var carry = 0

        for (i in blocks.indices.reversed()) {
            val total = blocks[i] + carry
            val writeDigit = total % 10
            carry = total / 10
            written.add(0, writeDigit)
            steps += "$total -> write $writeDigit, carry $carry"
        }

        val answerText = buildString {
            if (carry > 0) append(carry)
            written.forEach { append(it) }
        }

        steps += "Answer = $answerText = $result"

        CalculationResult(
            methodName = "One-Line 1|6|12|8",
            result = result.toString(),
            steps = steps
        )
    } else {
        overrideResult(
            name = "One-Line 1|6|12|8",
            base = solveLargeCube(n),
            note = "This one-line cube display is currently shown for two-digit cubes."
        )
    }
}

internal fun solveBaseRowCube(n: Int): CalculationResult {
    return if (n < 100) {
        val a = n / 10
        val b = n % 10

        val row1 = listOf(a * a * a, a * a * b, a * b * b, b * b * b)
        val row2 = listOf(0, 2 * a * a * b, 2 * a * b * b, 0)
        val totalBlocks = listOf(
            row1[0] + row2[0],
            row1[1] + row2[1],
            row1[2] + row2[2],
            row1[3] + row2[3]
        )
        val result = n * n * n

        val steps = mutableListOf<String>()
        steps += "Method: Base Row 1|2|4|8"
        steps += "$n = 10×$a + $b"
        steps += "Base row = ${row1.joinToString(separator = " | ")}"
        steps += "Double under the middle blocks = ${row2.joinToString(separator = " | ")}"
        steps += "Add them = ${totalBlocks.joinToString(separator = " | ")}"

        val written = mutableListOf<Int>()
        var carry = 0

        for (i in totalBlocks.indices.reversed()) {
            val total = totalBlocks[i] + carry
            val writeDigit = total % 10
            carry = total / 10
            written.add(0, writeDigit)
            steps += "$total -> write $writeDigit, carry $carry"
        }

        val answerText = buildString {
            if (carry > 0) append(carry)
            written.forEach { append(it) }
        }

        steps += "Answer = $answerText = $result"

        CalculationResult(
            methodName = "Base Row 1|2|4|8",
            result = result.toString(),
            steps = steps
        )
    } else {
        overrideResult(
            name = "Base Row 1|2|4|8",
            base = solveLargeCube(n),
            note = "This base-row cube display is currently shown for two-digit cubes."
        )
    }
}

internal fun solveAlgebraicCube(n: Int): CalculationResult {
    return if (n < 100) {
        val tens = (n / 10) * 10
        val units = n % 10
        val result = n * n * n

        CalculationResult(
            methodName = "Algebraic Cube",
            result = result.toString(),
            steps = listOf(
                "Method: Algebraic Cube",
                "$n = $tens + $units",
                "Use: (a + b)³ = a³ + 3a²b + 3ab² + b³",
                "a³ = $tens³ = ${tens * tens * tens}",
                "3a²b = 3 × $tens² × $units = ${3 * tens * tens * units}",
                "3ab² = 3 × $tens × $units² = ${3 * tens * units * units}",
                "b³ = $units³ = ${units * units * units}",
                "Answer = $result"
            )
        )
    } else {
        solveLargeCube(n)
    }
}

internal fun solveLargeCube(n: Int): CalculationResult {
    val square = n * n
    val result = square * n

    return CalculationResult(
        methodName = "Repeated Multiplication Cube",
        result = result.toString(),
        steps = listOf(
            "Method: Repeated Multiplication Cube",
            "First compute square: $n × $n = $square",
            "Then multiply by $n",
            "$square × $n = $result",
            "Answer = $result"
        )
    )
}