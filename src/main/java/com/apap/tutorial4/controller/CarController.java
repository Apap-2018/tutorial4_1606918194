package com.apap.tutorial4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.apap.tutorial4.model.*;
import com.apap.tutorial4.service.*;

/**
 * 
 * @author saffana.dira
 * CarController
 *
 */
@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		CarModel car = new CarModel();
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		car.setDealer(dealer);
		
		model.addAttribute("car", car);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add", method = RequestMethod.POST)
	private String addCarSubmit(@ModelAttribute CarModel car) {
		carService.addCar(car);
		return "add";
	}
	
	@RequestMapping(value = "/car/delete/{carId}", method = RequestMethod.GET)
	private String deleteDealerSubmit(@PathVariable(value = "carId") String id, Model model) {
		Long dealerId = Long.parseLong(id);
		DealerModel updated = dealerService.getDealerDetailById(dealerId).orElse(null);
		List<CarModel> theCars = carService.allCars(updated);
		model.addAttribute("theCars", theCars);
		return "deleteCar";
	}
	
	@RequestMapping(value = "/car/delete/id={carId}", method = RequestMethod.POST)
	private String deleteCarSubmit(@PathVariable(value = "carId") String id, Model model) {
		Long carId = Long.parseLong(id);
		CarModel deleted = carService.getCarDetailById(carId).orElse(null);
		carService.deleteCar(deleted);
		return "update";
	}
	
	@RequestMapping(value = "/car/update/{dealerId}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "dealerId") String id, Model model) {
		Long dealerId = Long.parseLong(id);
		DealerModel updated = dealerService.getDealerDetailById(dealerId).orElse(null);
		List<CarModel> theCars = carService.allCars(updated);
		model.addAttribute("theCars", theCars);
		return "updateCar";
	}
	
	@RequestMapping(value = "/car/update/id={carId}", method = RequestMethod.POST)
	private String updateCarSubmit(@PathVariable(value = "carId") String id, @ModelAttribute CarModel car) {
		Long carId = Long.parseLong(id);
		CarModel updated = carService.getCarDetailById(carId).orElse(null);
	
		updated.setBrand(car.getBrand());
		updated.setType(car.getType());
		updated.setPrice(car.getPrice());
		updated.setAmount(car.getAmount());
		carService.addCar(updated);
		return "update";
	}
}
