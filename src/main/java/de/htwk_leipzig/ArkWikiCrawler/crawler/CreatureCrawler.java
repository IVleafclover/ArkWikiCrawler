package de.htwk_leipzig.ArkWikiCrawler.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CreatureCrawler {

	private final String PREDOMIN = "http://ark.gamepedia.com";

	private final String USERAGENTBROWSER = "Mozilla";

	public void crawlCreature(String creatureURLName) {
		try {
			// mit Webpage verbinden
			Document webPage = Jsoup.connect(PREDOMIN + creatureURLName).userAgent(USERAGENTBROWSER).get();

			// Tabellen auslesen
			Elements tamingTables = webPage.select("table.wikitable[data-description^=Taming]");
			for (Element tamingTable : tamingTables) {
				Elements rows = tamingTable.select("tr");
				for (int i = 0; i < rows.size(); i++) {
					Element row = rows.get(i);
					if (isFirst(i)) {
						String tableHead = row.select("th").get(0).text();
						System.out.println(tableHead);
					} else {
						Elements columns = row.select("td");
						if (!isRowWithNoColumns(columns)) {
							for (int j = 0; j < columns.size(); j++) {
								Element column = columns.get(j);
								if (isFirst(j)) {
									String name = column.child(1).text();
									System.out.print(name + " - ");
								} else {
									String value = column.text();
									System.out.print(value + " - "	);
								}
							}
						} else {
							String value = columns.get(0).text();
							System.out.print(value);
						}
						System.out.print("\n");
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isFirst(int rowIndex) {
		return rowIndex == 0;
	}

	private boolean isRowWithNoColumns(Elements columns) {
		return columns.size() < 2;
	}
}
