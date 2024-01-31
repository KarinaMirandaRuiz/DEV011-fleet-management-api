package com.example.dev011fleetmanagementapi.model.dao;

import com.example.dev011fleetmanagementapi.model.entity.TaxiEntity;
import com.example.dev011fleetmanagementapi.model.entity.TrajectoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrajectoriesRepository extends JpaRepository<TrajectoryEntity, Integer> {
    Page<TrajectoryEntity> findByTaxi(TaxiEntity taxi, Pageable pageable);
    List<TrajectoryEntity> findByTaxi(TaxiEntity taxi);

    @Query(value = "SELECT ID,TAXI_ID, date, LONGITUDE,LATITUDE FROM public.TRAJECTORIES WHERE (TAXI_ID, date) IN " +
            "(SELECT TAXI_ID,MAX(date) FROM public.TRAJECTORIES GROUP BY TAXI_ID)" , nativeQuery = true)
    List<TrajectoryEntity> findLastTrajectory(Pageable pageable);
}
