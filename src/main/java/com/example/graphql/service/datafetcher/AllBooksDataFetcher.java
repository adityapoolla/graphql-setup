package com.example.graphql.service.datafetcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.graphql.model.Book;
import com.example.graphql.repository.BookRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book>>{

	@Autowired
	BookRepository bookRepository;
	
	@Override
	public List<Book> get(DataFetchingEnvironment environment) {
		return bookRepository.findAll();
	}

}
