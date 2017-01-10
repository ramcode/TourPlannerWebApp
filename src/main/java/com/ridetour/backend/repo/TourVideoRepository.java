package com.ridetour.backend.repo;

import com.ridetour.backend.domains.TourVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by eyal on 5/26/2016.
 */
@Repository
public interface TourVideoRepository extends JpaRepository<TourVideo, Long> {

    Long deleteByTour_IdAndId(Long tourid, Long id);

    List<TourVideo> findByTour_Id(Long tourId);
}
