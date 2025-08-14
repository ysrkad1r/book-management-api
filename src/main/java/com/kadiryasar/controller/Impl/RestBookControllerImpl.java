package com.kadiryasar.controller.Impl;

import com.kadiryasar.controller.IRestBookController;
import com.kadiryasar.controller.RestBaseController;
import com.kadiryasar.controller.RootEntity;
import com.kadiryasar.dto.DtoBook;
import com.kadiryasar.dto.DtoBookIU;
import com.kadiryasar.dto.DtoBookUpdateIU;
import com.kadiryasar.service.IBookService;
import com.kadiryasar.model.RestPageableEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rest/api/user")
public class RestBookControllerImpl extends RestBaseController implements IRestBookController {

    private final IBookService bookService;

    @PostMapping(path = "/books/save-book")
    @Override
    public RootEntity<DtoBook> saveBook(@Valid @RequestBody DtoBookIU dtoBookIU) {
        return ok(bookService.saveBook(dtoBookIU));
    }

    @PutMapping(path = "/books/update-book/{id}")
    @PreAuthorize("@bookSecurity.isOwner(#bookId)")
    @Override
    public RootEntity<DtoBook> updateBook(@PathVariable(name = "id") Long bookId,@RequestBody DtoBookUpdateIU dtoBookUpdateIU) {
        return ok(bookService.updateBook(bookId,dtoBookUpdateIU));
    }

    @DeleteMapping(path = "/books/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or @bookSecurity.isOwner(#id)")
    @Override
    public RootEntity<DtoBook> deleteBook(@PathVariable(name = "id") Long id) {
        return ok(bookService.deleteBook(id));
    }

    @GetMapping(path = "/books/list/{id}")
    @Override
    public RootEntity<DtoBook> getBookById(@PathVariable(name = "id") Long id) {
        return ok(bookService.getBookById(id));
    }

    @GetMapping(path = "/books/list")
    @Override
    public RootEntity<RestPageableEntity<DtoBook>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size,
                                                               @RequestParam(defaultValue = "id") String sortField,
                                                               @RequestParam(defaultValue = "false") boolean asc) {
        Pageable pageable = toPageable(page, size, sortField, asc);
        Page<DtoBook> userPage = bookService.getAllBooks(pageable);

        return ok(toPageableEntity(userPage));
    }

    @GetMapping(path = "/books/list/by-user-id/{userId}")
    @Override
    public RootEntity<RestPageableEntity<DtoBook>> getBooksByUser(@PathVariable(name = "userId") Long userId,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size,
                                                                  @RequestParam(defaultValue = "id") String sortField,
                                                                  @RequestParam(defaultValue = "false") boolean asc) {
        Pageable pageable = toPageable(page,size,sortField,asc);
        Page<DtoBook> userPage = bookService.getBooksByUser(userId,pageable);

        return ok(toPageableEntity(userPage));
    }


}







