package com.example.jpashop.controller

import com.example.jpashop.utils.logger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {
	
	@GetMapping("/")
	fun home(): String {
		logger().info("home controller")
		return "home"
	}
}
