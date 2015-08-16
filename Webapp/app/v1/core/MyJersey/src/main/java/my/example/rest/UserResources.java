package my.example.rest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.List;
	 







import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

//import org.codehaus.jettison.json.JSONObject;
import org.json.JSONObject;

//@Path("/UserResources")
@Path("/")
public class UserResources {
	@POST
	@Path("/userCreate")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Consumes({ MediaType.APPLICATION_XML})
	//@Produces({ MediaType.APPLICATION_XML})
	public Response createUser(InputStream incomingData){
		StringBuilder strBuilder = new StringBuilder();
	    try {
	         BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
	         String line = null;
	         while ((line = in.readLine()) != null) {
	             strBuilder.append(line);
	         }
	    } catch (Exception e) {
	        System.out.println("Error Parsing: - ");
	    }
	    System.out.println("Data Received: " + strBuilder.toString());

	    //DBService dbsvc=new DBService();
	    //dbsvc.createUser();
	    DBUserService.getInstance().createUser();

		// return HTTP response 200 in case of success
        //return Response.status(200).entity(newUser.toString()).build();	}

	 
	    // return HTTP response 200 in case of success
        return Response.status(200).entity(strBuilder.toString()).build();
    }

	@POST
	@Path("/userCreatex")
	//@Consumes(MediaType.APPLICATION_JSON)
	//@Consumes({ MediaType.APPLICATION_XML})
	//@Produces({ MediaType.APPLICATION_XML})
	public Response createUserx(
			@FormParam("email") String email,
			@FormParam("passwd") String passwd){
	    System.out.println("Data Received: email " + email);

	    DBUserService.getInstance().createUser(email, passwd);

	    return Response.status(200)
				.entity("createUserx is called, email : "+email)
				.build();
	}

	@GET
	@Path("/userQueryx")
	//@Consumes({ MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON})
	public Response queryUserx(){
	    return Response.status(200)
				.entity(DBUserService.getInstance().queryUser())
				.build();
	}

	@GET
	@Path("/userQueryx/{email}")
	//@Consumes({ MediaType.APPLICATION_XML})
	@Produces({ MediaType.APPLICATION_JSON})
	public Response queryUserx(@PathParam("email") String email){
	    return Response.status(200)
				.entity(DBUserService.getInstance().queryUser(email))
				.build();
	}


    @GET
    @Path("/userLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Object userLogin(@PathParam("email") String email,
                            @HeaderParam("authorization") String authString) throws IOException{
        
        String decodedAuth = "";
        // Header is in the format "Basic 5tyc0uiDat4"
        // We need to extract data before decoding it back to original string
        String[] authParts = authString.split("\\s+");
        String authInfo = authParts[1];
        // Decode the data back to original string
        byte[] bytes = null;
        bytes = Base64.getDecoder().decode(authInfo);
        decodedAuth = new String(bytes);
System.out.println(decodedAuth);
		
		String[] idpwd = decodedAuth.split(":");
         
        if(!isAuthenticated(idpwd[0], idpwd[1])){
            return "{\"error\":\"User not authenticated\"}";
        }else{
        	return "{\"ok\"}";
        }
    }
	
    @POST
    @Path("/userLoginx")
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
	public Response userLoginx(InputStream incomingData){
    	StringBuilder inputStr = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                inputStr.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }
	    System.out.println("Data Received: " + inputStr.toString());

	    //get the email and passwd from the input string
	    
	    JSONObject jsonObj = new JSONObject(inputStr.toString());
	    String email=jsonObj.getString("email");
	    String passwd=jsonObj.getString("passwd");		
	    
	    boolean ok=isAuthenticated(email, passwd);	
	    
        if(!ok){
    	    return Response.status(401)
    				.entity("failed")
    				.build();
        } else {
        	return Response.status(200)
				.entity("okay")
				.build();
        }
	}
     
    
    
    
    private boolean isAuthenticated(String email, String passwd){
        return DBUserService.getInstance().loginUser(email, passwd);
         
    }
	
	
	
	
	
	
	@GET
	@Produces( { MediaType.APPLICATION_XML})
	//@Produces( { MediaType.APPLICATION_JSON})
	@Path("/userQuerySim")
	public List<User> getAllUsers() {
		return DBsim.getAllUsers();
	}
	 
	@GET
	@Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/userQuerySim/{id}")
	public User getUser(@PathParam("id") int id) {
	return DBsim.getUser(id);
	}
	 
}