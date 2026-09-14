package org.radhe.transaction_logger.tags.domain.model

import org.radhe.transaction_logger.tags.domain.repository.TagsRepository
import org.radhe.transaction_logger.tags.infrastructure.persistence.TagsEntity
import org.slf4j.LoggerFactory

data class Tags (val name: String,
                 val description: String) {

    fun toEntity(): TagsEntity {
        return TagsEntity(
            name = name,
            description = description
        )
    }

    init {
        require(name.isNotBlank()) { "Invalid name: $name" }
        require(description.isNotBlank()) { "Invalid description : $name" }
    }

    companion object {
        private val log = LoggerFactory.getLogger(Tags::class.java)
        fun tagsCannotBeDuplicatedPolicy(tagsRepository: TagsRepository,
                                         tags: Tags) {
            val findByName = tagsRepository.findByName(tags.name)
            if (findByName != null) {
                log.error("Duplicated tags named ${tags.name}")
                throw DuplicateTagException()
            }
        }
    }
}

class DuplicateTagException: RuntimeException("Duplicated tag") { }
