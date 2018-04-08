package com.lvds.api.resource.rest.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me")
public class MeController {

	@GetMapping
	@ResponseBody
	public Principal getPrincipal(final Principal user) {
		return user;
	}
}
