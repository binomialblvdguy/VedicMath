package com.vedicmath.app.crossproduct.advanced

class AdvancedCrossProductQuizSession {

    private val quiz = AdvancedCrossProductQuiz()
    private val history = mutableListOf<AdvancedQuizItem>()
    private var index = 0
    var score = 0
        private set

    // Initialize a new session with a given number of questions
    fun newSession(size: Int = 5) {
        history.clear()
        index = 0
        repeat(size) {
            history.add(quiz.generateNext())
        }
        score = 0
    }

    // Get the current item to present in the UI
    fun currentItem(): AdvancedQuizItem? = history.getOrNull(index)

    // Submit an answer for the current item
    fun submitAnswer(answer: Int): Boolean {
        val item = currentItem() ?: return false
        val ok = quiz.checkAnswer(item, answer)
        if (ok) score++
        index++
        return ok
    }

    // Is the session finished?
    fun isFinished(): Boolean = index >= history.size
}
