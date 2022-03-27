package com.example.jpashop.repository.order.simplequery

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class OrderSimpleQueryRepository(
	@PersistenceContext private val em: EntityManager
) {
	fun findOrderDtos(): List<OrderSimpleQueryDto> {
		return em.createQuery(
			"select new com.example.jpashop.repository.order.simplequery.OrderSimpleQueryDto( o.id, m.name, o.orderDate, o.status, d.address )" +
					" from Order o" +
					" join o.member m" +
					" join o.delivery d", OrderSimpleQueryDto::class.java
		).resultList
	}
}
