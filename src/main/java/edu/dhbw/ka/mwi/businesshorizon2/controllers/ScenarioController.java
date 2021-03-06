package edu.dhbw.ka.mwi.businesshorizon2.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IScenarioService;
import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.interfaces.IUserService;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioPostRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioPutRequestDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.ScenarioResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/scenarios")
@Api(value="/scenario", description="Szenario um eine Unternehmensbewertung durchzuführen")
public class ScenarioController {
	
	@Autowired
	private IScenarioService scenarioService;
	
	@Autowired
	private IUserService userService;
	
	@ApiOperation(value = "Erzeugen eines Scenario Objekts", response = Long.class)
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public Long create(@RequestBody @Valid ScenarioPostRequestDto scenario) {
		
		System.out.println(scenario);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) auth.getPrincipal();
		
		Long appUserId = userService.getUserId(username);
		
		Long scenarioIdInDb = scenarioService.create(scenario, appUserId);
		
		return scenarioIdInDb;
	}
	
	@ApiOperation(value = "Ändern eines Scenario Objekts", response = Long.class,
			responseContainer = "ResponseEntity")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public ResponseEntity<Long> update(@PathVariable("id") Long scenarioId, @RequestBody @Valid ScenarioPutRequestDto scenario) {
		
		if(!scenarioId.equals(scenario.getId())) {
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) auth.getPrincipal();
		
		Long appUserId = userService.getUserId(username);
		
		Long scenarioIdInDb;
		
		try {
			scenarioIdInDb = scenarioService.update(scenario, appUserId);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(scenarioIdInDb, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Liefert alle Szenario Objekte des angemeldeten Users", 
				response = ScenarioResponseDto.class, responseContainer = "List")
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public List<ScenarioResponseDto> getAll () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) auth.getPrincipal();
		
		Long appUserId = userService.getUserId(username);
		List<ScenarioResponseDto> scenarios = scenarioService.getAll(appUserId);
		
		return scenarios;
	}
	
	@ApiOperation(value = "Liefert ein bestimmtes Szenario Objekt", response = ScenarioResponseDto.class,
			responseContainer = "ResponseEntity")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public ResponseEntity<ScenarioResponseDto> get (@PathVariable("id") Long scenarioId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) auth.getPrincipal();
		
		Long appUserId = userService.getUserId(username);
		
		ScenarioResponseDto scenario;
		
		try {
			scenario = scenarioService.get(scenarioId, appUserId);
		}catch(IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(AccessDeniedException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<ScenarioResponseDto>(scenario, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Löscht ein bestimmtes Scenario Objekt", response = ResponseEntity.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public ResponseEntity delete(@PathVariable("id") Long scenarioId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) auth.getPrincipal();
		
		Long appUserId = userService.getUserId(username);
		
		try {
			scenarioService.delete(scenarioId, appUserId);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch(AccessDeniedException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
