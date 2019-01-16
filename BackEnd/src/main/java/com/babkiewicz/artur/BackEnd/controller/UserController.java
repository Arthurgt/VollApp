package com.babkiewicz.artur.BackEnd.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.babkiewicz.artur.BackEnd.domain.Response;
import com.babkiewicz.artur.BackEnd.model.JoinRequest;
import com.babkiewicz.artur.BackEnd.model.User;
import com.babkiewicz.artur.BackEnd.service.JoinRequestService;
import com.babkiewicz.artur.BackEnd.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JoinRequestService requestService;
	
	@GetMapping(value="/users")
	//@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = userService.findAll();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	@GetMapping(value="/getUser")
	//@PreAuthorize("hasRole('USER')")
	public ResponseEntity<User> getUser(Principal principal){
		User user = userService.getUserByEmail(principal.getName());
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	@PostMapping(value="/updateUser")
	public ResponseEntity<Response> updateUser(@RequestBody User user){
		User dbUser = userService.update(user);
		return new ResponseEntity<Response>(new Response("User is updated successfully"), HttpStatus.OK);
	}
	@DeleteMapping(value="/deleteAllRequests/{id}")
	public ResponseEntity<Response> deleteAllUserRequests(@PathVariable("id") long user_id){
		List<JoinRequest> joinRequests = requestService.findAll();
		for(JoinRequest request : joinRequests) {
			if(request.getId().equals(user_id)) {
			System.out.println(request.getId());
			request.setEnabled(0);
			requestService.save(request);
			}
		}
		return new ResponseEntity<Response>(new Response("joinRequest has been updated successfully"), HttpStatus.OK);
	}
}
