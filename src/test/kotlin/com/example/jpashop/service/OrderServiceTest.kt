package com.example.jpashop.service

import com.example.jpashop.domain.Member
import com.example.jpashop.domain.OrderStatus
import com.example.jpashop.domain.delivery.Address
import com.example.jpashop.domain.item.Book
import com.example.jpashop.domain.item.Item
import com.example.jpashop.exception.NotEnoughStockException
import com.example.jpashop.repository.order.OrderRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class OrderServiceTest @Autowired constructor(
	private val em: EntityManager,
	private val orderService: OrderService,
	private val orderRepository: OrderRepository
) {
	@Test
	fun `상품주문`() {
		// given
		val member = createMember(name = "bactoria")
		val book = createBook(name = "시골 JPA", price = 10000, stockQuantity = 10)

		val orderCount = 2

		// when
		val orderId = orderService.order(member.id!!, book.id!!, orderCount)

		// then
		val findOrder = orderRepository.findOne(orderId)
		assertThat(findOrder.status).isEqualTo(OrderStatus.ORDER)
		assertThat(findOrder.orderItems.size).isEqualTo(1)
		assertThat(findOrder.getTotalPrice()).isEqualTo(10000 * orderCount)
		assertThat(book.stockQuantity).isEqualTo(8)
	}


	@Test
	fun `주문 시 상품 재고수량을 초과한 경우 예외 발생`() {
		// given
		val member = createMember(name = "bactoria")
		val book = createBook(name = "시골 JPA", price = 10000, stockQuantity = 10)

		val orderCount = 11

		// when
		assertThatThrownBy { orderService.order(member.id!!, book.id!!, orderCount) }
			.isInstanceOf(NotEnoughStockException::class.java)
	}

	@Test
	fun `주문 취소`() {
		// given
		val member = createMember(name = "bactoria")
		val book = createBook(name = "시골 JPA", price = 10000, stockQuantity = 10)

		val orderCount = 2

		val orderId = orderService.order(member.id!!, book.id!!, orderCount)
			.also { require(book.stockQuantity == 8) }

		// when
		orderService.cancelOrder(orderId)

		// then
		val findOrder = orderRepository.findOne(orderId)

		assertThat(findOrder.status).isEqualTo(OrderStatus.CANCEL)
		assertThat(book.stockQuantity).isEqualTo(10)
	}

	private fun createBook(name: String, price: Int, stockQuantity: Int): Item {
		val book = Book(
			author = "시골 JPA",
			isbn = "a"
		)
		book.name = name
		book.price = price
		book.stockQuantity = stockQuantity
		em.persist(book)
		return book
	}

	private fun createMember(name: String): Member {
		val member = Member(
			name = name,
			address = Address("부산", "해운대구", "마린시티")
		)
		em.persist(member)
		return member
	}
}
