package com.hybridit.HybridLibrary.repository;

import com.hybridit.HybridLibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.ElementCollection;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
