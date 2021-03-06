package org.sakila.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.sakila.rest.bean.ActorBean;
import org.sakila.rest.toa.ActorTOA;
import org.sakila.webservices.dao.ActorDAO;
import org.sakila.webservices.model.Actor;

@Path("/actor")
@Produces(MediaType.APPLICATION_JSON)
public class ActorRESTService {

	@Inject
	private ActorDAO actorDAO;
	
	@GET
	public Response list(){
		List<Actor> l = actorDAO.list();
		ArrayList<ActorBean> al = ActorTOA.toBean(l);
		ResponseBuilder rb = Response.ok(al, MediaType.APPLICATION_JSON);
		Response r = rb.build();
		return r;
	}
	
	@GET
	@Path("/{id}")
	public Response detail(@PathParam("id") int id){
		Actor l = actorDAO.find(id);
		if(l==null){
			return Response.noContent().build();
		}
		ActorBean ab = ActorTOA.toBean(l);
		ResponseBuilder rb = Response.ok(ab, MediaType.APPLICATION_JSON);
		Response r = rb.build();
		return r;
	}
	
	@POST
	public Response create(ActorBean ab){
		if(ab==null){
			return Response.noContent().build();
		}
		Actor a = ActorTOA.toModel(ab);
		Optional<Actor> optional = actorDAO.save(a);
		Response response;
		if(optional.isPresent()) {
			ActorBean ab1 = optional.map(ActorTOA::toBean).get();
			response = Response.ok(ab1, MediaType.APPLICATION_JSON).build();
		}else {
			response = Response.serverError().build();
		}
		return response;
	}
	
	@PUT
	public Response update(ActorBean ab){
		if(ab==null){
			return Response.noContent().build();
		}
		Actor a = ActorTOA.toModel(ab);
		Optional<Actor> optional = actorDAO.update(a);
		Response response;
		if(optional.isPresent()) {
			ActorBean ab1 = optional.map(ActorTOA::toBean).get();
			response = Response.ok(ab1, MediaType.APPLICATION_JSON).build();
		}else {
			response = Response.serverError().build();
		}
		return response;
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id){
		Optional<Actor> optional = actorDAO.delete(id);
		Response response;
		if(optional.isPresent()) {
			ActorBean ab = optional.map(ActorTOA::toBean).get();
			response = Response.ok(ab, MediaType.APPLICATION_JSON).build();
		}else {
			response = Response.serverError().build();
		}
		return response;
	}
	
}
