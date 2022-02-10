package org.credit.suisse.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.credit.suisse.event.CreditSuisseEvent;
import org.credit.suisse.event.CreditSuisseJSONElement;

public class CreditSuisseUtil {

	private static Logger logger = LogManager.getLogger(CreditSuisseUtil.class);
	
	public static List<CreditSuisseEvent> generateEventFromJsonElement(List<CreditSuisseJSONElement> jsonElementList) {
		logger.log(Level.DEBUG, "[CreditSuisseUtil] - start of generateEventFromJsonElement() method");
		List<CreditSuisseEvent> creditSuisseEventList = new ArrayList();
		Map<String, List<CreditSuisseJSONElement>> creditSuisseJSONElementMap = jsonElementList.stream().collect(Collectors.groupingBy(CreditSuisseJSONElement::getId));
		Set<Entry<String, List<CreditSuisseJSONElement>>> elementEntrySet = creditSuisseJSONElementMap.entrySet();
		for(Entry<String, List<CreditSuisseJSONElement>> entrySet : elementEntrySet) {
			CreditSuisseEvent creditSuisseEvent = new CreditSuisseEvent();
			for(CreditSuisseJSONElement element : entrySet.getValue()) {
				if(creditSuisseEvent.getId() == null) {
					creditSuisseEvent.setId(element.getId());
					creditSuisseEvent.setType(element.getType());
					creditSuisseEvent.setHost(element.getHost());
				}
				
				if(element.getState().equalsIgnoreCase("started")) {
					creditSuisseEvent.setStartTimestamp(element.getTimestamp());
				} else if(element.getState().equalsIgnoreCase("finished")) {
					creditSuisseEvent.setFinishTimestamp(element.getTimestamp());
				}
			}
			creditSuisseEvent.setDuration(creditSuisseEvent.getFinishTimestamp() - creditSuisseEvent.getStartTimestamp());
			if(creditSuisseEvent.getDuration() > 4) {
				creditSuisseEvent.setAlert(true);
			}
			creditSuisseEventList.add(creditSuisseEvent);
		}
		logger.log(Level.INFO, "[CreditSuisseUtil] - " + creditSuisseEventList.size() + " no. of events present in the log file");
		logger.log(Level.DEBUG, "[CreditSuisseUtil] - end of generateEventFromJsonElement() method");
		return creditSuisseEventList;
	}
	
	public static InputStream getInputStream(Map<String, String> argsMap) {
		logger.log(Level.DEBUG, "[CreditSuisseUtil] - start of getInputStream() method");
		InputStream input = null;
		try {
			if(!argsMap.containsKey("logFilePath")) {
				logger.log(Level.ERROR, "[CreditSuisseUtil] - please provide the log file path through the command line arguments");
				System.exit(0);
			}
			logger.log(Level.INFO, "[CreditSuisseUtil] - argument value passed for logFilePath is: " + argsMap.get("logFilePath"));
			input = new FileInputStream(argsMap.get("logFilePath"));
		} catch (FileNotFoundException e) {
			logger.log(Level.ERROR, "[CreditSuisseUtil] - please provide the correct log file path through the command line arguments");
			e.printStackTrace();
			System.exit(0);
		}
		logger.log(Level.DEBUG, "[CreditSuisseUtil] - end of getInputStream() method");
		return input;
	}
}
