package com.example.jpashop.service

data class UpdateItemDto(
	val name: String,
	val price: Int,
	val stockQuantity: Int
)
