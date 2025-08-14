package com.kadiryasar.controller;

import com.kadiryasar.util.PagerUtil;
import com.kadiryasar.model.RestPageableEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class RestBaseController {

    public <T> RestPageableEntity<T> toPageableEntity(Page<T> userPage){
        return toPageableResponse(userPage, userPage.getContent());
    }

    public Pageable toPageable(int pageNumber, int pageSize, String sortField, boolean asc){
        return PagerUtil.toPageable(pageNumber,pageSize,sortField,asc);
    }

    public <T> RestPageableEntity<T> toPageableResponse(Page<?> page, List<T> content){
        return PagerUtil.toPageableResponse(page,content);
    }

    public <T> RootEntity<T> ok(T payload){
        return RootEntity.ok(payload);
    }

    public <T> RootEntity<T> error(String errorMessage){
        return RootEntity.error(errorMessage);
    }

}
