package com.sidheshenator.webscrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This is unconfined crawler. Crawls over any link it comes accross. Cookies
 * not considered.
 * 
 * @author sidhesh
 * 
 */
public class WebPage {

	static Integer one = new Integer(1);
	static Integer zero = new Integer(0);
	static HashMap<String, Integer> masterSet = new HashMap<String, Integer>();

	// list of urls to visit in the current page
	HashMap<String, Integer> URLSet = new HashMap<String, Integer>();

	void processPage(String url) throws IOException {
		String tmp;
		// add to masterset as soon as visited.
		masterSet.put(url, one);
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException exp) {
			return;
		}
		Elements hrefs = doc.select("[abs:href]");
		Iterator<Element> i1 = hrefs.iterator();
		while (i1.hasNext()) {
			tmp = i1.next().absUrl("href");
			if (!masterSet.containsKey(tmp))
				URLSet.put(tmp, 0);
		}
		// iterate over new non visited pages from the URLSet.
		Iterator<Entry<String, Integer>> i2 = URLSet.entrySet().iterator();
		Entry<String, Integer> e;

		while (i2.hasNext()) {
			e = i2.next();
			if (e.getValue().equals(zero)) {
				tmp = e.getKey();
				System.out.println("Woah! In here. Going to " + tmp);
				new WebPage().processPage(tmp);
				System.out.println("Came back from " + tmp);
			} else
				return;
		}

	}

}
