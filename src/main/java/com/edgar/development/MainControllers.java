package com.edgar.development;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController()
@RequestMapping("/path/path")
public class MainControllers {
	
	@Autowired
    private RestTemplate restTemplate;
	
	@GetMapping()
	public String invokeEndpoint() {
		String url = "https://hsbc.local:8443";
        return restTemplate.getForObject(url, String.class);
	}

}
