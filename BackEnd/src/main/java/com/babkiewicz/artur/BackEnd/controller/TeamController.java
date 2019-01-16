package com.babkiewicz.artur.BackEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.babkiewicz.artur.BackEnd.domain.Response;
import com.babkiewicz.artur.BackEnd.model.Team;
import com.babkiewicz.artur.BackEnd.model.User;
import com.babkiewicz.artur.BackEnd.service.TeamService;

@RestController
public class TeamController {
	@Autowired
	private TeamService teamService;
	
	@PostMapping(value="/saveTeam")
	public ResponseEntity<Response> registration(@RequestBody Team team){
		teamService.save(team);
	    return new ResponseEntity<Response>(new Response("Team is saved successfully"), HttpStatus.OK);
	}
	
	@GetMapping(value="/teams")
	//@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Team>> getAllTeams(){
		List<Team> teams = teamService.findAll();
		return new ResponseEntity<List<Team>>(teams,HttpStatus.OK);
	}
	
	@GetMapping("/getTeam/{id}")
	public ResponseEntity<Team> getTeam(@PathVariable("id") long id){
		Team team = teamService.findById(id);
		return new ResponseEntity<Team>(team,HttpStatus.OK);
	}
	
	@GetMapping("/getPlayers/{id}")
	public ResponseEntity<List<User>> getPlayers(@PathVariable("id") long id){
		Team team = teamService.findById(id);
		List<User> players = team.getPlayers();
		return new ResponseEntity<List<User>>(players,HttpStatus.OK);
	}
	
	@PostMapping("/addPlayer/{id}")
	public ResponseEntity<Response> addPlayer(@PathVariable("id") long id, @RequestBody User user){
		Team team = teamService.findById(id);
		team.addPlayer(user);
		return new ResponseEntity<Response>(new Response("Player added successfully"), HttpStatus.OK);
	}
	
	@PostMapping("/removePlayer/{id}")
	public ResponseEntity<Response> removePlayer(@PathVariable("id") long id, @RequestBody User user){
		Team team = teamService.findById(id);
		team.removePlayer(user);
		return new ResponseEntity<Response>(new Response("Player removed successfully"), HttpStatus.OK);
	}
}
