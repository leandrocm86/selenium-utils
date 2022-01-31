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
	
	/**
	 * Busca todos os filhos do elemento.
	 */
	public Lista<Elemento> filhos() {
		return elementos(element.findElements(By.xpath(".//*")));
	}
	
	/**
	 * Busca os filhos (descendentes diretos) do elemento que casem com um dado seletor.
	 */
	public Lista<Elemento> filhosBySelector(String selector) {
		return elementos(element.findElements(By.cssSelector(selector)));
	}
	
	/**
	 * Busca todos os descendentes do elemento que casem com um dado seletor.
	 */
	public Lista<Elemento> descendentesBySelector(String selector) {
		Lista<Elemento> descendentes = new Lista<Elemento>();
		descendentes.addAll(filhosBySelector(selector));
		Lista<Elemento> filhos = filhos();
		for (Elemento filho : filhos) {
			descendentes.addAll(filho.descendentesBySelector(selector));
		}
		return descendentes;
	}
	
	/**
	 * Busca o filho (descendente direto) que case com dado seletor.
	 * Se houver mais de um, retorna o primeiro. Se não houver nenhum, retorna nulo.
	 */
	public Elemento filhoBySelector(String selector) {
		WebElement filho = null;
		try {
			filho = element.findElement(By.cssSelector(selector));
		}
		catch(Exception e) {
			return null;
		}
		return new Elemento(filho);
	}
	
	/**
	 * Busca os filhos (descendentes diretos) que contenham um dado texto.
	 */
	public Lista<Elemento> filhosByText(CharSequence text) {
		return elementos(element.findElements(By.xpath("//*[contains(text(), '" + text + "')]")));
	}
	
	public Str texto() {
		try {
			return new Str(this.element.getAttribute("innerHTML"));
		}
		catch(Throwable t) {
			return new Str();
		}
	}
	
	public Str textoSemTags() {
		Str texto = texto();
		while (true) {
			int inicioTag = texto.indexOf("<");
			int fimTag = texto.indexOf(">");
			if (inicioTag == -1 || fimTag == -1)
				break;
			fimTag += 1;
			texto.val(texto.substring(0, inicioTag) + (fimTag < texto.length() ? texto.substring(fimTag) : ""));
		}
		return texto;
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
