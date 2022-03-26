package com.example.jpashop.domain.delivery

import com.example.jpashop.domain.Order
import javax.persistence.*

@Entity
class Delivery(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	var id: Long? = null,

	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	var order: Order? = null,

	@Embedded
	var address: Address,

	@Enumerated(EnumType.STRING)
	var status: DeliveryStatus
) {
	fun isComp(): Boolean {
		return this.status === DeliveryStatus.COMP
	}
}
