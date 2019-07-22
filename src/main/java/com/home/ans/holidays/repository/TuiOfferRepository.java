package com.home.ans.holidays.repository;

import com.home.ans.holidays.entity.TuiOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuiOfferRepository extends JpaRepository<TuiOfferEntity, Integer> {
}
