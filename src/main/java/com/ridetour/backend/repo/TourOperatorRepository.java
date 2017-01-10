package com.ridetour.backend.repo;

import com.ridetour.backend.domains.TourOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by eyal on 5/28/2016.
 */
@Repository
public interface TourOperatorRepository extends JpaRepository<TourOperator, Long> {
}
