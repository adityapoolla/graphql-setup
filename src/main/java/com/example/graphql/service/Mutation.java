package com.example.graphql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphql.model.Book;
import com.example.graphql.repository.BookRepository;

@Component
public class Mutation implements GraphQLMutationResolver {
    
    @Autowired
    private BookRepository bookRepository;
    
    public Mutation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public Book createLink(String isn, String title) {
        Book book = new Book(isn, title, "", new String[] {}, "");
        bookRepository.save(book);
        return book;
    }
}