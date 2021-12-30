package l86.utils.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import estruturas.Lista;
import io.Log;
import utils.Str;

public class Elemento implements WebElement {
	
	private WebElement element;
	
	public static Lista<Elemento> elementos(List<WebElement> webElements) {
		Lista<Elemento> retorno = new Lista<Elemento>();
		for (WebElement element : webElements)
			retorno.add(new Elemento(element));
		return retorno;
	}
	
	public Elemento(WebElement element) {
		this.element = element;
	}
	
	public Elemento pai() {
		return new Elemento(element.findElement(By.xpath("./..")));
		
		/* Poderia ser em Javascript:
		WebElement parent = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].parentNode;", myElement)*/
	}
	
	public Lista<Elemento> filhos() {
		return elementos(element.findElements(By.xpath(".//*")));
	}
	
	public Lista<Elemento> filhosBySelector(String selector) {
		return elementos(element.findElements(By.cssSelector(selector)));
	}
	
	public Elemento filhoBySelector(String selector) {
		WebElement filho = null;
		try {
			filho = element.findElement(By.cssSelector(selector));
		}
		catch(Exception e) {
			Log.msgLn("Elemento não encontrado para selector " + selector);
			return null;
		}
		return new Elemento(filho);
	}
	
	public Str texto() {
		try {
			return new Str(this.element.getAttribute("innerHTML"));
		}
		catch(Throwable t) {
			return new Str();
		}
	}
	
	public Str atributo(String atributo) {
		return new Str(this.element.getAttribute(atributo));
	}
	
	
	// Metodos da interface, repassados para a instancia original WebElement ----------------------------------------------------
	
	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		return element.getScreenshotAs(target);
	}

	@Override
	public void click() {
		element.click();
	}

	@Override
	public void submit() {
		element.submit();
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		element.sendKeys(keysToSend);
	}

	@Override
	public void clear() {
		element.clear();
	}

	@Override
	public String getTagName() {
		return element.getTagName();
	}

	@Override
	public String getAttribute(String name) {
		return element.getAttribute(name);
	}

	@Override
	public boolean isSelected() {
		return element.isSelected();
	}

	@Override
	public boolean isEnabled() {
		return element.isEnabled();
	}

	@Override
	public String getText() {
		return element.getText();
	}

	@Override
	public List<WebElement> findElements(By by) {
		return element.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return element.findElement(by);
	}

	@Override
	public boolean isDisplayed() {
		return element.isDisplayed();
	}

	@Override
	public Point getLocation() {
		return element.getLocation();
	}

	@Override
	public Dimension getSize() {
		return element.getSize();
	}

	@Override
	public Rectangle getRect() {
		return element.getRect();
	}

	@Override
	public String getCssValue(String propertyName) {
		return element.getCssValue(propertyName);
	}

}
