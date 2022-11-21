package com.pentonix.apache;

import java.util.HashMap;
import java.util.Map;

public class ApacheApplicationTest {
	public static void main(String[] args) {
//		String text = "B-1";
//		String[] textArr = text.split("-");
//		if( textArr[0].toLowerCase().equals("b") ) {
//			System.out.println(textArr[0].toLowerCase());			
//		}
//		characterCount(textArr[0]);		
	}
	
	public static void characterCount(String inputString) {
        HashMap<Character, Integer> charCountMap = new HashMap<Character, Integer>();
        char[] strArray = inputString.toCharArray();
        for (char c : strArray) {
            if (charCountMap.containsKey(c)) {
                charCountMap.put(c, charCountMap.get(c) + 1);
            }
            else {
                charCountMap.put(c, 1);
            }
        }
        for (Map.Entry entry : charCountMap.entrySet()) {
        	char str = (char) entry.getKey();
        	if( str == '.' ) {
        		System.out.println(entry.getValue());
        	}
    		System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
