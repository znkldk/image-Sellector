import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;

public class methods {
    public float referanceX;
    public float referanceY;
    WebDriver driver= main.driver;
    public void takeScreenShot() throws IOException, InterruptedException {
        Thread.sleep(2000);
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("Screenshot.png"));
    }
    public void clickOnImage(String img) throws Exception {

        Actions actions = new Actions(driver);
        String [] coordinats=imageSelector.getImgCoordinats(img);
        System.out.println(coordinats);
        System.out.println("2referance numbers= x= "+referanceX+"referance numbers= Y= "+referanceY);
        int X=(int) Math.round(Integer.parseInt(coordinats[0])/referanceX);
        int Y=(int) Math.round(Integer.parseInt(coordinats[1])/referanceY);
        System.out.println("Tıklanan kordinatlar x="+X+" Y= "+ Y);

        ((JavascriptExecutor) driver).executeScript("el = document.elementFromPoint("+X+", "+Y+"); el.click();");


    }
    public int[] getCoordinates() throws Exception {
        //Locate element for which you wants to retrieve x y coordinates.
        WebElement Image = main.driver.findElement(By.xpath("//*[@class=\"nav-item navbar-nav__navbar-item mx-md-1\"]//*[.=\"AUTOMATED\"]"));
        //Used points class to get x and y coordinates of element.
        Point classname = Image.getLocation();
        int xcordi = classname.getX();
       // System.out.println("Element's Position from left side"+xcordi +" pixels.");
        int ycordi = classname.getY();
       // System.out.println("Element's Position from top"+ycordi +" pixels.");
        Dimension dimension=Image.getSize();

        int[] coordinats;
        coordinats = new int [2];
        coordinats[0]=xcordi+(dimension.width/2);
        coordinats[1]=ycordi+(dimension.height/2);

        return coordinats;
    }


    public  void setUpreferanceNumbers(String ref) throws Exception {
        String [] imageCoordinats=imageSelector.getImgCoordinats(ref);
        int [] elementCoordinats=getCoordinates();
        referanceX=(float)Integer.parseInt(imageCoordinats[0])/(float)elementCoordinats[0];
        referanceY=(float)Integer.parseInt(imageCoordinats[1])/(float)elementCoordinats[1];
        System.out.println("referance numbers= x= "+referanceX+"referance numbers= Y= "+referanceY);
    }

}


