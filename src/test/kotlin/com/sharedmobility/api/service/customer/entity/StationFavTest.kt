package com.sharedmobility.api.service.customer.entity

import com.sharedmobility.api.service.customer.repository.CustomerRepository
import com.sharedmobility.api.service.customer.repository.StationFavRepository
import com.sharedmobility.api.service.station.entity.Station
import com.sharedmobility.api.service.station.repository.StationRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class StationFavTest(
    @Autowired
    val customerRepository: CustomerRepository,
    @Autowired
    val stationRepository: StationRepository,
    @Autowired
    val stationFavRepository: StationFavRepository,
) {
    @Test
    fun `StationFav Save`() {
        val c = customerRepository.save(Customer(email = "email", password = "password"))
        val s = stationRepository.save(
            Station(
                name = "s_name",
                lat = 36.125,
                lon = 127.112,
                address1 = "where",
                address2 = "there",
                numHolds = 3
            )
        )
        val sf = stationFavRepository.save(
            StationFav(
                customerId = c.id,
                stationId = s.id
            )
        )
        assertNotNull(sf)
        println(sf)
    }
}