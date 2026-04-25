package com.vedicmath.app.adapters

interface EntityMapper<Domain, Model> {
    fun map(input: Domain): Model
    fun mapList(inputs: List<Domain>): List<Model> = inputs.map { map(it) }
}
