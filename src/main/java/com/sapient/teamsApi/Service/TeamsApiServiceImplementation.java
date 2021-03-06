package com.sapient.teamsApi.Service;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapient.teamsApi.Data.TeamsApiDataImpl;
import com.sapient.teamsApi.DataDocuments.CitationCollection;
import com.sapient.teamsApi.DataDocuments.MapperCollection;
import com.sapient.teamsApi.Helpers.JsonToStringToJson;

@Service
public class TeamsApiServiceImplementation implements TeamsApiService {

	@Autowired
	TeamsApiDataImpl teamsApiDataImpl;



	public static void main(String[]  systemargs) {
		String s="{\"name\":\"composeExtension/submitAction\",\"type\":\"invoke\",\"timestamp\":\"2021-02-19T10:25:16.38Z\",\"localTimestamp\":\"2021-02-19T15:55:16.38+05:30\",\"id\":\"f:a8b1e254-7f1b-3f7d-18cd-6a63a8d74895\",\"channelId\":\"msteams\",\"serviceUrl\":\"https://smba.trafficmanager.net/amer/\",\"from\":{\"id\":\"29:1qN1LMly7HzGCpvvzn2NCetUh7g_Fzs75CDsa-l1vH8y1ZIYGdpKPfW_lx9NIdEnGbkbQ1K40uqP_LuzpEm-KrA\",\"name\":\"Napa Manoj\",\"aadObjectId\":\"45024d95-82dd-48d7-9218-2cde1e3f7402\"},\"conversation\":{\"isGroup\":true,\"conversationType\":\"groupChat\",\"tenantId\":\"d52c9ea1-7c21-47b1-82a3-33a74b1f74b8\",\"id\":\"19:96fe89ab1c75498cb3f2f09719d29af7@thread.v2\",\"name\":\"FCA Unofficial\"},\"recipient\":{\"id\":\"28:02740583-af17-4103-b7e5-bca0505cceb0\",\"name\":\"My Bot1\"},\"entities\":[{\"locale\":\"en-US\",\"country\":\"US\",\"platform\":\"Windows\",\"timezone\":\"Asia/Calcutta\",\"type\":\"clientInfo\"}],\"channelData\":{\"tenant\":{\"id\":\"d52c9ea1-7c21-47b1-82a3-33a74b1f74b8\"},\"source\":{\"name\":\"compose\"}},\"value\":{\"commandId\":\"createCard\",\"commandContext\":\"compose\",\"requestId\":\"26fb6fb02deb4405861bb6e505583335\",\"data\":{\"id\":\"submitButton\",\"Email\":\"reddy@publicisgroupe.net\",\"Citation\":\"thank you\",\"Points\":\"3\"},\"context\":{\"theme\":\"default\"}},\"locale\":\"en-US\",\"localTimezone\":\"Asia/Calcutta\"}\r\n" + 
				"";
		String s1="{\r\n" + 
				"  \"composeExtension\": {\r\n" + 
				"    \"attachments\": [\r\n" + 
				"      {\r\n" + 
				"        \"content\": {\r\n" + 
				"          \"text\": \"congrtas bro \"\r\n" + 
				"        },\r\n" + 
				"        \"contentType\": \"application/vnd.microsoft.card.hero\",\r\n" + 
				"        \"preview\": {\r\n" + 
				"          \"content\": {\r\n" + 
				"            \"text\": \"\"\r\n" + 
				"          },\r\n" + 
				"          \"contentType\": \"application/vnd.microsoft.card.hero\"\r\n" + 
				"        }\r\n" + 
				"      }\r\n" + 
				"    ],\r\n" + 
				"    \"type\": \"result\",\r\n" + 
				"    \"attachmentLayout\": \"list\"\r\n" + 
				"  },\r\n" + 
				"  \"responseType\": \"composeExtension\"\r\n" + 
				"}";
		//	new TeamsApiServiceImplementation().takeDataFromApi(s);

		MapperCollection mapperObject=new MapperCollection();
		mapperObject.setEmail("abc@gmail.com");
		mapperObject.setJsonId("edf");
		mapperObject.setName("saimanoj");
		mapperObject.setPoints(100);
		mapperObject.setTimestamp(new Date());
		System.out.println("came here aswell");
		new TeamsApiDataImpl().saveMapperData(mapperObject);
	}


	@Override
	public String citationWritten(String citationData) {
		String 	result=null;
		JSONObject jsonObject=JsonToStringToJson.convertStringtoJson(citationData);
		JSONObject valueObject=(JSONObject)jsonObject.get("value");
		JSONObject data=(JSONObject)valueObject.get("data");
		String email=(String)data.get("Email");
		JSONObject from=(JSONObject)jsonObject.get("from");
		String jsonId=(String)from.get("id");
		boolean isValidJsonId=teamsApiDataImpl.isValidJsonId(jsonId);
		boolean isValidEmail =teamsApiDataImpl.isValidEmail(email);
		if(isValidJsonId && isValidEmail) {
			MapperCollection fromObject=teamsApiDataImpl.getMapperObject(jsonId);
			MapperCollection toObject=teamsApiDataImpl.getMapperObjectWithEmail(email);
			String citation=(String)data.get("Citation");
			String type=(String)data.get("type");
			CitationCollection citationCollection=new CitationCollection();
			citationCollection.setCitation(citation);
			citationCollection.setFrom(fromObject.getName());
			citationCollection.setFrom_email(fromObject.getEmail());
			citationCollection.setPoints(0);
			citationCollection.setTimestamp(new Date());
			citationCollection.setTo(toObject.getName());
			citationCollection.setTo_email(toObject.getEmail());
			citationCollection.setType(type);

			teamsApiDataImpl.saveCitationData(citationCollection);
			result="Succesfull";
		}
		else if(!isValidJsonId) {
			result="please login first.This is just one time login ";
		}
		else {
			result="Rewardee didnt login once";
		}
		return result;
	}

	@Override
	public String pointsGiven(String pointsData) {
		String 	result=null;
		JSONObject jsonObject=JsonToStringToJson.convertStringtoJson(pointsData);
		JSONObject valueObject=(JSONObject)jsonObject.get("value");
		JSONObject data=(JSONObject)valueObject.get("data");
		String email=(String)data.get("email");
		JSONObject from=(JSONObject)jsonObject.get("from");
		String jsonId=(String)from.get("id");
		boolean isValidJsonId=teamsApiDataImpl.isValidJsonId(jsonId);
		boolean isValidEmail =teamsApiDataImpl.isValidEmail(email);
		if(isValidJsonId && isValidEmail) {
			MapperCollection fromObject=teamsApiDataImpl.getMapperObject(jsonId);
			MapperCollection toObject=teamsApiDataImpl.getMapperObjectWithEmail(email);
			String pointsString=(String)data.get("points");
			int points=Integer.parseInt(pointsString);
			String type=(String)data.get("type");
			CitationCollection citationCollection=new CitationCollection();
			citationCollection.setCitation(null);
			citationCollection.setFrom(fromObject.getName());
			citationCollection.setFrom_email(fromObject.getEmail());
			citationCollection.setPoints(points);
			citationCollection.setTimestamp(new Date());
			citationCollection.setTo(toObject.getName());
			citationCollection.setTo_email(toObject.getEmail());
			citationCollection.setType(type);
			Date lastrefreshed=fromObject.getTimestamp();
			Date presentDate=new Date();
			int lastRefreshedYear=lastrefreshed.getYear();
			int presentYear=presentDate.getYear();
			int lastRefreshedMonth=lastrefreshed.getMonth();
			int presentMonth=presentDate.getMonth();
			if(points>100) {
				result="You cannot give more than 100 points";
			}
			else {
				if(presentYear>lastRefreshedYear ||  presentMonth>lastRefreshedMonth) {
					fromObject.setPoints(100-points);
					teamsApiDataImpl.saveMapperData(fromObject);
					teamsApiDataImpl.saveCitationData(citationCollection);
					result="Succesfull";
				}
				else {
					if(points>fromObject.getPoints()) {
						result="you dont have enough points for this month your left with :"+fromObject.getPoints();
					}
					else {
						fromObject.setPoints(fromObject.getPoints()-points);
						teamsApiDataImpl.saveMapperData(fromObject);
						teamsApiDataImpl.saveCitationData(citationCollection);
						result="Succesfull";
					}
				}

			}


		}
		else if(!isValidJsonId) {
			result="please login first.This is just one time login ";
		}
		else {
			result="Rewardee didnt login once";
		}
		return result;
	}








	@Override
	public String LoginFunctionality(String loginDetails) {
		String result=null;
		JSONObject jsonObject=JsonToStringToJson.convertStringtoJson(loginDetails);
		JSONObject valueObject=(JSONObject)jsonObject.get("value");
		JSONObject data=(JSONObject)valueObject.get("data");
		String email=(String)data.get("Email");
		JSONObject from=(JSONObject)jsonObject.get("from");
		String jsonId=(String)from.get("id");
		String name=(String)from.get("name");
		String timestamp=(String)jsonObject.get("timestamp");
		boolean isValidJsonId=teamsApiDataImpl.isValidJsonId(jsonId);
		if(isValidJsonId) {
			MapperCollection object=teamsApiDataImpl.getMapperObject(jsonId);
			System.out.println(object.getName());
			System.out.println(object.getTimestamp());
			object.setEmail(email);
			teamsApiDataImpl.saveMapperData(object);
		}
		else {
			System.out.println("came here");
			MapperCollection mapperObject=new MapperCollection();
			mapperObject.setEmail(email);
			mapperObject.setJsonId(jsonId);
			mapperObject.setName(name);
			mapperObject.setPoints(100);

			//			String[] splitTimestamp=timestamp.split("T");
			//			String[] splitTime=splitTimestamp[0].split("-");
			//			System.out.println(timestamp+" "+splitTime[0]+" "+splitTime[1]+" "+splitTime[2]);
			//			int year=Integer.parseInt(splitTime[0]);
			//			int month=Integer.parseInt(splitTime[1]);
			//			int day=Integer.parseInt(splitTime[2]);
			//			//Date date=new Date(year,month,day);



			Date date=new Date();
			mapperObject.setTimestamp(date);
			System.out.println("came here aswell");
			teamsApiDataImpl.saveMapperData(mapperObject);

		}
		return null;
	}


	@Override
	public boolean checkPersonLoginStatus(String loginDetails) {

		return false;
	}


	@Override
	public boolean isValidEmail(String teamsData) {

		return false;
	}

}
