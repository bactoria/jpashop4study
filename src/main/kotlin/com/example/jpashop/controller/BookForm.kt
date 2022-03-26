package com.example.jpashop.controller

data class BookForm(
	val id: Long? = null,

	val name: String? = null,
	val price: Int? = null,
	val stockQuantity: Int? = null,

	val author: String? = null,
	val isbn: String? = null
)
