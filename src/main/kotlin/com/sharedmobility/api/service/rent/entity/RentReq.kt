package com.sharedmobility.api.service.rent.entity

import com.sharedmobility.api.global.entity.PkEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.util.UUID

@Entity(name = "rent_req")
class RentReq(
    @Column(name = "bike_id", columnDefinition = "uuid", nullable = false, updatable = false)
    val bikeId: UUID,
    @Column(name = "ticket_id", columnDefinition = "uuid", nullable = false, updatable = false)
    val ticketId: UUID,
) : PkEntity()
