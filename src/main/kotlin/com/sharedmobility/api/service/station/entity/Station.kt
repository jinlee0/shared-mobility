package com.sharedmobility.api.service.station.entity

import com.sharedmobility.api.global.entity.PkEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity(name = "station")
class Station(
    name: String,
    lat: Double,
    lon: Double,
    address1: String,
    address2: String,
    numHolds: Int,
) : PkEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "lat", nullable = false)
    var lat: Double = lat
        protected set

    @Column(name = "lon", nullable = false)
    var lon: Double = lon
        protected set

    @Column(name = "address1", nullable = false)
    var address1: String = address1
        protected set

    @Column(name = "address2", nullable = false)
    var address2: String = address2
        protected set

    @Column(name = "n_hold", nullable = false)
    var numHolds: Int = numHolds
        protected set
}
