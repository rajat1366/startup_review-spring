package com.StartupReview.repository;

import com.StartupReview.models.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartupRepository extends JpaRepository<Startup,Long> {
}
