package com.example.graphql.service;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.graphql.model.Book;
import com.example.graphql.repository.BookRepository;
import com.example.graphql.service.datafetcher.AllBooksDataFetcher;
import com.example.graphql.service.datafetcher.BookDataFetcher;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class GraphQLService {

	@Value("classpath:books.graphql")
	Resource resource;

	private GraphQL graphQL;

	@Autowired
	private AllBooksDataFetcher allBooksDataFetcher;

	@Autowired
	private BookDataFetcher bookDataFetcher;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private Mutation mutation;

	@PostConstruct
	public void loadSchema() throws IOException {
		loadDataIntoHsql();
		File schemaFile = resource.getFile();
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildRuntimeWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}

	private void loadDataIntoHsql() {
		Stream.of(
				new Book("111","Book 1","Aditya_1", new String[] {
						"abc",
						"def"
				},"Nov 17"),
				new Book("222","Book 2","Aditya_2", new String[] {
						"abc",
						"def"
				},"Nov 17"),
				new Book("333","Book 3","Aditya_3", new String[] {
						"abc",
						"def"
				},"Nov 17")
				).forEach(
						book -> 
						bookRepository.save(book)
						);
	}

	private RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", typeWiring -> 
				typeWiring
				.dataFetcher("allBooks", allBooksDataFetcher)
				.dataFetcher("book", bookDataFetcher))
				.build();
	}

	public GraphQL getGraphQL() {
		return graphQL;
	}
}
