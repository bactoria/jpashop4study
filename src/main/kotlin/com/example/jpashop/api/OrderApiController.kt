package com.example.jpashop.api

import com.example.jpashop.repository.order.OrderRepository
import com.example.jpashop.repository.order.SimpleOrderDto
import com.example.jpashop.repository.order.simplequery.OrderSimpleQueryDto
import com.example.jpashop.repository.order.simplequery.OrderSimpleQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApiController(
	private val orderSimpleQueryRepository: OrderSimpleQueryRepository,
	private val orderRepository: OrderRepository,
) {
	/**
	 * V3: DB에서 엔티티로 조회 후, dto로 변환하여 응답
	 */
	@GetMapping("/api/v3/simple-orders")
	fun ordersV3(): List<SimpleOrderDto> {
		return orderRepository.findAllWithMemberDelivery()
			.map { SimpleOrderDto.fromEntity(it) }
	}

	/**
	 * V4: DB에서 dto로 조회하여 바로 응답
	 */
	@GetMapping("/api/v4/simple-orders")
	fun ordersV4(): List<OrderSimpleQueryDto> {
		return orderSimpleQueryRepository.findOrderDtos()
	}
}
