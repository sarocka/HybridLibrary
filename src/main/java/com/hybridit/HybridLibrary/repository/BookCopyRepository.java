package com.hybridit.HybridLibrary.repository;

import com.hybridit.HybridLibrary.model.Book;
import com.hybridit.HybridLibrary.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
}
