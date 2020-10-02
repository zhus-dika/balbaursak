package com.balbaursak.webapp.repository;

import com.balbaursak.webapp.domain.Feedback;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Feedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
