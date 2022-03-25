package com.example.jpashop

import com.example.jpashop.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class MemberRepositoryTest(
	@Autowired private val memberRepository: MemberRepository
) {

	@Test
	@Transactional
	fun testMember() {
		// given
		val member = Member()
		member.username = "memberA"

		// when
		val saveId = memberRepository.save(member)
		val findMember = memberRepository.find(saveId)

		// then
		assertThat(findMember.id).isEqualTo(member.id)
		assertThat(findMember.username).isEqualTo(member.username)
		assertThat(findMember).isSameAs(member)
	}
	
}
