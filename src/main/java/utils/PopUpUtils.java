package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import com.google.common.base.Function;

/** * @author mitchellw 
    *
    * Looks for and clicks any WebElements in the Native or Webview contexts 
    * Useful for a quick check to exit any popups displayed on screen 
    * 
    * Example Usage (Will remove any Chrome popups seen after browser is cleaned):
    * new PopUpUtils(driver).addNativePopupBtns(
        By.xpath("//*[contains(@class,'Button') and (contains(@text,'Accept') or contains(@text, 'OK'))]"),
        By.xpath("//*[contains(@class,'Button') and (contains(@text,'No') or contains(@text,'Next'))]"),
        By.xpath("//*[contains(@class,'Button') and (contains(@text,'Allow') or contains(@text,'No'))]"))
        .clickMulitplePopUps(3); 
    * 
    * Click on a No link     
    * new PopUpUtils(driver).addWebViewPopupBtns(By.linkText("No")).clickOnPopUpIfFound();
 */
public class PopUpUtils { 
    public enum Context {
        NATIVE_APP,
        WEBVIEW,
        VISUAL
     } 
    HashMap<By, Context> popupBtns; 
    RemoteWebDriver driver; 
    int fluentTimeout = 1; 
    int implicitTimeout = 30; 
    int pageTimeout = 30; 
    Wait<RemoteWebDriver> wait; 
    /**
      * Set existing implicit and pageTimeout waits
      * waits will be reset to these after looking for popups
      * @param driver 
      * @param implicitTimeout
      * @param pageTimeout
     */ 
    public PopUpUtils(RemoteWebDriver driver, int implicitTimeout, int pageTimeout)
    {
        this(driver);
        this.implicitTimeout = implicitTimeout;
        this.pageTimeout = pageTimeout;
    } 
    public PopUpUtils(RemoteWebDriver driver) {
        this.driver = driver;
        popupBtns = new HashMap<By, Context>();
        wait = new FluentWait<RemoteWebDriver>(driver)
                .withTimeout( fluentTimeout, TimeUnit.SECONDS )
                .pollingEvery( 250, TimeUnit.MILLISECONDS )
                .ignoring( NoSuchElementException.class, StaleElementReferenceException.class );
     }
     public HashMap<By, Context> getPopupBtns() {
        return popupBtns; 
     }
     public void setPopupBtns(HashMap<By, Context> popupBtns) {
        this.popupBtns = popupBtns;
     } 
    /**
      * Add By locators with a Native context
      * @param locators
      * @return
      */
     public PopUpUtils addNativePopupBtns(By... locators) {
        for (By btn : locators) {
            popupBtns.put(btn, Context.NATIVE_APP);
        };
        return this;
     }
     /**
      * Add By locators with a Webview context
      * @param locators
      * @return
      */
     public PopUpUtils addWebViewPopupBtns(By... locators) {
        for (By btn : locators) {
            popupBtns.put(btn, Context.WEBVIEW);
        };
        return this;
     }
     public PopUpUtils addToPopupBtns(By by, Context context) {
        popupBtns.put(by, context);
        return this;
     }
     /**
      * Set fluent wait timeout (default is 1 second)
      * @param timeout
      */
     public void setTimeout(int timeout) {
        this.fluentTimeout = timeout;
     }
     /**
      * Look through map of locators and click on first one found  
      */
     public boolean clickOnPopUpIfFound() {
        driver.manage().timeouts().pageLoadTimeout(0, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        Context previousCtx = getCurrentContextHandle();
        try {
            WebElement popup = wait.until(new Function<RemoteWebDriver, WebElement>() {
                public WebElement apply(RemoteWebDriver driver) {
                    return findPopupBtns();
                }
            });
            if (popup != null) {
                popup.click();
                return true;
            }
        } catch (Exception e) {
        } finally {
            switchToContext(previousCtx);
            driver.manage().timeouts().pageLoadTimeout(pageTimeout, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
        }
        return false;
     }
     /**
      * Click on any element found in popup btn map and repeat expectedClicks times
      * @param expectedClicks
      */
     public void clickMulitplePopUps(int expectedClicks) {
        for (int i = 0; i < expectedClicks; i++) {
            clickOnPopUpIfFound();
        }
     }
     private WebElement findPopupBtns() {
        Context currentCtx = getCurrentContextHandle();
        for (Entry<By, Context> locator : popupBtns.entrySet()) {
            try {
                if (currentCtx != locator.getValue()) {
                    switchToContext(locator.getValue());
                    currentCtx = locator.getValue();
                }
                return driver.findElement(locator.getKey());
            } catch (Exception e) {
            }
        }return null;
     }
     /**
      * Switch the {@link RemoteWebDriver} driver context.
      * A list with possible contexts can be generated by the {@link #getCurrentContextHandle(RemoteWebDriver)} method.
      * To check the current context, use the {@link #getCurrentContextHandle(RemoteWebDriver)} method.
      * @param driver The context of the passed driver will be changed
      * @param context The context to change to
      */
     public void switchToContext(Context context) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", context.name());
        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
     }
     /**
      * Gets the {@link String} value of the current context of the driver.
      * In order to change the current context, use the {@link #switchToContext(RemoteWebDriver, String)} method.
      * @param driver The driver to get the context from.
      * @return {@link String} value of the current context.
      */
     public Context getCurrentContextHandle() {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        String context =  (String) executeMethod.execute (DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
        if (context.toLowerCase().contains("webview")) return Context.WEBVIEW;
        else if (context.toLowerCase().contains("native")) return Context.NATIVE_APP;
        else if (context.toLowerCase().contains("visual")) return Context.VISUAL;
        return Context.WEBVIEW;
     }
}