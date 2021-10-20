package com.sabanahabbo.meetu.repository;

import java.util.List;
import java.util.Map;

import com.sabanahabbo.meetu.model.University;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    @Query(value = "SELECT name, interests FROM university_interests t1 INNER JOIN universities t2  ON t1.university_id = t2.id", nativeQuery = true)
    List<Map<String, Object>> getAllInterestByUniversity();
}
