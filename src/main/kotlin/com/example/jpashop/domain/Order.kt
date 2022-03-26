package com.example.jpashop.domain

import com.example.jpashop.domain.delivery.Delivery
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	var id: Long? = null,

	@ManyToOne @JoinColumn(name = "member_id")
	var member: Member? = null,

	@OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
	val orderItems: MutableList<OrderItem> = mutableListOf(),

	@OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
	@JoinColumn(name = "delivery_id")
	var delivery: Delivery? = null,

	var orderDate: LocalDateTime? = null,

	@Enumerated(EnumType.STRING)
	var status: OrderStatus? = null
) {

	fun changeMember(member: Member) {
		this.member = member
		member.orders.add(this)
	}

	fun addOrderItem(orderItem: OrderItem) {
		orderItems.add(orderItem)
		orderItem.order = this
	}

	fun changeDelivery(delivery: Delivery) {
		this.delivery = delivery
		delivery.order = this
	}

	/**
	 * 주문 취소
	 */
	fun cancel() {
		if (delivery!!.isComp()) { // FIXME: DEFAULT CONSTRUCTOR by noArg
			throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
		}

		this.status = OrderStatus.CANCEL
		for (orderItem in orderItems) {
			orderItem.cancel()
		}
	}

	/**
	 * 전체 주문 가격 조회
	 */
	fun getTotalPrice(): Int {
		return orderItems.sumOf { it.getTotalPrice() }
	}

	companion object {
		fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {
			val order = Order() // FIXME: DEFAULT CONSTRUCTOR by noArg
			order.changeMember(member)
			order.changeDelivery(delivery)
			for (orderItem in orderItems) {
				order.addOrderItem(orderItem)
			}
			order.status = OrderStatus.ORDER
			order.orderDate = LocalDateTime.now()
			return order
		}
	}
}
