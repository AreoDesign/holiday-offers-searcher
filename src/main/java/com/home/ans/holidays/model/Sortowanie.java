package com.home.ans.holidays.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Sortowanie {
    private boolean czyPoDacie;
    private boolean czyPoCenie;
    private boolean czyPoOcenach;
    private boolean czyPoPolecanych;
    private boolean czyDesc;

}
