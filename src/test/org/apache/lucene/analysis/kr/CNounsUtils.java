package org.apache.lucene.analysis.kr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import junit.framework.TestCase;

public class CNounsUtils extends TestCase {

	public void testAdjust() throws Exception {
		
		List<String> strs = FileUtils.readLines(new File("cnouns_all.txt"));
		
		Map<String, String> compoundList = new TreeMap<String, String>();
		Map<String, String> nounList = new TreeMap();
		
		for(String str : strs) {
			System.out.println(str);
			if(str==null||str.length()<1) continue;
			
			str = str.trim();
			String[] infoNouns = StringUtils.split(str,":");
			if(infoNouns.length==2) {
				infoNouns[0] = infoNouns[0].trim();
				if(compoundList.get(infoNouns[0])==null) compoundList.put(infoNouns[0], str);
			} else {
				if(compoundList.get(str)==null&&str.length()>1) 
					nounList.put(str, str);
			}
		}
		
		writeResult(compoundList,"compounds.txt");
		writeResult(nounList,"noun.txt");
		
	}
	
	private void writeResult(Map map, String fName) throws IOException {
		
		Iterator<String> iter = map.keySet().iterator();
		List list = new ArrayList();
		while(iter.hasNext()) {			
			String str = iter.next();
			list.add(map.get(str));					
		}
		
		FileUtils.writeLines(new File(fName), list);
	}
	
}
