package com.ridetour.backend.repo;

import com.ridetour.backend.domains.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by eyal on 5/24/2016.
 */
@Repository
public interface TourRepository extends JpaRepository<Tour, Long>, JpaSpecificationExecutor<Tour> {

    @Override
    void delete(Long id);
}
