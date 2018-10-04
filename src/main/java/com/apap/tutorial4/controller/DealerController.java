package com.apap.tutorial4.controller;

import java.util.Collections;
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
 * DealerController
 *
 */
@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
		dealerService.addDealer(dealer);
		return "add";
	}
	
	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
	private String viewDealer(@RequestParam("dealerId") String id, Model model) {
		Long dealerId = Long.parseLong(id);
		DealerModel archive = dealerService.getDealerDetailById(dealerId).orElse(null);
		
		if (archive == null) {
			model.addAttribute("error", true);
			return "view-dealer";
		}
		
		model.addAttribute("dealerDetail", archive);
		Collections.sort(archive.getListCar());
		model.addAttribute("listCar", archive.getListCar());
		
		return "view-dealer";
	}
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.GET)
	private String updateDealer(@PathVariable(value = "dealerId") String id, Model model) {
		Long dealerId = Long.parseLong(id);
		DealerModel updated = dealerService.getDealerDetailById(dealerId).orElse(null);
		
		model.addAttribute("updateDealer", updated);
		return "updateDealer";
	}
	
	@RequestMapping(value = "/dealer/update/id={dealerId}", method = RequestMethod.POST)
	private String updateDealerSubmit(@PathVariable(value = "dealerId") String id, @ModelAttribute DealerModel dealer) {
		Long dealerId = Long.parseLong(id);
		DealerModel updated = dealerService.getDealerDetailById(dealerId).orElse(null);
		
		updated.setAlamat(dealer.getAlamat());
		updated.setNoTelp(dealer.getNoTelp());
		dealerService.addDealer(updated);
		return "update";
	}
	
	@RequestMapping(value = "/dealer/delete/{dealerId}", method = RequestMethod.GET)
	private String deleteDealerSubmit(@PathVariable(value = "dealerId") String id) {
		Long dealerId = Long.parseLong(id);
		DealerModel deleted = dealerService.getDealerDetailById(dealerId).orElse(null);
		
		dealerService.deleteDealer(deleted);
		return "delete";
	}
	
	@RequestMapping(value = "/dealer/view-all", method = RequestMethod.GET)
	private String viewAllDealer(Model model) {	
		List<DealerModel> allDealers = dealerService.allDealer();
		
		model.addAttribute("allDealers", allDealers);
		return "view-all-dealer";
	}
}