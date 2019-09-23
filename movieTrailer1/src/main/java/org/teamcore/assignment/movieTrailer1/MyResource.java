package org.teamcore.assignment.movieTrailer1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.ParseException;
import java.util.Scanner;
/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
	public final static String API_KEY = "api_key=ed2f480d35c680cceb45bc6efd6ac96c";
	public final static String query_url = "https://api.themoviedb.org/3/movie/upcoming";
	public final static String video_query_url = "https://api.themoviedb.org/3/movie/";
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getTrailer")
    public String getMovieTrailer(String queryText)
    {
    	JSONArray jsonarr_1=null,video_jsonarr=null,output_arr=new JSONArray();
		HttpURLConnection conn=null,conn1 = null;
    	try
    	{
    		URL url = new URL(query_url+"?"+API_KEY+"&page=1");
    		conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("GET");
    		conn.setRequestProperty("Accept", "application/json");

    		if (conn.getResponseCode() != 200) {
    			throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
    		}

    		String output="";
    		System.out.println("Output from Server .... \n");
    		Scanner sc = new Scanner(url.openStream());
			while(sc.hasNext())
			{
			output+=sc.nextLine();
			}
			JSONParser parse = new JSONParser(); 
			
			JSONObject jobj = (JSONObject)parse.parse(output); 
			
			System.out.println(jobj);
			jsonarr_1 = (JSONArray) jobj.get("results");
			System.out.println(jsonarr_1);

			//Get data for Results array
			for(int i=0;i<jsonarr_1.size();i++)
			{
					JSONObject responseObj = new JSONObject();
					JSONObject jsonobj_1 = (JSONObject)jsonarr_1.get(i);
					Long ID =(Long) jsonobj_1.get("id");
					System.out.println("Elements under results array");
					System.out.println("\nMovie Id: " +ID);
					System.out.println("Title: " +jsonobj_1.get("title"));
					responseObj.put("movie_id", ID);
					responseObj.put("movie_title", jsonobj_1.get("title"));
					responseObj.put("movie_overview", jsonobj_1.get("overview"));
					responseObj.put("poster_path", jsonobj_1.get("poster_path"));
					/*Video KEY Fetch...foreach movies upcoming*/
						URL video_url = new URL(video_query_url+ID+"/videos?"+API_KEY+"&page=1");
						conn1 = (HttpURLConnection) video_url.openConnection();
			    		conn1.setRequestMethod("GET");
			    		conn1.setRequestProperty("Accept", "application/json");
			    		String video_output="";
			    		if (conn.getResponseCode() != 200) {
			    			throw new RuntimeException("Failed : HTTP error code : "+conn1.getResponseCode());
			    		}
			    		Scanner sc1 = new Scanner(video_url.openStream());
						while(sc1.hasNext())
						{
							video_output+=sc1.nextLine();
						}
						JSONParser video_parse = new JSONParser(); 
						
						JSONObject video_jobj = (JSONObject)video_parse.parse(video_output); 
						System.out.println(video_jobj);
						video_jsonarr = (JSONArray) video_jobj.get("results");
						System.out.println(video_jsonarr);
			
						//Get data for Results array
						for(int j=0;j<video_jsonarr.size();j++) {
							JSONObject video_jsonobj = (JSONObject)video_jsonarr.get(j);
							String videoId =(String) video_jsonobj.get("id");
							System.out.println("Elements under results array");
							System.out.println("\nMovie Id: " +videoId);
							if(video_jsonobj.get("type").equals("Trailer"))
							{
							String key = (String) video_jsonobj.get("key");
							System.out.println("key: " +key);
							key = "https://www.youtube.com/embed/"+key;
							responseObj.put("movie_trailer_key", key);
							}
						}
						output_arr.add(responseObj);
						conn1.disconnect();
			}
    	}catch(MalformedURLException e)
    	{
    		e.printStackTrace();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally {
    		conn.disconnect();
    	}
    	return output_arr.toString();
    }
}
