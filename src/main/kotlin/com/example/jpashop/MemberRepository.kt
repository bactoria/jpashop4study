package com.example.jpashop

import com.example.jpashop.domain.Member
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberRepository(
	@PersistenceContext private val em: EntityManager
) {
	fun save(member: Member): Long {
		em.persist(member)
		return member.id!!
	}

	fun find(id: Long): Member {
		return em.find(Member::class.java, id)!!
	}
}
