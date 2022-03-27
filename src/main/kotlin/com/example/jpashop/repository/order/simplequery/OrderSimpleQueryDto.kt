package com.example.jpashop.repository.order.simplequery

import com.example.jpashop.domain.OrderStatus
import com.example.jpashop.domain.delivery.Address
import java.time.LocalDateTime

data class OrderSimpleQueryDto(
	val orderId: Long,
	val name: String,
	val orderDate: LocalDateTime,
	val orderStatus: OrderStatus,
	val address: Address
)
