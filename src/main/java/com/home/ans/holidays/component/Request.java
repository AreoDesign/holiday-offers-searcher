package com.home.ans.holidays.component;

import org.springframework.http.HttpEntity;

public interface Request {

    HttpEntity prepareHttpEntity(int read);

}
