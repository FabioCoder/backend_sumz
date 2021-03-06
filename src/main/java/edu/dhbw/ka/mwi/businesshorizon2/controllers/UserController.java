package edu.dhbw.ka.mwi.businesshorizon2.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.dhbw.ka.mwi.businesshorizon2.businesslogic.services.UserService;
import edu.dhbw.ka.mwi.businesshorizon2.config.AdditionalWebConfig;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.AppUserDto;
import edu.dhbw.ka.mwi.businesshorizon2.models.dtos.UserPutRequestDto;

import java.util.Base64;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private AdditionalWebConfig webConfig;

	/**
	 * Adds a new User
	 * @param userDto
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addUser(@RequestBody @Valid AppUserDto userDto, HttpServletRequest request) throws Exception{
		String host = request.getRequestURL().toString();
		userService.addUser(userDto, host);
	}

	/**
	 * Activates a User. An activationtoken is needed
	 * @param token
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/activate/{token}", method = RequestMethod.GET)
	public void activateUser(@PathVariable("token") String token, HttpServletRequest request, HttpServletResponse response) throws Exception{
		userService.activateUser(token);
		
		String redirectURL = "http://" + webConfig.getClientHost();
		redirectURL = redirectURL + "/login?useractivated";
		
		response.sendRedirect(redirectURL);
		
	}

	/**
	 * Starts the process of password reset. This method creates a token and sends it via email to the user.
	 * @param user
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/forgot", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void passwordForgot(@RequestBody @Valid AppUserDto user, HttpServletRequest request) throws Exception {
		String redirectURL = request.getRequestURL().toString();
		redirectURL = redirectURL.replaceAll(request.getRequestURI(), "");
		userService.requestUserPasswordReset(user.getEmail(), redirectURL);
	}

	/**
	 * Checks if the given Token is valid
	 * @param token
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/forgot/{token}", method = RequestMethod.GET)
	public void checkPasswordResetToken(@PathVariable("token") String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String redirectURL = "http://" + webConfig.getClientHost();
		
		redirectURL = redirectURL + "/users/reset/" + token;
		userService.checkPasswordResetToken(token);
		
		response.sendRedirect(redirectURL);
	}

	/**
	 * Resets the passowrd for the given user
	 * @param token
	 * @param userDto
	 * @throws Exception
	 */
	@RequestMapping(value = "/reset/{token}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void resetPassword(@PathVariable("token") String token, @RequestBody @Valid AppUserDto userDto) throws Exception {
		
		userService.resetUserPassword(userDto, token);
	}
	
	@RequestMapping(value = "/reset/{token}", method = RequestMethod.GET)
	public void resetPassword() {
		
	}

	/**
	 * Deletes an user
	 * @param user
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public void deleteUser(@RequestBody @Valid AppUserDto user, @PathVariable Long id) throws Exception {
		userService.deleteUser(user, id);
	}

	/**
	 * Change User Information
	 * @param user
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public void updateUser(@RequestBody UserPutRequestDto user, @PathVariable Long id) throws Exception {

		userService.updateUserPassword(user, id);
	}
}
