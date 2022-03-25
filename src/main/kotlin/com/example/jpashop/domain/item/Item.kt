package com.example.jpashop.domain.item

import com.example.jpashop.domain.Category
import com.example.jpashop.exception.NotEnoughStockException
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

	var stockQuantity: Int = 0,

	@ManyToMany(mappedBy = "items")
	val categories: MutableList<Category> = mutableListOf()
) {
	/**
	 * stock 증가
	 */
	fun addStock(quantity: Int) {
		this.stockQuantity += quantity
	}

	/**
	 * stock 감소
	 */
	fun removeStock(quantity: Int) {
		val restStock = this.stockQuantity - quantity;
		if (restStock < 0) {
			throw NotEnoughStockException()
		}
		this.stockQuantity = restStock // FIXME : 재고관리 이런식으로 하면 동시성 이슈 생길 것 같은데...?
	}
}

// FIXME: noArg 설정 추가되면 nullable  제거해야함. 디폴트생성자 땜에 nullable로 했었음...
