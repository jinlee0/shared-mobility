package com.sharedmobility.api.service.customer.repository

import com.sharedmobility.api.service.customer.entity.StationFav
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StationFavRepository : JpaRepository<StationFav, UUID>{
}