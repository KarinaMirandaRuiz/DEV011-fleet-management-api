package com.example.dev011fleetmanagementapi.service.impl;

import com.example.dev011fleetmanagementapi.model.dao.TrajectoriesRepository;
import com.example.dev011fleetmanagementapi.model.entity.TrajectoryEntity;
import com.example.dev011fleetmanagementapi.service.ITrajectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrajectoryImpl implements ITrajectory {

    @Autowired
    private TrajectoriesRepository trajectoriesRepository;

    @Transactional
    @Override
    public TrajectoryEntity save(TrajectoryEntity trajectories) {
        return trajectoriesRepository.save(trajectories);
    }

    @Transactional(readOnly = true)
    @Override
    public TrajectoryEntity findById(Integer id) {
        return trajectoriesRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(TrajectoryEntity trajectories) {
        trajectoriesRepository.delete(trajectories);
    }

    @Transactional
    @Override
    public TrajectoryEntity update(TrajectoryEntity trajectories) {
        return trajectoriesRepository.save(trajectories);
    }
}