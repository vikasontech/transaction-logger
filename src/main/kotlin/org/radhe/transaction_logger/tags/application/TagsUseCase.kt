package org.radhe.transaction_logger.tags.application

import org.radhe.transaction_logger.tags.domain.model.Tags
import org.radhe.transaction_logger.tags.domain.repository.TagsRepository
import org.radhe.transaction_logger.tags.domain.vo.TagVo
import org.radhe.transaction_logger.tags.infrastructure.persistence.TagsEntity
import org.springframework.stereotype.Component

interface TagsUseCase {
    fun handle(tags: TagVo): TagsEntity
    fun getTag(tagName: String): TagsEntity?
}

@Component
class TagsUseCaseImpl(
    private val tagsRepository: TagsRepository,
) : TagsUseCase {
    override fun handle(tags: TagVo): TagsEntity {
        val tags = Tags(tags.name, tags.description)
        Tags.tagsCannotBeDuplicatedPolicy(tagsRepository, tags)
        return tagsRepository.save(tags.toEntity())
    }

    override fun getTag(tagName: String): TagsEntity? {
        return tagsRepository.findByName(tagName)
    }
}