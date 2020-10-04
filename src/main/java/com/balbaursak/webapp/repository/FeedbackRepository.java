package com.balbaursak.webapp.repository;

import com.balbaursak.webapp.domain.Feedback;
import com.balbaursak.webapp.domain.Produce;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


/**
 * Spring Data  repository for the Feedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Page<Feedback> findAllByProduce(Pageable pageable, Produce produce);
}
