package yandexDisk.pages;

import yandexDisk.utils.WebDriverSingleton;
import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AbstractPage {

    private static final int DEFAULT_TIMEOUT = 20;
    protected WebDriver driver;

    protected AbstractPage() {
        this.driver = WebDriverSingleton.getWebDriverInstance();
    }

    protected boolean isElementVisible(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected void waitForElementPresent(By locator) {
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitForElementVisible(By locator) {
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForElementEnabled(By locator) {
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void highlightElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid green'", driver.findElement(locator));
    }

    protected void clickDiscCrumbs() {
        ((JavascriptExecutor) driver).executeScript("document.getElementById('/disk').click()");
    }

    protected void unHighlightElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='0px'", driver.findElement(locator));
    }

    protected void pageReload() {
        driver.navigate().refresh();
    }

    public void waitForFileDownload(final File uploadFile) {
        FluentWait wait = new FluentWait<File>(uploadFile).withTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        wait.until(new Function<File, Boolean>() {
            @Override
            public Boolean apply(File file) {
                return file.exists();
            }
        });
    }
}