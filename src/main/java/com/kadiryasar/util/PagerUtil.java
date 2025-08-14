package com.kadiryasar.util;

import com.kadiryasar.model.RestPageableEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@UtilityClass
public class PagerUtil {

    public boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public Pageable toPageable(int pageNumber, int pageSize, String sortField, boolean asc) {
        Sort sortBy = isNullOrEmpty(sortField)
                ? Sort.unsorted() // Eğer sıralama alanı boşsa sıralama yapılmaz
                : (asc ? Sort.by(Sort.Direction.ASC, sortField) : Sort.by(Sort.Direction.DESC, sortField));
        return PageRequest.of(pageNumber, pageSize, sortBy);
    }

    public <T> RestPageableEntity<T> toPageableResponse(Page<?> page, List<T> content) {
        RestPageableEntity<T> pageableEntity = new RestPageableEntity<>();
        pageableEntity.setContent(content);
        pageableEntity.setPageNumber(page.getPageable().getPageNumber());
        pageableEntity.setPageSize(page.getPageable().getPageSize());
        pageableEntity.setTotalElements(page.getTotalElements());
        pageableEntity.setTotalPages(page.getTotalPages());
        pageableEntity.setFirst(page.isFirst());
        pageableEntity.setLast(page.isLast());
        pageableEntity.setHasNext(page.hasNext());
        pageableEntity.setHasPrevious(page.hasPrevious());
        pageableEntity.setSort(page.getSort().toString());

        return pageableEntity;
    }
}
