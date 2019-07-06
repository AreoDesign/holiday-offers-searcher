package com.home.ans.holidays.repository;

import com.home.ans.holidays.entity.RainbowOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RainbowOfferRepository extends JpaRepository<RainbowOfferEntity, Integer> {
}
