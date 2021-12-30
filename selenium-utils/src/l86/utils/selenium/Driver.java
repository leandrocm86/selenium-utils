package l86.utils.selenium;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import estruturas.Lista;
import io.Log;

public class Driver implements WebDriver {
	
	public final static String DRIVER_URL = "/usr/bin/chromedriver"; // apt-install chromium-chromedriver
	
	private WebDriver webDriver;
	
//	private Integer janelasAbertas = 0;
	
	public Driver() {
		try {
			this.iniciaNovoWebDriver();
		}
		catch(Throwable t) {
			Log.logaErro(t);
			throw t;
		}
	}
	
	private void iniciaNovoWebDriver() {
//		System.setProperty("webdriver.firefox.marionette","/home/06742776609/geckodriver");
//		instance = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", DRIVER_URL);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		webDriver = new ChromeDriver(options);
		webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@Override
	public void get(String url) {
		try {
			webDriver.get(url);
		} catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.remote.UnreachableBrowserException e) {
			Log.msgLn("Timeout para carregar a pagina!");
//			try {
//				Thread.sleep(30000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
	    }
	}
	
	public void get(CharSequence url) {
		this.get(url.toString());
	}

	@Override
	public String getCurrentUrl() {
		return webDriver.getCurrentUrl();
	}

	@Override
	public String getTitle() {
		return webDriver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {
		return webDriver.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return webDriver.findElement(by);
	}

	@Override
	public String getPageSource() {
		return webDriver.getPageSource();
	}

	@Override
	public void close() {
		webDriver.close();
	}

	@Override
	public void quit() {
		if (webDriver != null)
			webDriver.quit();
	}

	@Override
	public Set<String> getWindowHandles() {
		return webDriver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {
		return webDriver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		return webDriver.switchTo();
	}

	@Override
	public Navigation navigate() {
		return webDriver.navigate();
	}

	@Override
	public Options manage() {
		return webDriver.manage();
	}
	
	public void espera() {
		espera(10000);
	}
	
	public void espera(long millisecs) {
		try {
			Thread.sleep(millisecs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void esperaPouco() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Elemento byName(String name) {
		return new Elemento(webDriver.findElement(By.name(name)));
	}
	
	public Elemento byId(String id) {
		return new Elemento(webDriver.findElement(By.id(id)));
	}
	
	public Elemento bySelector(String selector) {
		return new Elemento(webDriver.findElement(By.cssSelector(selector)));
	}
	
	public Lista<Elemento> allBySelector(String selector) {
		return Elemento.elementos(webDriver.findElements(By.cssSelector(selector)));
	}
	
	public Lista<Elemento> allByTag(String tag) {
		return Elemento.elementos(webDriver.findElements(By.tagName(tag)));
	}

}
