package com.sidheshenator.webscrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This is a more confined crawler. It crawlers only the given domain; hence
 * that weird regex check. cookies not taken into consideration
 * 
 * @author sidhesh
 * 
 */
public class WebPageMod1 {

	static String telephoneRegex = "[[+][1]]{0,1}[\\d]{3}[-][\\d]{3}[-][\\d]{4}";
	static String emailRegex = "[\\w]+[@][\\w]+[.][\\w]+";
	static String domainRegex = null;
	static Integer one = new Integer(1);
	static Integer zero = new Integer(0);
	static Set<String> telephoneSet = new HashSet<String>();
	static Set<String> emailSet = new HashSet<String>();
	static HashMap<String, Integer> masterSet = new HashMap<String, Integer>();

	// list of urls to visit in the current page
	HashMap<String, Integer> URLSet = new HashMap<String, Integer>();

	void processPage(String url) throws IOException {
		String tmp;
		Pattern pattern;
		Matcher matcher;
		// add to masterset as soon as visited.
		masterSet.put(url, one);
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			pattern = Pattern.compile(telephoneRegex);
			// [DEBUG]
			// System.out.println(doc.toString());
			matcher = pattern.matcher(doc.toString());
			while (matcher.find())
				telephoneSet.add(matcher.group());

			pattern = Pattern.compile(emailRegex);
			matcher = pattern.matcher(doc.toString());
			while (matcher.find())
				emailSet.add(matcher.group());
		} catch (IOException exp) {
			// [DEBUG]
			exp.printStackTrace();
			return;
		}
		// initialized only once! Haa!
		if (domainRegex == null)
			domainRegex = "^" + url + "[/][\\w\\W]*$";
		pattern = Pattern.compile(domainRegex);
		Elements hrefs = doc.select("a");
		for (Element href : hrefs) {
			tmp = href.absUrl("href");
			matcher = pattern.matcher(tmp);
			if (!masterSet.containsKey(tmp) && matcher.find())
				URLSet.put(tmp, 0);
		}
		// [debug]
		// System.out.print(URLSet);
		// iterate over new non visited pages from the URLSet.
		Iterator<Entry<String, Integer>> i2 = URLSet.entrySet().iterator();
		Entry<String, Integer> e;

		while (i2.hasNext()) {
			e = i2.next();
			if (e.getValue().equals(zero)) {
				tmp = e.getKey();
				// [DEBUG]
				System.out.println("Woah! In here. Going to " + tmp);
				new WebPageMod1().processPage(tmp);
				// [DEBUG]
				System.out.println("Came back from " + tmp);
			} else
				return;
		}

	}

}
