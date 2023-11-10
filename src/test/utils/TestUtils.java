package test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TestUtils {
	
	//Get latest date from a list of Dates
    public static String getLatestDate(List<String> dates) {
    	ArrayList<Date> dateList=new ArrayList<>(); 
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd"); 
    	
    	for (Iterator<String> iterator = dates.iterator(); iterator.hasNext();) {
    		try {
				dateList.add(format.parse(iterator.next()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
		
    	Collections.sort(dateList);    	
    	return format.format(dateList.get(dateList.size()-1));
    } 
}
