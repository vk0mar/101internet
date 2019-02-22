import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test101 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "bin\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //неявное
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void test101() {
        List<WebElement> elements = null;
        boolean allLinkCorrect = true;
        Helper.pageListInit();

        for (int i = 0; i < Helper.pageList.size(); i++) {
            driver.get(Helper.pageList.get(i));
            elements = driver.findElements(By.cssSelector(Helper.itemsLink));
            System.out.println("LOG for page [" + elements.size() + "] " + Helper.pageList.get(i));
            allLinkCorrect &= Helper.urlMatching(elements);
        }
        Assert.assertTrue(allLinkCorrect); // на первых 5 страницах все ссылки корректны

        //выбрать 3й во второй строке товар на 3й странице выдачи (3_2_3)
        driver.get(Helper.pageList.get(2));
        List<WebElement> elements3 = driver.findElements(By.cssSelector(Helper.itemsLink));
        String href = elements3.get(5).getAttribute("href"); //ссылка на 3й во второй строке товар
        int asinIndex = href.indexOf(Helper.delim) + 4;

        // asin - из ссылки на страницу
        String asin = href.substring(asinIndex, asinIndex + 10);

        //e_asin - из карточки товара
        driver.get(href);
        WebElement e_asin = driver.findElement(By.cssSelector(Helper.asinSelector));
        Assert.assertEquals(asin, e_asin.getText().substring(6, 16));

        // e_date - дата товара
        WebElement e_date = driver.findElement(By.cssSelector(Helper.itemDateSelector));
        Date dateContent = new Date(e_date.getText().substring(e_date.getText().indexOf(':') + 2));
        Assert.assertTrue(dateContent.getTime() < (new Date()).getTime());

        //3. Страница не содержит умляут Ö.
        Assert.assertFalse(driver.getPageSource().indexOf(Helper.umlayt) > 0);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
