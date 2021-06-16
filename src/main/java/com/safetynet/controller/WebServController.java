package com.safetynet.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.service.WebServices;

@RestController
public class WebServController {
	@Autowired
	WebServices ws;

//	http://localhost:8080/communityEmail?city=<city>
	@GetMapping("/communityEmail")
	public List<String> getEmailListByCity(@RequestParam String city) {
		return ws.emailListByCity(city);
	}
	
//	http://localhost:8080/firestation?stationNumber=<station_number>
	//pour éviter des conflits, remplacée par:
//	http://localhost:8080/firestationCount?station=<station_number>
	@GetMapping("/firestationCount")
	public Map<String,Object> getPersonsAndCountByAge_ByFirestationNumber(@RequestParam String station) {
		return ws.getPersonsByFirestationNumber(station);
	}
	
//	http://localhost:8080/childAlert?address=<address>
	@GetMapping("/childAlert")
	public Map<String,Object> getChildrenAndTheirFamilyMembersByAddress(@RequestParam String address) {
		return ws.getChildrenAndFamilyMembersByAddress(address);
	}
}