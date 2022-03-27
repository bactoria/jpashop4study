package com.example.jpashop

import com.example.jpashop.domain.Member
import com.example.jpashop.domain.Order
import com.example.jpashop.domain.OrderItem
import com.example.jpashop.domain.delivery.Address
import com.example.jpashop.domain.delivery.Delivery
import com.example.jpashop.domain.delivery.DeliveryStatus
import com.example.jpashop.domain.item.Book
import com.example.jpashop.domain.item.Item
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@Component
class initDb(private val initService: InitService) {

	@PostConstruct
	fun init() {
		initService.dbInit1()
		initService.dbInit2()
	}

	companion object {
		@Component
		@Transactional
		class InitService(private val em: EntityManager) {
			fun dbInit1() {
				val member = Member(name = "user1", address = Address("", "", ""))
				em.persist(member)

				val book1 = createBook("author", "cc-01", 10, 1000)
				em.persist(book1)

				val book2 = createBook("author", "cc-02", 123, 100)
				em.persist(book2)

				val orderItem1 = OrderItem.createOrderItem(item = book1, orderPrice = 2000, count = 2)
				val orderItem2 = OrderItem.createOrderItem(item = book2, orderPrice = 100, count = 1)

				val delivery = Delivery(address = member.address, status = DeliveryStatus.READY)

				val order = Order.createOrder(member = member, delivery = delivery, orderItem1, orderItem2)
				em.persist(order)
			}

			fun dbInit2() {
				val member = Member(name = "user2", address = Address("", "", ""))
				em.persist(member)

				val book3 = createBook("author", "cc-03", 10, 1000)
				em.persist(book3)

				val book4 = createBook("author", "cc-04", 123, 100)
				em.persist(book4)

				val orderItem1 = OrderItem.createOrderItem(item = book3, orderPrice = 200, count = 5)
				val orderItem2 = OrderItem.createOrderItem(item = book4, orderPrice = 500, count = 5)

				val delivery = Delivery(address = member.address, status = DeliveryStatus.READY)

				val order = Order.createOrder(member = member, delivery = delivery, orderItem1, orderItem2)
				em.persist(order)
			}

			private fun createBook(author: String, isbn: String, stockQuantity: Int, price: Int): Item {
				val book = Book(author, isbn)
				book.stockQuantity = stockQuantity
				book.price = price
				return book
			}
		}
	}

}
