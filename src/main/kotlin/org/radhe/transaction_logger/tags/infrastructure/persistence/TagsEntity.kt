package org.radhe.transaction_logger.tags.infrastructure.persistence

import io.swagger.v3.oas.models.tags.Tag
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.radhe.transaction_logger.tags.domain.model.Tags


@Entity
@Table(name = "tag_master")
data class TagsEntity(
    @Id
    @GeneratedValue
    val id: Long?=null,
    val name: String,
    val description: String
) {
    fun toDomain(): Tags {
        return Tags(name, description);
    }
}
