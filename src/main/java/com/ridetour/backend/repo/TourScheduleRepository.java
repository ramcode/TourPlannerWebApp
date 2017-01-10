package com.ridetour.backend.repo;

import com.ridetour.backend.domains.TourSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by eyal on 5/29/2016.
 */
@Repository
public interface TourScheduleRepository extends JpaRepository<TourSchedule, Long> {
    List<TourSchedule> findByTour_Id(Long tourId);
    Long deleteByTour_Id(Long tourId);
}
