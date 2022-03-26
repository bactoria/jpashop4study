package com.example.jpashop.repository

import com.example.jpashop.domain.Order
import com.example.jpashop.domain.OrderStatus
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.persistence.criteria.*

@Repository
class OrderRepository(
	@PersistenceContext private val em: EntityManager
) {
	fun save(order: Order) {
		em.persist(order)
	}

	fun findOne(id: Long): Order {
		return em.find(Order::class.java, id)
	}

	fun findAllByCriteria(orderSearch: OrderSearch): List<Order> {
		val cb: CriteriaBuilder = em.criteriaBuilder
		val cq = cb.createQuery(Order::class.java)
		val o: Root<Order> = cq.from(Order::class.java)
		val m: Join<Any, Any> = o.join("member", JoinType.INNER)

		val criteria = mutableListOf<Predicate>()

		if (orderSearch.orderStatus != null) {
			val status = cb.equal(o.get<OrderStatus>("status"), orderSearch.orderStatus)
			criteria.add(status)
		}

		if (StringUtils.hasText(orderSearch.memberName)) {
			val name = cb.like(m["name"], "%" + orderSearch.memberName + "%")
			criteria.add(name)
		}

		cq.where(cb.and(*criteria.toTypedArray()))
		val query: TypedQuery<Order> = em.createQuery(cq).setMaxResults(10000)
		return query.resultList
	}
}
