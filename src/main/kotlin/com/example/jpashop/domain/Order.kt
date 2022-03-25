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
	var member: Member,

	@OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
	val orderItems: MutableList<OrderItem> = mutableListOf(),

	@OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
	@JoinColumn(name = "delivery_id")
	var delivery: Delivery,

	var orderDate: LocalDateTime,

	@Enumerated(EnumType.STRING)
	var status: OrderStatus
)
