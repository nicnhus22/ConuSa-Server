package com.service.app;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;

import com.entities.WordProbability;
import com.helper.db.DatabaseHelper;
import com.helper.request.RequestHelper;

@Path("/app/service")
public class ApplicationService {
	
	@GET
	@Path("/review")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject fetchReviews(
			@QueryParam("name") String appName, 
			@QueryParam("rating") String appRating, 
			@Context UriInfo uriInfo, String content){
		
		return DatabaseHelper.fetchReviews(appName, appRating);
	}
	
	@GET
	@Path("/application")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject fetchApplication(
			@QueryParam("name") String appName, 
			@QueryParam("fetchAll") Boolean fetchAll, 
			@Context UriInfo uriInfo, String content){
		
		return DatabaseHelper.fetchApplications(appName,fetchAll);
	}
	
	@GET
	@Path("/probability")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WordProbability> fetchProbabilities(
			@QueryParam("rating") int rating, 
			@QueryParam("limit") int limit, 
			@Context UriInfo uriInfo, String content){
		
		return DatabaseHelper.fetchProbabilities(rating,limit);
	}
	
	@GET
	@Path("/analyze")
	@Produces(MediaType.APPLICATION_JSON)
	public String analyzeReview(
			@QueryParam("review") String review, 
			@Context UriInfo uriInfo, String content){
		
		return review;
	}

	@POST
	@Path("/override")
	@Produces(MediaType.APPLICATION_JSON)
	public String overrideReviewRating(
			@FormParam("id") String id,
			@FormParam("current") String current,
			@FormParam("override") String override,
			@Context UriInfo uriInfo, String content){
		
		return DatabaseHelper.addToGoldStandard(id, current, override);
	}


}