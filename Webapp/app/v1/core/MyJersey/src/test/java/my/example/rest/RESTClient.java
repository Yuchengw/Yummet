package my.example.rest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

// for test authentication
import sun.misc.BASE64Encoder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

 
 
public class RESTClient {
    public static void main(String[] args) {
    	//testPostAPIcreateUser();
    	testPostAPIlogin();
    }
    	
    public static void testPostAPIcreateUser() {
        String string = "";
        try {
            // Step1: Let's 1st read file from fileSystem
            InputStream inStream = new FileInputStream(
                    "/Users/otaspg/Devl/JSONFile.txt");
            InputStreamReader inReader = new InputStreamReader(inStream);
            BufferedReader br = new BufferedReader(inReader);
            String line;
            while ((line = br.readLine()) != null) {
                string += line + "\n";
            }
 
            JSONObject jsonObject = new JSONObject(string);
            System.out.println(jsonObject);
 
            // Step2: Now pass JSON File Data to REST Service
            try {
                URL url = new URL("http://localhost:8080/MyJersey/rest/userCreate");
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonObject.toString());
                out.close();
 
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
 
                while (in.readLine() != null) {
                }
                System.out.println("\nREST Service Invoked Successfully..");
                in.close();
            } catch (Exception e) {
                System.out.println("\nError while calling REST Service");
                System.out.println(e);
            }
 
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     
     
    public static void testPostAPIlogin(){
        String url = "http://localhost:8080/MyJersey/rest/userLoginx";
        String authString = "test@test.com" + ":" + "test";
        String authStringEnc = new BASE64Encoder().encode(authString.getBytes());
System.out.println("Base64 encoded auth string: " + authStringEnc);

		Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        
        String input= "{\"email\":\"test@test.com\",\"passwd\":\"test1\"}";
        
        ClientResponse resp = webResource.type("application/json")
                                         .post(ClientResponse.class, input);
       /*
        if(resp.getStatus() != 201){
    		throw new RuntimeException("Failed : HTTP error code : "
   			     + resp.getStatus());
    		}
        */
        System.out.println("Output from Server .... \n");
		String output = resp.getEntity(String.class);
		System.out.println(output);
		}

}