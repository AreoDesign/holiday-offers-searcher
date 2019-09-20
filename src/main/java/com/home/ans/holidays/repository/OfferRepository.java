package com.home.ans.holidays.repository;

import com.home.ans.holidays.entity.OfferEntity;
import com.home.ans.holidays.entity.RainbowOfferEntity;
import com.home.ans.holidays.entity.TuiOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Integer> {

    @Query("from OfferRepository as o" +
            "where o.source = 'RAINBOW'")
    List<RainbowOfferEntity> findAllRainbowOffers();

    @Query("from OfferRepository as o" +
            "where o.source = 'TUI'")
    List<TuiOfferEntity> findAllTuiOffers();
}
