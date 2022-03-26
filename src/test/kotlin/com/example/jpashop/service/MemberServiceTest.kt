package com.example.jpashop.service

import com.example.jpashop.domain.Member
import com.example.jpashop.domain.delivery.Address
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberServiceTest @Autowired constructor(
	private val memberService: MemberService
) {

	@Test
	@Rollback(false)
	fun `회원가입`() {
		// given
		val member = Member(
			name = "book",
			address = Address("부산", "해운대구", "마린시티")
		)

		// when
		val savedId = memberService.join(member)

		// then
		assertThat(savedId).isEqualTo(member.id)
	}

	@Test
	fun `중복 회원 예외발생`() {
		// given
		val member1 = Member(
			name = "duplicatedName",
			address = Address("부산", "해운대구", "마린시티")
		)
		memberService.join(member1)

		val member2 = Member(
			name = "duplicatedName",
			address = Address("서울", "송파구", "시그니엘")
		)

		// when then
		assertThatThrownBy { memberService.join(member2) }
			.isInstanceOf(IllegalStateException::class.java)
	}
}
