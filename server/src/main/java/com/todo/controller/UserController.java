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

import com.todo.dto.UserSearchDTO;
import com.todo.model.User;
import com.todo.service.ICrudService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("users")
@Api(value="userCrud", description="User operations(CRUD)")
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ICrudService<User> userService;
	
	
	@ApiOperation(value = "Find a specific user through Id", response = User.class)
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = User.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		User user = userService.getById(id);
		if (user != null) { 
			logger.info("User with Id "+id+" found");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		logger.warn("User with Id "+id+" NOT found");
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "Find all users(And filter)", response = User[].class)
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "id", value = "Users ids", required = false, dataType = "string", paramType = "query"),
    	@ApiImplicitParam(name = "name", value = "Users names", required = false, dataType = "string", paramType = "query"),
        @ApiImplicitParam(name = "role", value = "Users roles", required = false, dataType = "string", paramType = "query")
      }) 
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = User.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@GetMapping()
	@ResponseBody
	public ResponseEntity<List<User>> getAllUsers(UserSearchDTO dto) {
		logger.info("Getting users throught SearchDTO: "+dto);
		List<User> list = userService.getAll(dto); 
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Insert a new user")
	@ApiResponses(value = { 
            @ApiResponse(code = 201, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@PostMapping()
	public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder builder) {
		User newUser = userService.add(user);
        if (newUser == null) {
        	logger.error("User not inserted");
        	return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }
        logger.info("User inserted succesfully");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Update an existing user through Id")
	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = User.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		user.setId(id);
		user = userService.update(user);
		if ( user != null ) {
			logger.info("User edited successfully:"+user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		logger.warn("User with id not found");
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "Delete an existing user through Id")
	@ApiResponses(value = { 
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Failure")})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
		logger.info("Deleting user with id '"+id+"'");
		boolean userDeleted = userService.delete(id);
		if ( userDeleted ) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
} 