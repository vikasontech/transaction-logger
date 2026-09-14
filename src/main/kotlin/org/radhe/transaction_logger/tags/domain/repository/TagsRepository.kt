package org.radhe.transaction_logger.tags.domain.repository

import org.radhe.transaction_logger.tags.infrastructure.persistence.TagsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TagsRepository: JpaRepository<TagsEntity, Long> {
    fun findByName(name: String): TagsEntity?
}
