package com.example.jpashop.repository

import com.example.jpashop.domain.OrderStatus

data class OrderSearch(
	var memberName: String? = null,
	var orderStatus: OrderStatus? = null
)
