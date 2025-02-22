package com.week11.zomato.data.repository;

import org.springframework.data.repository.CrudRepository;

import com.week11.zomato.data.model.OrderFoodItems;

public interface OrderFoodItemsRepo extends CrudRepository<OrderFoodItems, Integer> {

}
