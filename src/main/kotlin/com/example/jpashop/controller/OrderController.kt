package com.example.jpashop.controller

import com.example.jpashop.repository.OrderSearch
import com.example.jpashop.service.ItemService
import com.example.jpashop.service.MemberService
import com.example.jpashop.service.OrderService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class OrderController(
	private val orderService: OrderService,
	private val memberService: MemberService,
	private val itemService: ItemService
) {

	@GetMapping("/order")
	fun createForm(model: Model): String {
		val members = memberService.findMembers();
		val items = itemService.findItems();

		model.addAttribute("members", members);
		model.addAttribute("items", items);

		return "order/orderForm"
	}

	@PostMapping("/order")
	fun order(
		@RequestParam("memberId") memberId: Long,
		@RequestParam("itemId") itemId: Long,
		@RequestParam("count") count: Int
	): String {
		orderService.order(memberId, itemId, count)
		return "redirect:/orders"
	}

	@GetMapping("/orders")
	fun orders(@ModelAttribute("orderSearch") orderSearch: OrderSearch, model: Model): String {
		val orders = orderService.findOrders(orderSearch)
		model.addAttribute("orders", orders)
		return "order/orders"
	}
	
	@PostMapping("/orders/{orderId}/cancel")
	fun cancelOrder(@PathVariable orderId: Long): String {
		orderService.cancelOrder(orderId)
		return "redirect:/orders"
	}
}
