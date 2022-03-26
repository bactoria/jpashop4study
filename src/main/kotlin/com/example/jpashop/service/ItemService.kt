package com.example.jpashop.service

import com.example.jpashop.domain.item.Item
import com.example.jpashop.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(
	private val itemRepository: ItemRepository
) {

	@Transactional
	fun saveItem(item: Item) {
		itemRepository.save(item)
	}

	@Transactional
	fun updateItem(itemId: Long, updateItemDto: UpdateItemDto) {
		val findItem = itemRepository.findOne(itemId)
		findItem.change(
			price = updateItemDto.price,
			name = updateItemDto.name,
			stockQuantity = updateItemDto.stockQuantity
		)
	}

	fun findItems(): List<Item> {
		return itemRepository.findAll()
	}

	fun findOne(itemId: Long): Item {
		return itemRepository.findOne(itemId)
	}
}
