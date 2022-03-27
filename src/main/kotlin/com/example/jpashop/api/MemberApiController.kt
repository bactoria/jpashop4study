package com.example.jpashop.api

import com.example.jpashop.domain.Member
import com.example.jpashop.domain.delivery.Address
import com.example.jpashop.service.MemberService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v2")
class MemberApiController(
	private val memberService: MemberService
) {
	@PostMapping("/members")
	fun saveMemberV2(@RequestBody @Valid request: CreateMemberRequest): CreateMemberResponse {
		val member = Member(
			name = request.name,
			address = Address(
				city = request.city,
				street = request.street,
				zipcode = request.zipcode
			)
		)
		val id = memberService.join(member)
		return CreateMemberResponse(id)
	}

	@PutMapping("/members/{id}")
	fun updateMemberV2(
		@PathVariable("id") id: Long,
		@RequestBody @Valid request: UpdateMemberRequest
	): UpdateMemberResponse {
		memberService.update(id, request.name)
		val findMember = memberService.findMember(id)
		return UpdateMemberResponse(
			id = findMember.id!!,
			name = findMember.name
		)
	}

	@GetMapping("/members")
	fun membersV2(): Result<List<MemberDto>> {
		val collect = memberService.findMembers()
			.map { MemberDto(it.name) }
			.toList()
		return Result(collect.size, collect)
	}


}

class Result<T>(
	val count: Int,
	val data: T
)

data class MemberDto(
	val name: String
)

data class UpdateMemberRequest(
	val name: String,
	val city: String,
	val street: String,
	val zipcode: String
)

data class UpdateMemberResponse(
	val id: Long,
	val name: String
)

data class CreateMemberRequest(
	val name: String,
	val city: String,
	val street: String,
	val zipcode: String
)

data class CreateMemberResponse(
	val id: Long
)
