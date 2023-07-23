package com.sharedmobility.api.service.bike.entity

import com.sharedmobility.api.global.entity.PkEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.util.UUID

@Entity(name = "bike")
class Bike(
    @Column(name = "station_id", columnDefinition = "uuid", nullable = false, updatable = false)
    val stationId: UUID
) : PkEntity()
