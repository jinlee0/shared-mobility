package com.sharedmobility.api.service.customer.entity

import com.sharedmobility.api.global.entity.PkEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.util.*

@Entity(name = "station_fav")
class StationFav(
    @Column(name = "customer_id", columnDefinition = "uuid", nullable = false, updatable = false)
    val customerId: String,
    @Column(name = "station_id", columnDefinition = "uuid", nullable = false, updatable = false)
    val stationId: String
) : PkEntity()