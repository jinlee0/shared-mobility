package com.sharedmobility.api.service.rent.entity

import com.sharedmobility.api.global.entity.PkEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDateTime
import java.util.*

@Entity(name = "rent")
class Rent(
    @Column(name = "ticket_id", columnDefinition = "uuid", nullable = false, updatable = false)
    val ticketId: UUID,
    @Column(name = "bike_id", columnDefinition = "uuid", nullable = false, updatable = false)
    val bikeId: UUID,
    @Column(name = "start_station_id", columnDefinition = "uuid", nullable = false, updatable = false)
    val startStationId: UUID,
    @Column(name = "end_station_id", columnDefinition = "uuid", nullable = false, updatable = false)
    val endStationId: UUID,
    @Column(name = "rent_at", nullable = false, updatable = false)
    val rentAt: LocalDateTime = LocalDateTime.now(),
    returnedAt: LocalDateTime?,
) : PkEntity() {
    @Column(name = "returned_at")
    var returnedAt: LocalDateTime? = returnedAt
        protected set
}
