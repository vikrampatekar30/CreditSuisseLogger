package org.credit.suisse.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.credit.suisse.event.CreditSuisseJSONElement;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONParserUtil {
	
	private static Logger logger = LogManager.getLogger(JSONParserUtil.class);

	public static List<CreditSuisseJSONElement> parseJSON(InputStream input) {
		logger.log(Level.DEBUG, "[JSONParserUtil] - start of parseJSON() method");
		List<CreditSuisseJSONElement> jsonElementList = new ArrayList();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	JSONParser parser = new JSONParser();  
		    	try {
					JSONObject json = (JSONObject) parser.parse(line);
					logger.log(Level.INFO, "[JSONParserUtil] - successfully parsed String to JSON object " + json.toJSONString());
					Set<Map.Entry> entrySet = json.entrySet();
					CreditSuisseJSONElement jsonElement = new CreditSuisseJSONElement();
					for(Map.Entry entry : entrySet) {
						
						switch(entry.getKey().toString()) {
						case "id" :
							jsonElement.setId(entry.getValue().toString());
							break;
						case "state" :
							jsonElement.setState(entry.getValue().toString());
							break;
						case "timestamp" :
							jsonElement.setTimestamp(Long.parseLong(entry.getValue().toString()));
							break;
						case "type" :
							jsonElement.setType(entry.getValue().toString());
							break;
						case "host" :
							jsonElement.setHost(entry.getValue().toString());
							break;
						
						}
					}
					
					jsonElementList.add(jsonElement);
				} catch (ParseException e) {
					logger.log(Level.ERROR, "[JSONParserUtil] - error while parsing the String to JSON object");
					e.printStackTrace();
				}  
		    }
		} catch (IOException e) {
			logger.log(Level.ERROR, "[JSONParserUtil] - error while reading the input stream");
			e.printStackTrace();
		}
		logger.log(Level.INFO, "[JSONParserUtil] - " + jsonElementList.size() + " no. of JSON objects provided in the log file");
		logger.log(Level.DEBUG, "[JSONParserUtil] - end of parseJSON() method");
		return jsonElementList;
	}
}
