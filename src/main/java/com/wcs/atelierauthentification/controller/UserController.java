package com.wcs.atelierauthentification.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wcs.atelierauthentification.dto.UserDto;
import com.wcs.atelierauthentification.model.ERole;
import com.wcs.atelierauthentification.model.Role;
import com.wcs.atelierauthentification.model.User;
import com.wcs.atelierauthentification.repository.RoleRepository;
import com.wcs.atelierauthentification.repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public void create(@Valid @RequestBody(required = true) UserDto userDto) {
		//on vérifie si le username et l'email n'existe pas déjà
		if(userRepository.existsByUsername(userDto.getUsername()) ||
				userRepository.existsByEmail(userDto.getEmail())){
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		
		Role role = roleRepository.findByAuthority(ERole.ROLE_USER.name())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword())); //encodage via le passwordEncoder du WebSecurityConfig
		
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setAuthorities(roles);
		
		userRepository.save(user);
		
	}
}
