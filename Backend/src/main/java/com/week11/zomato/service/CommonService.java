package com.week11.zomato.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.week11.zomato.data.model.FoodItem;
import com.week11.zomato.data.model.RestaurantDetails;
import com.week11.zomato.data.model.RestaurantInfo;
import com.week11.zomato.data.repository.RestaurantInfoRepo;

@Service
public class CommonService {

    @Autowired
    private RestaurantInfoRepo restaurantInfoRepo;

    public ResponseEntity<ArrayList<RestaurantDetails>> getRestaurants() {

        Iterable<RestaurantInfo> restaurants = restaurantInfoRepo.findAll();
        Iterator<RestaurantInfo> i = restaurants.iterator();

        ArrayList<RestaurantDetails> restInf = new ArrayList<RestaurantDetails>();

        while (i.hasNext()) {
            RestaurantInfo r = i.next();
            RestaurantDetails ra = new RestaurantDetails(r.getRestaurantname(), r.getRestaurantid(),
                    r.getRestaurantaddress(), r.getRestaurantimages(),r.getRestaurantrating());

            restInf.add(ra);

        }
        return ResponseEntity.ok().body(restInf);
    }

    // for specific restaurant
    public ResponseEntity<List<FoodItem>> getFoodItems(Map<String, Integer> entity) {

        Optional<RestaurantInfo> restInfo = restaurantInfoRepo.findById(entity.get("restaurantid"));

        RestaurantInfo restaurantInfo = restInfo.get();
        List<FoodItem> foodItem = restaurantInfo.getFoodItem();
        return ResponseEntity.ok().body(foodItem);

    }
}
