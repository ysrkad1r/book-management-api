package com.kadiryasar.controller;

import com.kadiryasar.dto.DtoBook;
import com.kadiryasar.dto.DtoBookIU;
import com.kadiryasar.dto.DtoBookUpdateIU;
import com.kadiryasar.model.RestPageableEntity;

public interface IRestBookController {

    public RootEntity<DtoBook> saveBook(DtoBookIU dtoBookIU);

    public RootEntity<DtoBook> updateBook(Long bookId ,DtoBookUpdateIU dtoBookUpdateIU);

    public RootEntity<DtoBook> deleteBook(Long id);

    public RootEntity<DtoBook> getBookById(Long id);

    public RootEntity<RestPageableEntity<DtoBook>> getAllBooks(int page,int size,String sortField,boolean asc);

    public RootEntity<RestPageableEntity<DtoBook>> getBooksByUser(Long userId,int page,int size,String sortField,boolean asc);

}
