package com.hybridit.HybridLibrary.repository;

import com.hybridit.HybridLibrary.model.Author;
import com.hybridit.HybridLibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
