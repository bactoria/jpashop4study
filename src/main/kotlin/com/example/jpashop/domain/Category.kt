package com.example.jpashop.domain

import com.example.jpashop.domain.item.Item
import javax.persistence.*

@Entity
class Category(
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	var id: Long? = null,

	var name: String,

	@ManyToMany
	@JoinTable(name = "category_item", joinColumns = [JoinColumn(name = "category_id")], inverseJoinColumns = [JoinColumn(name = "item_id")])
	val items: MutableList<Item> = mutableListOf(),

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	var parent: Category,

	@OneToMany(mappedBy = "parent")
	val child: MutableList<Category> = mutableListOf()
)
