package crawler;

import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class SDVXInPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	public void process(Page page) {
		List<String> links = page.getHtml().links().regex("http://sdvx\\.in/[0-9][0-9]/.*").all();
		page.addTargetRequests(links);

		Document document = page.getHtml().getDocument();
		Elements elements = document.select("title");
		Elements elements2 = document.select(".s > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(3) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1)");

		if (elements.size() != 0) {
			page.putField("Song", elements.get(0).text());
		}
		if (elements2.size() != 0) {
			page.putField("Author", elements2.get(0).text().substring(2).trim());
		}
	}

	@Override
	public Site getSite() {
		return site;

	}

	public static void main(String[] args) {
		Spider.create(new SDVXInPageProcessor()).setDownloader(new HtmlUnitDownloader())
				.addUrl("http://sdvx.in/sort/sort_18.htm")
				.addPipeline(new ConsolePipeline()).thread(5).run();
	}
}