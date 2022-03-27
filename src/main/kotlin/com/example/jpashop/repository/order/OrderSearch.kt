package com.example.jpashop.repository.order

import com.example.jpashop.domain.OrderStatus

data class OrderSearch(
	var memberName: String? = null,
	var orderStatus: OrderStatus? = null
)
