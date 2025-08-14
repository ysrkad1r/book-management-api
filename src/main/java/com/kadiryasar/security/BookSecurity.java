package com.kadiryasar.security;

import com.kadiryasar.model.Book;
import com.kadiryasar.model.User;
import com.kadiryasar.repository.BookRepository;
import com.kadiryasar.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("bookSecurity")
@RequiredArgsConstructor
public class BookSecurity {

    private final BookRepository bookRepository;
    private final SecurityUtil securityUtil;

    public boolean isOwner(Long id){
        String username = securityUtil.getCurrentUsername();
        if (username == null){
            return false;
        }

        //buradaki mapler aslinda bookRepository.findById(id) methodundan donen Optional nesnesi uzerinden
        //diger parametrelere ulasip bir kontrolden gecirip sonucu dondurmenin yazimi daha kisa ve okunakli hali
        return bookRepository.findById(id)
                .map(Book::getCreatedBy)
                .map(User::getId)
                .map(ownerId -> ownerId.equals(securityUtil.getCurrentUserId()))
                .orElse(false);
    }


}
