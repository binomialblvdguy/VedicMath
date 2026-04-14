package com.vedicmath.app.models

enum class MethodChoice(val label: String) {
    AUTO("AUTO"),

    MULT_BY_ONE_MORE("BY 1 MORE"),
    MULT_SUM_9("SUM 9 SAME TENS"),
    MULT_SAME_UNITS("SAME UNITS"),
    MULT_RECIPROCAL("RECIPROCALS"),
    MULT_GROUP_1("1-DIGIT GROUP"),
    MULT_GROUP_2("2-DIGIT GROUP"),
    MULT_VERTICAL("VERTICAL"),
    MULT_NEAR_BASE("NEAR-BASE"),
    MULT_SERIES("SERIES"),

    SQUARE_DUPLEX("DUPLEX"),
    SQUARE_ENDS_14("ENDS 1/4"),
    SQUARE_ENDS_5("ENDS 5"),
    SQUARE_ENDS_69("ENDS 6/9"),

    CUBE_1248("ONE-LINE 1|6|12|8"),
    CUBE_RATIO("BASE ROW 1|2|4|8"),
    CUBE_ALGEBRAIC("ALGEBRAIC")
}

data class CalculationResult(
    val methodName: String,
    val result: String,
    val steps: List<String>
)