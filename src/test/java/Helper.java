import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public static final String itemsLink = "div.s-item-container  a.a-link-normal.s-access-detail-page.s-color-twister-title-link.a-text-normal";
    public static final String asinSelector = "div.content li:nth-child(1)";
    public static final String itemDateSelector = "div.content li:nth-child(2)";
    public static ArrayList<String> pageList = new ArrayList<String>();
    public static final char umlayt ='Ö';
    public static final String delim = "/dp/";


    public static void pageListInit() {
        pageList.add("https://www.amazon.com.au/b/?node=5581881051&bbn=5581859051");
        pageList.add("https://www.amazon.com.au/b/?node=5581881051&bbn=5581859051&page=2");
        pageList.add("https://www.amazon.com.au/b/?node=5581881051&bbn=5581859051&page=3");
        pageList.add("https://www.amazon.com.au/b/?node=5581881051&bbn=5581859051&page=4");
        pageList.add("https://www.amazon.com.au/b/?node=5581881051&bbn=5581859051&page=5");
    }

    public static boolean urlMatching(List<WebElement> elements) {

        boolean resPage = true;
        boolean resUrl = true;

        Pattern pt = Pattern.compile("(https://www.amazon.com.au/)(.*)(/dp/)([A-Z 0-9]{10})(/.*)");
        for (WebElement e : elements) {
            Matcher m = pt.matcher(e.getAttribute("href"));
            if (!m.matches()) {
                resPage = false;
                resUrl = false;
            } else {
                resUrl = true;
            }

            System.out.println("LOG for link " + resUrl + " " + e.getAttribute("href"));
        }
        return resPage;
    }

}
