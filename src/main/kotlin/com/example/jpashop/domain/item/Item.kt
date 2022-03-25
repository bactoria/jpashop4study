package com.example.jpashop.domain.item

import com.example.jpashop.domain.Category
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	var id: Long? = null,

	var name: String? = null,

	var price: Int? = null,

	var stockQuantity: Int? = null,

	@ManyToMany(mappedBy = "items")
	val categories: MutableList<Category> = mutableListOf()
)

// FIXME: noArg 설정 추가되면 nullable  제거해야함. 디폴트생성자 땜에 nullable로 했었음...
