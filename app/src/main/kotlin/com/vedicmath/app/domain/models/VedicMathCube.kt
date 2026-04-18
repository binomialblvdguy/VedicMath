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
        steps += "Write $n as 10×$a + $b"
        steps += "Use the cube coefficient row 1 : 3 : 3 : 1"
        steps += "Build the four blocks: $c0 | $c1 | $c2 | $c3"

        val blocks = listOf(c0, c1, c2, c3)
        val written = mutableListOf<Int>()
        var carry = 0

        for (i in blocks.indices.reversed()) {
            val total = blocks[i] + carry
            val writeDigit = total % 10
            carry = total / 10
            written.add(0, writeDigit)
            steps += "From right to left: $total gives write $writeDigit and carry $carry"
        }

        val answerText = buildString {
            if (carry > 0) append(carry)
            written.forEach { append(it) }
        }

        steps += "Read the written digits together: $answerText"
        steps += "Final answer = $result"

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
        steps += "Write $n as 10×$a + $b"
        steps += "Base row blocks = ${row1.joinToString(separator = " | ")}"
        steps += "Double the two middle contributions = ${row2.joinToString(separator = " | ")}"
        steps += "Add the rows block by block = ${totalBlocks.joinToString(separator = " | ")}"

        val written = mutableListOf<Int>()
        var carry = 0

        for (i in totalBlocks.indices.reversed()) {
            val total = totalBlocks[i] + carry
            val writeDigit = total % 10
            carry = total / 10
            written.add(0, writeDigit)
            steps += "From right to left: $total gives write $writeDigit and carry $carry"
        }

        val answerText = buildString {
            if (carry > 0) append(carry)
            written.forEach { append(it) }
        }

        steps += "Read the written digits together: $answerText"
        steps += "Final answer = $result"

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
                "Split $n into $tens + $units",
                "Use the identity (a + b)³ = a³ + 3a²b + 3ab² + b³",
                "a³ = $tens³ = ${tens * tens * tens}",
                "3a²b = 3 × $tens² × $units = ${3 * tens * tens * units}",
                "3ab² = 3 × $tens × $units² = ${3 * tens * units * units}",
                "b³ = $units³ = ${units * units * units}",
                "Add all parts together to get the cube",
                "Final answer = $result"
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
            "First compute the square: $n × $n = $square",
            "Then multiply that square by $n",
            "$square × $n = $result",
            "Final answer = $result"
        )
    )
}