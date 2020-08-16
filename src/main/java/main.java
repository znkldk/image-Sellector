import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.ResourceBundle;
public class main {
    public int referanceX;
    public int referanceY;
    public static WebDriver driver;
    public static String baseUrl = "https://testinium.com/";


    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(baseUrl);

        senaryo Senaryo= new senaryo();
        Senaryo.basicSenaryo();
    }

}
