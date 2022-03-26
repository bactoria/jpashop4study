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
	var order: Order? = null,

	var orderPrice: Int,

	var count: Int
) {
	fun cancel() {
		item.addStock(count)
	}

	fun getTotalPrice(): Int {
		return orderPrice * count
	}

	companion object {
		fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
			val orderItem = OrderItem(
				item = item,
				orderPrice = orderPrice,
				count = count
			)

			item.removeStock(count)
			return orderItem
		}
	}
}
