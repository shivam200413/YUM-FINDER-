package com.week11.zomato.data.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.week11.zomato.data.model.RestaurantImages;

public interface RestaurantImagesRepo extends CrudRepository<RestaurantImages, Integer> {
    @Query(value = "DELETE FROM restaurant_images where restaurant_id = :rId", nativeQuery = true)
    @Modifying
    @Transactional
    public void deleteByRestaurantid(Integer rId);
}
