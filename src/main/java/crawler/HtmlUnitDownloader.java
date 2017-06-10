package crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import org.apache.commons.logging.LogFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;
import us.codecraft.webmagic.selector.PlainText;

/**
 * Created by T on 2017/6/10.
 */
public class HtmlUnitDownloader extends AbstractDownloader {

	private int poolsize;

	@Override
	public Page download(Request request, Task task) {

		Page page = Page.fail();
		try {
			LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
			java.util.logging.Logger.getLogger("net.sourceforge.htmlunit").setLevel(java.util.logging.Level.OFF);

			WebClient webClient = new WebClient();
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setTimeout(1000);
			HtmlPage htmlPage = webClient.getPage(request.getUrl());
			String content = htmlPage.asXml();

			page.setRawText(content);
			page.setUrl(new PlainText(String.valueOf(htmlPage.getUrl())));
			page.setRequest(request);
			page.setDownloadSuccess(true);
			return page;
		} catch (IOException e) {
			e.printStackTrace();
			return page;
		}

	}

	@Override
	public void setThread(int threadNum) {
		this.poolsize=threadNum;
	}

	public int getPoolsize() {
		return poolsize;
	}

	public void setPoolsize(int poolsize) {
		this.poolsize = poolsize;
	}
}
