package com.example.jpashop.domain

import com.example.jpashop.domain.item.Item
import javax.persistence.*

@Entity
class OrderItem(
	@Id @GeneratedValue
	@Column(name = "order_item_id")
	var id: Long? = null,

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	var item: Item,

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	var order: Order,

	var orderPrice: Int,

	var count: Int
)
