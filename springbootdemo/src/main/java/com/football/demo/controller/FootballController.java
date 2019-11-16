package com.football.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.football.demo.model.Compitition;
import com.football.demo.model.Country;
import com.football.demo.model.Standing;


@RestController
public class FootballController {
	
	@RequestMapping("/")
    public String getStandingSample() 
    {
		return "hello";
    }
	
	@RequestMapping("/api/getStandingByCountryName")
	public List<List<Standing>> getStandingByCountryName(@RequestParam(name="APIkey") String apiKey,@RequestParam String action,@RequestParam (name="country_name")String countryName) {
		List<Country> countryList = getAllCountries(apiKey);
		Country countryObject=new Country();
		for(Country country:countryList ) {
			if(country.getCountryName().equalsIgnoreCase(countryName)) {
				countryObject.setCountry_id(country.getCountryId());
				countryObject.setCountry_name(country.getCountryName());
				break;
			}
		}
		
		List<Compitition> compititionList=getAllCompitationByCountryId(countryObject.getCountryId(), apiKey);
		List<List<Standing>> standingList = new ArrayList<List<Standing>>();
		for(Compitition comp:compititionList) {
			standingList.add(getAllStandingByLeagueId(countryObject.getCountryId(),comp.getLeague_id(),apiKey));
		}
		return standingList;
		
	}

	@RequestMapping("/api/getStandingByLeagueName")
	public List<List<Standing>> getStandingByLeagueName(@RequestParam(name="APIkey") String apiKey,@RequestParam String action,@RequestParam (name="league_name")String leagueName) {
		List<Country> countryList = getAllCountries(apiKey);
		Country countryObject=new Country();
		List<List<Standing>> standingList = new ArrayList<List<Standing>>();
		for(Country country:countryList ) {
			countryObject.setCountry_id(country.getCountryId());
			countryObject.setCountry_name(country.getCountryName());
			List<Compitition> compititionList=getAllCompitationByCountryId(countryObject.getCountryId(), apiKey);
			for(Compitition compitition:compititionList) {
				if(compitition.getLeague_name().equalsIgnoreCase(leagueName)) {
					standingList.add(getAllStandingByLeagueId(countryObject.getCountryId(),compitition.getLeague_id(),apiKey));
					break;
				}
			}
		}
		return standingList;
		
	}
	private List<Country> getAllCountries(String apiKey) {
		final String uri = "https://apiv2.apifootball.com/?"
				+ "action=get_countries&"
				//+ "league_id=148&"
				+ "APIkey="+apiKey;
	     
	    RestTemplate restTemplate = new RestTemplate();
	     ResponseEntity<Country[]> response =
	    		  restTemplate.getForEntity(uri,
	    		  Country[].class);
	  
		List<Country> countryList= Arrays.asList(response.getBody());
		return countryList;
	}
	
	private List<Compitition> getAllCompitationByCountryId(String countryId,String apiKey) {
		final String uri = "https://apiv2.apifootball.com/?"
				+ "action=get_leagues&"
				+ "country_id="+countryId+"&"
				+ "APIkey="+apiKey;
	     
	    RestTemplate restTemplate = new RestTemplate();
	     ResponseEntity<Compitition[]> response =
	    		  restTemplate.getForEntity(uri,
	    		  Compitition[].class);
	  
		List<Compitition> compititionList= Arrays.asList(response.getBody());
		return compititionList;
	}
	private List<Standing> getAllStandingByLeagueId(String countryId,String leagueId,String apiKey) {
		final String uri = "https://apiv2.apifootball.com/?"
				+ "action=get_standings&"
				+ "league_id="+leagueId+"&"
				+ "APIkey="+apiKey;
	     
	    RestTemplate restTemplate = new RestTemplate();
	     ResponseEntity<Standing[]> response =
	    		  restTemplate.getForEntity(uri,
	    				  Standing[].class);
	  
		List<Standing> standingList= Arrays.asList(response.getBody());
		for(Standing standing:standingList) {
			standing.setCountry_id(countryId);
		}
		return standingList;
	}

}
