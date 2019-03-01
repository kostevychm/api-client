package me.kostevych.zonkyapi.objects;

import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Loan {
	private Integer id;
	private String url;
	private String name;
	private String rating;
	private Double amount;
	private OffsetDateTime datePublished;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public OffsetDateTime getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(OffsetDateTime datePublished) {
		this.datePublished = datePublished;
	}
	

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) {
	        return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	        return false;
	    }
	    Loan loan = (Loan) o;
	    return Objects.equals(id, loan.id);
	}
}
