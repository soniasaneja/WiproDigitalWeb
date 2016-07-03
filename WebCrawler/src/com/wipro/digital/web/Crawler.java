package com.wipro.digital.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/*
 * Sonia Saneja
 * Crawler.java
 * 03-Jul-2016
 * This programme run for more than 20 min 
 */

public class Crawler {
    public static void main(String[] args) throws IOException {
            String strUrl = "http://wiprodigital.com/" ;
            URL url = new URL(strUrl);
            Crawler c = new Crawler();
            HashMap siteMap = c.callCrawler(url, new HashSet<String>());
    }
    
    
    /*
     * callCrawler is expecting a url and set as input, first time it will be empty set
     */
    public HashMap callCrawler(URL url, HashSet<String> hashSet) throws UnsupportedEncodingException, IOException{
    HashMap<String, HashMap> siteMap = new HashMap<String, HashMap>();
    
    //Set is used for avoid duplicates urls
    
    Set<URL> linkList = new HashSet<URL>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
        for (String line; (line = reader.readLine()) != null;) {
        	// String manipulation since one url was coming with single quotes  
            line = line.replace('\'', '\"');
            if(line.contains("wiprodigital.com") && line.contains("href=")){
                    String link = line.split("href=")[1].split("\"")[1] ; // Spliting on href
                    if( link.contains("wiprodigital.com")  && hashSet.add(link))
                    {
                            linkList.add(new URL(link));
                    }
            }
        }
    }
    for(URL linkUrl : linkList){
    	
    	// Exception handling for some of the invalid urls and post request 
            try{
                    System.out.println(linkUrl.toString());
                    // Recursive call to be run for each url
                    siteMap.put(linkUrl.toString(), callCrawler(linkUrl, hashSet));
            }catch(Exception e){
                    System.out.println(e.getMessage());
            }
    }
    return  siteMap ;
    }
}

