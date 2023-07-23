package com.sharedmobility.api.global.entity

import jakarta.persistence.*
import org.springframework.data.domain.Persistable
import java.util.*
import kotlin.jvm.Transient

@MappedSuperclass
abstract class PkEntity : Persistable<UUID> {
    @Id
    @Column(columnDefinition = "uuid")
    private val id: UUID = UUID.randomUUID()

    @Transient
    private var _isNew = true

    override fun getId(): UUID = id

    override fun isNew(): Boolean = _isNew

    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) {
            return false
        }
        return id == (other as PkEntity).id
    }

    override fun hashCode() = Objects.hashCode(id)

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }
}
