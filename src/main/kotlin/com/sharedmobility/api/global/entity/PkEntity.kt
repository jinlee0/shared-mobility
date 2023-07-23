package com.sharedmobility.api.global.entity

import jakarta.persistence.*
import org.springframework.data.domain.Persistable
import java.util.*
import kotlin.jvm.Transient

@MappedSuperclass
abstract class PkEntity : Persistable<String> {
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private val id: String = UUID.randomUUID().toString()

    @Transient
    private var _isNew = true

    override fun getId(): String = id

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
