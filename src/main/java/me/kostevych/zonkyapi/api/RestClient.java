package me.kostevych.zonkyapi.api;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import me.kostevych.zonkyapi.objects.Loan;

/** 
 * Provides communication with REST api entrypoint 
 */
@Component
public class RestClient {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private RestTemplate restTemplate;
	
	//URL of API entrypoint from property file
	@Value("${api.source-url}")
	private String sourceUrl;

	@Value("${api.initial-loans-count}")
	private Integer initialCount;
    
	public RestClient() {
		this.restTemplate = new RestTemplateBuilder().build();
	}

	/** 
	 * Get initial data from rest
	 */
	public List<Loan> getInitialLoans()
	{		
		logger.debug("Getting initial data...");

		Map<String,String> headers = new HashMap<>();
	    
		//According to documentation will return limited count of results.
		headers.put("X-Size", initialCount.toString());
	    
		URI uri = UriComponentsBuilder.fromHttpUrl(sourceUrl).build(true).toUri();
	
		return getFromURI(uri, headers);
	}
	
	/** 
	 * Check new loans 
	 */
	public List<Loan> getNewLoans(OffsetDateTime afterTime) {
		logger.debug("Getting new loans... After: {}", afterTime);

		//Find all published later than @afterTime
    	URI uri = UriComponentsBuilder.fromHttpUrl(sourceUrl)
                .queryParam("datePublished__gt", UriUtils.encode(afterTime.toString(), "UTF-8"))
                .build(true).toUri();

    	return getFromURI(uri, null);
	}
	
	/** 
	 * Send requests and returns list of loans 
	 */
	private List<Loan> getFromURI(URI uri, Map<String,String> customHeaders) {
		logger.debug("Downloading from REST entrypoint...");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		//Order by
		headers.set("X-Order", "-datePublished");
        
		//Add additional headers to HttpHeaders
		if(customHeaders != null && !customHeaders.isEmpty()) {
        	for(String key : customHeaders.keySet())
        		headers.set(key, customHeaders.get(key));
		}
		
		ResponseEntity<Loan[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<String>(headers), Loan[].class);
		Loan[] returnedLoans = responseEntity.getBody();
		return returnedLoans != null ? Arrays.asList(returnedLoans) : Collections.emptyList();
	}
}
