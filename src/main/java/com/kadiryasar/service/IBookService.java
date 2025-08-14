package com.kadiryasar.service;

import com.kadiryasar.dto.DtoBook;
import com.kadiryasar.dto.DtoBookIU;
import com.kadiryasar.dto.DtoBookUpdateIU;
import com.kadiryasar.dto.DtoUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookService {

    public DtoBook saveBook(DtoBookIU dtoBookIU);

    public DtoBook updateBook(Long bookId, DtoBookUpdateIU dtoBookUpdateIU);

    public DtoBook deleteBook(Long id);

    public DtoBook getBookById(Long bookId);

    public Page<DtoBook> getAllBooks(Pageable pageable);

    public Page<DtoBook> getBooksByUser(Long userId, Pageable pageable);

}
