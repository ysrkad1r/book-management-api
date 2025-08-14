package com.kadiryasar.service.Impl;

import com.kadiryasar.dto.DtoBook;
import com.kadiryasar.dto.DtoBookIU;
import com.kadiryasar.dto.DtoBookUpdateIU;
import com.kadiryasar.dto.DtoUser;
import com.kadiryasar.exception.BaseException;
import com.kadiryasar.exception.ErrorMessage;
import com.kadiryasar.exception.MessageType;
import com.kadiryasar.model.Book;
import com.kadiryasar.model.User;
import com.kadiryasar.repository.BookRepository;
import com.kadiryasar.repository.UserRepository;
import com.kadiryasar.service.IBookService;
import com.kadiryasar.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //Autowired alternatifi daha sektorel
@Transactional //dbden yazma yaptigimiz icin
public class BookServiceImpl implements IBookService {

    //@RequiredArgsConstructor ile bu sekile kullanmak daha sektorel
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;


    public DtoBook toDto(Book book){
        DtoBook dtoBook = new DtoBook();
        DtoUser dtoUser = new DtoUser();

        BeanUtils.copyProperties(book.getCreatedBy(),dtoUser);

        dtoUser.setRoleType(book.getCreatedBy().getRoleType());
        dtoBook.setCreateTime(book.getCreateTime());

        BeanUtils.copyProperties(book,dtoBook);

        dtoBook.setCreatedBy(dtoUser);

        return dtoBook;
    }

    private User getCurrentUser(){
        return userRepository.findByUsername(securityUtil.getCurrentUsername()).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.USER_NOT_FOUND,"")
        ));
    }

    private Book createBook(DtoBookIU dtoBookIU){
        Book book = new Book();
        book.setCreateTime(new Date());
        BeanUtils.copyProperties(dtoBookIU,book);

        User user = userRepository.findById(dtoBookIU.getUserId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USERNAME_NOT_FOUND, dtoBookIU.getUserId().toString())
                ));

        book.setCreatedBy(user);
        return book;
    }

    @Override
    public DtoBook saveBook(DtoBookIU dtoBookIU) {
        DtoBook dtoBook = new DtoBook();
        DtoUser dtoUser = new DtoUser();

        //Kitabi dbye kaydettik
        Book savedBook = bookRepository.save(createBook(dtoBookIU));

        //geri donecegimiz DtoBook nesnesinin primitive fieldlerini otomatik BeanUtils ile kopyaliyoruz
        BeanUtils.copyProperties(savedBook,dtoBook);

        //DtoBook icerisindeki DtoUser fieldi icin DtoUser nesnesinin icini dolduruyoruz
        BeanUtils.copyProperties(savedBook.getCreatedBy(),dtoUser);
        dtoUser.setRoleType(savedBook.getCreatedBy().getRoleType());

        //DtoBook icine DtoUser nesnesini set edip DtoBook nesnesini hazir hale geririyoruz
        dtoBook.setCreatedBy(dtoUser);

        return dtoBook;
    }

    @Override
    public DtoBook updateBook(Long bookId, DtoBookUpdateIU dtoBookUpdateIU) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.BOOK_NOT_FOUND,bookId.toString())
        ));

        User currentUser = getCurrentUser();
        boolean isOwner = (book.getCreatedBy() != null && book.getCreatedBy().getId().equals(currentUser.getId()));

        if (!isOwner){
            throw new BaseException(new ErrorMessage(MessageType.ONLY_OWNERS_AND_ADMINS_CAN_MAKE_ARRENGEMENTS,"You are not owner or admin!"));
        }

        if (!(dtoBookUpdateIU.getTitle() == null)){
            book.setTitle(dtoBookUpdateIU.getTitle());
        }
        if (!(dtoBookUpdateIU.getAuthor() == null)){
            book.setAuthor(dtoBookUpdateIU.getAuthor());
        }
        if (!(dtoBookUpdateIU.getDescription() == null)){
            book.setDescription(dtoBookUpdateIU.getDescription());
        }

        Book savedBook = bookRepository.save(book);

        DtoUser dtoUser = new DtoUser();
        BeanUtils.copyProperties(savedBook.getCreatedBy(),dtoUser);
        dtoUser.setRoleType(savedBook.getCreatedBy().getRoleType());

        DtoBook dtoBook = new DtoBook();
        BeanUtils.copyProperties(book,dtoBook);
        dtoBook.setCreatedBy(dtoUser);

        return dtoBook;
    }

    @Override
    public DtoBook deleteBook(Long id) {
        User currentUser = getCurrentUser();
        Book book = bookRepository.findById(id).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.BOOK_NOT_FOUND, id.toString())
        ));

        boolean isAdmin = (currentUser.getRoleType() != null && currentUser.getRoleType().name().equals("ADMIN") );
        boolean isOwner = (book.getCreatedBy() != null && book.getCreatedBy().getId().equals(currentUser.getId()));

        if (!(isAdmin || isOwner)){
            throw new BaseException(new ErrorMessage(MessageType.ONLY_OWNERS_AND_ADMINS_CAN_MAKE_ARRENGEMENTS, currentUser.toString()));
        }

        DtoUser dtouser = new DtoUser();
        BeanUtils.copyProperties(currentUser,dtouser);
        dtouser.setRoleType(currentUser.getRoleType());

        DtoBook dtoBook = new DtoBook();
        BeanUtils.copyProperties(book,dtoBook);
        dtoBook.setCreatedBy(dtouser);

        bookRepository.deleteById(id);

        return dtoBook;
    }

    @Override
    public DtoBook getBookById(Long bookId) {
        DtoBook dtoBook = new DtoBook();
        DtoUser dtoUser = new DtoUser();

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.BOOK_NOT_FOUND,bookId.toString())
        ));

        BeanUtils.copyProperties(book.getCreatedBy(),dtoUser);
        dtoUser.setRoleType(book.getCreatedBy().getRoleType());

        BeanUtils.copyProperties(book,dtoBook);
        dtoBook.setCreatedBy(dtoUser);

        return dtoBook;
    }

    @Override
    public Page<DtoBook> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public Page<DtoBook> getBooksByUser(Long userId, Pageable pageable) {
        return bookRepository.findByCreatedById(userId, pageable).map(this::toDto);
    }


}
