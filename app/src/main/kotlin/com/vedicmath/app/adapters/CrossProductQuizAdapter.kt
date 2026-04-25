package com.vedicmath.app.adapters

import com.vedicmath.app.models.CrossProductQuizItem

typealias DomainCrossProductQuizItem = CrossProductQuizItem
typealias ModelCrossProductQuizItem = CrossProductQuizItem

object CrossProductQuizAdapter : EntityMapper<DomainCrossProductQuizItem, ModelCrossProductQuizItem> {
    override fun map(input: DomainCrossProductQuizItem): ModelCrossProductQuizItem = CrossProductQuizItem(
        leftNumber = input.leftNumber,
        rightNumber = input.rightNumber,
        expectedCrossTerm = input.expectedCrossTerm,
        typeLabel = input.typeLabel,
        prompt = input.prompt,
        explanation = input.explanation
    )

    fun toModel(domainItem: DomainCrossProductQuizItem): ModelCrossProductQuizItem = map(domainItem)

    fun toModelList(domainItems: List<DomainCrossProductQuizItem>): List<ModelCrossProductQuizItem> =
        mapList(domainItems)
}
