package com.home.ans.holidays.repository;

import com.home.ans.holidays.entity.SearchCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchCriteriaRepository extends JpaRepository<SearchCriteria, Integer> {
}
