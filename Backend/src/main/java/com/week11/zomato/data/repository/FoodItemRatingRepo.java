package com.week11.zomato.data.repository;

import org.springframework.data.repository.CrudRepository;

import com.week11.zomato.data.model.FoodItemRating;

public interface FoodItemRatingRepo extends CrudRepository<FoodItemRating, Integer> {

}
