package com.sidheshenator.webscrapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

public class MainClass {

	public static void main(String[] args) throws IOException {
		WebPageMod1 wp = new WebPageMod1();
		wp.processPage("http://www.queenpizzanewark.com");

		// printing the crawled links.
		Entry<String, Integer> e;
		Iterator<Entry<String, Integer>> i0 = WebPageMod1.masterSet.entrySet()
				.iterator();
		while (i0.hasNext()) {
			e = i0.next();
			System.out.println(e.getKey() + "\t\t" + e.getValue());
		}
		// printing the telephone numbers.
		Iterator<String> i1 = WebPageMod1.telephoneSet.iterator();
		while (i1.hasNext()) {
			System.out.println(i1.next());
		}

		Iterator<String> i2 = WebPageMod1.emailSet.iterator();
		while (i2.hasNext()) {
			System.out.println(i2.next());
		}

	}

}
