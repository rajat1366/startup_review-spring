package com.StartupReview.repository;

import com.StartupReview.models.Startup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartupRepository extends JpaRepository<Startup,Long> {
    Boolean existsByName(String name);

    Page<Startup> findAllByOrderByDateTimeDesc(Pageable pageable);

    Page<Startup> findByNameContainingOrDescriptionContaining(String name,String description,Pageable pageable);

}
