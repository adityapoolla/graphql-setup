package com.example.graphql.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.graphql.service.GraphQLService;

import graphql.ExecutionResult;

@RequestMapping("/rest/books")
@RestController
public class BookResource {
	
	@Autowired
	GraphQLService graphqlService;

	@PostMapping
	public ResponseEntity<Object> getAllBooks(@RequestBody String query) {
		ExecutionResult result = graphqlService.getGraphQL().execute(query);
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
}
