package com.example.jpashop.controller

import com.example.jpashop.domain.Member
import com.example.jpashop.domain.delivery.Address
import com.example.jpashop.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.AbstractBindingResult
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class MemberController(
	private val memberService: MemberService
) {

	@GetMapping("/members/new")
	fun createForm(model: Model): String {
		model.addAttribute("memberForm", MemberForm())
		return "members/createMemberForm"
	}

	@PostMapping("/members/new")
	fun create(@Valid memberForm: MemberForm, result: BindingResult): String {

		if (result.hasErrors()) {
			return "members/createMemberForm"
		}
		
		val address = Address(
			city = memberForm.city!!,
			street = memberForm.street!!,
			zipcode = memberForm.zipcode!!
		)
	
		val member = Member(
			name = memberForm.name!!,
			address = address
		)

		memberService.join(member)

		return "redirect:/"
	}
	
	@GetMapping("members")
	fun list(model: Model): String {
		val members = memberService.findMembers()
		model.addAttribute("members", members)
		return "members/memberList"
	}
}
