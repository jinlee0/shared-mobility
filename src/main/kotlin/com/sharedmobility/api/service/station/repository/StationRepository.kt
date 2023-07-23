package com.sharedmobility.api.service.station.repository

import com.sharedmobility.api.service.station.entity.Station
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StationRepository : JpaRepository<Station, UUID>
