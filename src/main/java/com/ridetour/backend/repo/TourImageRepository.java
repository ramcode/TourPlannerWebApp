package com.ridetour.backend.repo;

import com.ridetour.backend.domains.TourImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by eyal on 5/26/2016.
 */
@Repository
public interface TourImageRepository extends JpaRepository<TourImage, Long> {

    Long deleteByTour_IdAndId(Long tourid, Long id);
}
