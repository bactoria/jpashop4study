package com.example.jpashop.repository.order

import com.example.jpashop.domain.Order
import com.example.jpashop.domain.OrderStatus
import com.example.jpashop.domain.delivery.Address
import java.time.LocalDateTime

data class SimpleOrderDto(
	val orderId: Long,
	val name: String,
	val orderDate: LocalDateTime,
	val orderStatus: OrderStatus,
	val address: Address
) {
	companion object {
		fun fromEntity(order: Order): SimpleOrderDto {
			return SimpleOrderDto(
				orderId = order.id!!,
				name = order.member!!.name,
				orderDate = order.orderDate!!,
				orderStatus = order.status!!,
				address = order.delivery!!.address
			)
		}
	}
}
