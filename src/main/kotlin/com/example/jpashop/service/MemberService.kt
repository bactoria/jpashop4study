package com.example.jpashop.service

import com.example.jpashop.domain.Member
import com.example.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
	private val memberRepository: MemberRepository
) {
	/**
	 * 회원가입
	 */
	@Transactional
	fun join(member: Member): Long {
		validateDuplicatedMember(member)
		memberRepository.save(member)
		return member.id!!
	}

	private fun validateDuplicatedMember(member: Member) {
		val findMembers = memberRepository.findByName(member.name)
		if (findMembers.isNotEmpty()) {
			throw IllegalStateException("이미 존재하는 회원입니다.")
		}
	}

	/**
	 * 회원 전체조회
	 */
	fun findMembers(): List<Member> {
		return memberRepository.findAll()
	}

	/**
	 * 회원 단건조회
	 */
	fun findMember(id: Long): Member {
		return memberRepository.findOne(id)
	}
}
