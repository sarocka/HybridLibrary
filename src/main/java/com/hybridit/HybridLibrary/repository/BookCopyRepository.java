package com.hybridit.HybridLibrary.repository;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    List<BookCopy> findByBookId(Long id);

    BookCopy findFirstByBookIdAndDateOfBorrowingNull(Long id);

    BookCopy findByLibraryNum(String libraryNum);

    List<BookCopy>findByDateOfBorrowingNotNull();

}
