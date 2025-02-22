package com.week11.zomato.service;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.week11.zomato.data.model.FoodItem;
import com.week11.zomato.data.model.RestaurantImages;
import com.week11.zomato.data.model.RestaurantInfo;
import com.week11.zomato.data.repository.FoodItemRepo;
import com.week11.zomato.data.repository.RestaurantImagesRepo;
import com.week11.zomato.data.repository.RestaurantInfoRepo;

@Service
public class AdminService {

    @Autowired
    private RestaurantInfoRepo restaurantInfoRepo;
    @Autowired
    private RestaurantImagesRepo restaurantImagesRepo;

    @Autowired
    private FoodItemRepo foodItemRepo;

    public ResponseEntity<String> addRestaurant(Map entity) {
        System.out.println("********************************************************" + "success");
        Optional<RestaurantInfo> restaurant = restaurantInfoRepo
                .findByRestaurantnameAndRestaurantaddress((String) entity.get("restaurantname"),
                        (String) entity.get("address"));

        if (restaurant.isPresent()) {
            return ResponseEntity.ok().body("address");
        }
        RestaurantInfo restaurantInfo = new RestaurantInfo();
        restaurantInfo.setRestaurantname((String) entity.get("restaurantname"));
        restaurantInfo.setRestaurantaddress((String) entity.get("address"));

        ArrayList<String> imageLinks = (ArrayList) entity.get("restaurantimages");
        ListIterator<String> ll = imageLinks.listIterator();

        while (ll.hasNext()) {

            RestaurantImages img = new RestaurantImages();
            String link = ll.next();
            img.setLink(link);
            img.setRestaurantInfo(restaurantInfo);
            restaurantInfo.getRestaurantimages().add(img);
            restaurantInfoRepo.save(restaurantInfo);
        }

        return ResponseEntity.ok().body("success");
    }

    public ResponseEntity<String> editRestaurant(Map entity) {

        Integer resturantid = (Integer) entity.get("restaurantid");
        Optional<RestaurantInfo> restaurantInfo = restaurantInfoRepo.findById(resturantid);
        RestaurantInfo rest = restaurantInfo.get();

        Optional<RestaurantInfo> info = restaurantInfoRepo.findByRestaurantnameAndRestaurantaddress(
                (String) entity.get("restaurantname"), (String) entity.get("address"));
        // if (info.isPresent()) {
        // return ResponseEntity.ok().body("address");
        // }

        rest.setRestaurantname((String) entity.get("restaurantname"));
        rest.setRestaurantaddress((String) entity.get("address"));
        restaurantInfoRepo.save(rest);
        restaurantImagesRepo.deleteByRestaurantid(resturantid); // native query written in class

        ArrayList<String> imageLinks = (ArrayList) entity.get("restaurantimages");
        ListIterator<String> ll = imageLinks.listIterator();
        rest = restaurantInfo.get();
        for (int i = 0; i < imageLinks.size(); i++) {
            System.out.println("****************************************************" + imageLinks.get(i));
        }

        while (ll.hasNext()) {

            RestaurantImages img = new RestaurantImages();
            String link = ll.next();
            img.setLink(link);
            img.setRestaurantInfo(rest);
            rest.getRestaurantimages().add(img);
            restaurantInfoRepo.save(rest);
        }
        return ResponseEntity.ok().body("success");

    }

    public ResponseEntity<String> deleteRestaurant(Map entity) {

        restaurantInfoRepo.deleteById((Integer) entity.get("restaurantid"));
        return ResponseEntity.ok().body("success");
    }

    public ResponseEntity<String> addFoodItems(Map entity) {

        Optional<RestaurantInfo> restInfo = restaurantInfoRepo.findById((Integer) entity.get("restaurantid"));
        Optional<FoodItem> fooditem = foodItemRepo.findByRestaurantidAndFoodname((Integer) entity.get("restaurantid"),
                (String) entity.get("foodname"));

        if (fooditem.isPresent()) {
            return ResponseEntity.ok().body("name");
        }
        FoodItem fooditemInfo = new FoodItem();
        fooditemInfo.setFoodname((String) entity.get("foodname"));
        fooditemInfo.setDescription((String) entity.get("description"));
        fooditemInfo.setImage((String) entity.get("image"));
        fooditemInfo.setPrice(Integer.parseInt((String) entity.get("price")));
        foodItemRepo.save(fooditemInfo);
        fooditemInfo.setRestaurantInfo(restInfo.get());
        restInfo.get().getFoodItem().add(fooditemInfo);
        restaurantInfoRepo.save(restInfo.get());
        return ResponseEntity.ok().body("success");

    }

    public ResponseEntity<String> editFoodItems(Map entity) {

        Integer resturantid = (Integer) entity.get("restaurantid");
        Optional<RestaurantInfo> restaurantInfo = restaurantInfoRepo.findById(resturantid);
        RestaurantInfo rest = restaurantInfo.get();
        Integer fooditemid = (Integer) entity.get("fooditemid");
        Optional<FoodItem> fooditem1 = foodItemRepo.findByRestaurantidAndFoodname((Integer) entity.get("restaurantid"),
                (String) entity.get("foodname"));

        if (fooditem1.isPresent() && fooditem1.get().getFooditemid() != fooditemid) {
            return ResponseEntity.ok().body("name");
        }
        Optional<FoodItem> fooditem = foodItemRepo.findById(fooditemid);
        FoodItem f = fooditem.get();
        f.setFoodname((String) entity.get("foodname"));
        f.setDescription((String) entity.get("description"));
        f.setImage((String) entity.get("image"));
        f.setPrice(Integer.parseInt((String)entity.get("price")));
        foodItemRepo.save(f);
        f.setRestaurantInfo(rest);
        restaurantInfoRepo.save(rest);
        return ResponseEntity.ok().body("success");

    }

    public ResponseEntity<String> deleteFooditem(Map<String, String> entity) {
        foodItemRepo.deleteById(Integer.parseInt(entity.get("fooditemid")));
        return ResponseEntity.ok().body("success");
    }

}
