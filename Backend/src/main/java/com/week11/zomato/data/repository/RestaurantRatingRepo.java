package com.week11.zomato.data.repository;

import org.springframework.data.repository.CrudRepository;

import com.week11.zomato.data.model.RestaurantRating;

public interface RestaurantRatingRepo extends CrudRepository<RestaurantRating, Integer> {

}
