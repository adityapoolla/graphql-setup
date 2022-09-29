package com.example.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.graphql.model.Book;

public interface BookRepository extends JpaRepository<Book, String>{

}
