package com.todo.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.todo.dto.TodoDTO;
import com.todo.dto.TodoPriorityCountDTO;
import com.todo.model.Todo;
import com.todo.service.TodoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("todos")
@Api(value="todo", description="Todo operations(CRUD)")
public class TodoController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TodoService todoService;
	
	
	@ApiOperation(value = "Find a specific todo through Id", response = Todo.class)
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Todo.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@GetMapping("/{id}")
	public ResponseEntity<Todo> getTodoById(@PathVariable("id") Long id) {
	  Todo todo = todoService.getById(id);
		if (todo != null) { 
			logger.info("Todo with Id "+id+" found");
			return new ResponseEntity<Todo>(todo, HttpStatus.OK);
		}
		logger.warn("Todo with Id "+id+" NOT found");
		return new ResponseEntity<Todo>(HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "Find all todos(And filter)", response = Todo[].class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "id", value = "Todos ids", required = false, dataType = "string", paramType = "query"),
    	@ApiImplicitParam(name = "text", value = "Todos texts", required = false, dataType = "string", paramType = "query")
      }) 
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Todo[].class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@GetMapping()
	@ResponseBody
	public ResponseEntity<List<Todo>> getAllTodos(TodoDTO dto) {
		logger.info("Getting todos throught SearchDTO: "+dto);
		List<Todo> list = todoService.getAll(dto); 
		return new ResponseEntity<List<Todo>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Insert a new todo")
	@ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@PostMapping()
	public ResponseEntity<Todo> addTodo(@RequestBody Todo todo, UriComponentsBuilder builder) {
		Todo newTodo = todoService.add(todo);
        if ( newTodo == null ) {
        	logger.error("Todo not inserted");
        	return new ResponseEntity<Todo>(HttpStatus.CONFLICT);
        }
        logger.info("Todo inserted succesfully");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/todos/{id}").buildAndExpand(todo.getId()).toUri());
        return new ResponseEntity<Todo>(newTodo, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Update an existing todo through Id")
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Todo.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@PutMapping("/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable("id") Long id, @RequestBody Todo todo) {
		todo.setId(id);
		todo = todoService.update(todo);
		if ( todo != null ) {
			logger.info("Todo edited successfully:"+todo);
			return new ResponseEntity<Todo>(todo, HttpStatus.OK);
		}
		logger.warn("Todo with id not found");
		return new ResponseEntity<Todo>(HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "Delete an existing todo through Id")
	@ApiResponses(value = { 
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) {
		logger.info("Deleting todo with id '"+id+"'");
		boolean todoDeleted = todoService.delete(id);
		if ( todoDeleted ) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "Find todos through priorities and count all records for each priority.", response = TodoPriorityCountDTO[].class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "priorities", value = "Todos priorities", required = false, dataType = "int", paramType = "query")
    }) 
  @ApiResponses(value = { 
          @ApiResponse(code = 200, message = "Success", response = TodoPriorityCountDTO[].class),
          @ApiResponse(code = 400, message = "Bad Request"),
          @ApiResponse(code = 401, message = "Unauthorized"),
          @ApiResponse(code = 403, message = "Forbidden"),
          @ApiResponse(code = 409, message = "Conflict"),
          @ApiResponse(code = 500, message = "Failure")})
	@GetMapping("/priorities")
  @ResponseBody
  public ResponseEntity<List<TodoPriorityCountDTO>> getPriorities(Integer[] priorities) {
    logger.info("Getting priorities, search through following priorities:"+priorities);
    List<TodoPriorityCountDTO> list = todoService.getPrioritiesCounts(priorities); 
    return new ResponseEntity<List<TodoPriorityCountDTO>>(list, HttpStatus.OK);
  }
	
} 