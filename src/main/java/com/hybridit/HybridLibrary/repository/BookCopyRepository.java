package com.hybridit.HybridLibrary.repository;

import com.hybridit.HybridLibrary.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    BookCopy findFirstByBookIdAndDateOfBorrowingNull(Long id);

    BookCopy findByLibraryNum(String libraryNum);

    List<BookCopy> findByBookIdAndDateOfBorrowingNotNull(Long id);

    List<BookCopy> findByDateOfBorrowingNotNull();

    List<BookCopy> findByBookId(Long id);

}
