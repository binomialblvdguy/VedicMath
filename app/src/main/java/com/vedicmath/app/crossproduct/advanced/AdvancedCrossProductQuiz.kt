package com.vedicmath.app.crossproduct.advanced

data class AdvancedQuizItem(
    val question: AdvancedCrossProductQuestion,
    val ruleId: String,
    val ruleName: String,
    val ruleDescription: String
)

class AdvancedCrossProductQuiz {

    // Generate a new problem using a randomly selected advanced rule
    fun generateNext(): AdvancedQuizItem {
        val rule = AdvancedCrossProductRuleRegistry.allRules.random()
        val q = rule.generate()
        return AdvancedQuizItem(
            question = q,
            ruleId = rule.id,
            ruleName = rule.name,
            ruleDescription = rule.description
        )
    }

    // Check the answer against the actual product left × right
    fun checkAnswer(item: AdvancedQuizItem, answer: Int): Boolean {
        val correct = item.question.left * item.question.right
        return correct == answer
    }
}
