package com.example.jpashop.service

import com.example.jpashop.domain.Order
import com.example.jpashop.domain.OrderItem
import com.example.jpashop.domain.delivery.Delivery
import com.example.jpashop.domain.delivery.DeliveryStatus
import com.example.jpashop.repository.ItemRepository
import com.example.jpashop.repository.MemberRepository
import com.example.jpashop.repository.OrderRepository
import com.example.jpashop.repository.OrderSearch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService(
	private val orderRepository: OrderRepository,
	private val memberRepository: MemberRepository,
	private val itemRepository: ItemRepository
) {

	/**
	 * 주문
	 */
	@Transactional
	fun order(memberId: Long, itemId: Long, count: Int): Long {
		// 엔티티 조회
		val member = memberRepository.findOne(memberId)
		val item = itemRepository.findOne(itemId)

		// 배송정보 생성
		val delivery = Delivery(
			address = member.address!!,
			status = DeliveryStatus.READY
		)

		// 주문상품 생성
		val orderItem = OrderItem.createOrderItem(item, item.price!!, count)

		// 주문 생성
		val order = Order.createOrder(member, delivery, orderItem)

		// 주문 저장
		orderRepository.save(order)

		return order.id!!
	}

	/**
	 * 주문 취소
	 */
	@Transactional
	fun cancelOrder(orderId: Long) {
		// 주문 엔티티 조회
		val order = orderRepository.findOne(orderId)

		// 주문 취소
		order.cancel()
	}

	fun findOrders(orderSearch: OrderSearch): List<Order> {
		return orderRepository.findAllByCriteria(orderSearch)
	}
}
