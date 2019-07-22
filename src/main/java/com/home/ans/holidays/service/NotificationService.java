package com.home.ans.holidays.service;

import com.home.ans.holidays.entity.RainbowOfferEntity;
import com.home.ans.holidays.entity.TuiOfferEntity;

public interface NotificationService {

    void notifyAboutRainbowOffer(RainbowOfferEntity offer);

    void notifyAboutTuiOffer(TuiOfferEntity offer);

}
