package com.dalhousie.MealStop.Restaurant.service;

import com.dalhousie.MealStop.Meal.service.IMealService;
import com.dalhousie.MealStop.Restaurant.model.Restaurant;
import com.dalhousie.MealStop.Restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class RestaurantServiceImplementation implements IRestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private IMealService mealService;

    @Override
    public void addRestaurant(Restaurant restaurant)
    {
        restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant(long id)
    {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        List<Restaurant> filteredList = new ArrayList<>();
        for(Restaurant restaurant : restaurantList)
        {
            if(restaurant.getUserId() == id)
                filteredList.add(restaurant);
        }

        return filteredList;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant, long id)
    {
        Optional<Restaurant> restaurantData = restaurantRepository.findById(id);
        if(restaurantData.isPresent())
        {
            Restaurant _restaurant = restaurantData.get();
            _restaurant.setRestaurantName(restaurant.getRestaurantName());
            _restaurant.setAddress(restaurant.getAddress());
            _restaurant.setEmail(restaurant.getEmail());
            _restaurant.setPhoneNumber(restaurant.getPhoneNumber());
            _restaurant.setAvailability(restaurant.getAvailability());
            restaurantRepository.save(_restaurant);
            return _restaurant;
        }
        return null;
    }


    @Override
    public List<Restaurant> getAvailableRestaurants(Date startDate, Date endDate)
    {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        List<Restaurant> availableRestaurants = new ArrayList<>();

        if(allRestaurants.size() > 0)
        {
            if(startDate == null || endDate == null)
            {
                new Exception("Please select a valid range");
            }
            else
            {
                DateFormat formatter = new SimpleDateFormat("EEEE", Locale.ENGLISH);
                List<String> daysSelected = new ArrayList<>();

                Calendar cal = Calendar.getInstance();
                while(startDate.compareTo(endDate) <= 0)
                {
                    String weekday = formatter.format(startDate);
                    if(!daysSelected.contains(weekday))
                        daysSelected.add(weekday);

                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 1);
                    startDate = cal.getTime();
                }


                allRestaurants.forEach((restaurant) -> daysSelected.forEach((weekDay) ->{

                    if(restaurant.getAvailability().toLowerCase().contains(weekDay.toLowerCase()))
                    {
                        if(!availableRestaurants.contains(restaurant))
                            availableRestaurants.add(restaurant);
                    }
                }));

                return availableRestaurants;
            }
        }

        return availableRestaurants;
    }

    @Override
    public Restaurant getRestaurantById(Long Id) {
        Restaurant restaurant = restaurantRepository.findById(Id).orElse(null);
        return restaurant;
    }

}
