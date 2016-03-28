package de.htwk_leipzig.ArkWikiCrawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.htwk_leipzig.ArkWikiCrawler.crawler.CreatureCrawler;

public class App {

	private final static String DOMAIN = "http://ark.gamepedia.com/Creatures";

	private final static String USERAGENTBROWSER = "Mozilla";

	public static void main(String[] args) {
		try {
			// mit Webpage verbinden
			Document webPage = Jsoup.connect(DOMAIN).userAgent(USERAGENTBROWSER).get();

			Element creatureTable = webPage.select("table.wikitable").get(0);
			Elements rows = creatureTable.select("tr");

			// alle Kreaturen auslesen
			for (int i = 0; i < rows.size(); i++) {
				Element row = rows.get(i);
				if (!isFirst(i)) {
					Element firstColumn = row.select("td").get(0);
					String link = firstColumn.child(1).attr("href");
					System.out.println("\nLese " + link + " aus\n");	
					new CreatureCrawler().crawlCreature(link);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean isFirst(int rowIndex) {
		return rowIndex == 0;
	}
}
