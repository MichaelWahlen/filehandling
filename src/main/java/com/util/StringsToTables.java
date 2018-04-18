package main.java.com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.com.data.Table;

public class StringsToTables {
	
	public static Map<String, Table> convertFolderToTable(Map<String, List<String>> namesAndContent, char delimiter){
		Map<String, Table> returnValue = new HashMap<String, Table>();
		if (namesAndContent.size() > 0) {
			for(Map.Entry<String, List<String>> entry : namesAndContent.entrySet()) {
				List<String> strings = entry.getValue();
				List<List<String>> stringLists = new ArrayList<List<String>>();
				for(String string: strings) {
					List<String> splitStrings = StringUtil.lineSplit(string, delimiter);
					stringLists.add(splitStrings);
				}
				Table newTable = new Table();
				newTable.setTable(stringLists, true);
				returnValue.put(entry.getKey(), newTable);			
			}
		}		
		return returnValue;		
	}
	
}
