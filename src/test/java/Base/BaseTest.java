package Base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
    }

    @Test
    public void TitleTest(){
        System.out.println( driver.getTitle());
    }

    @Test
    public void RadioButtontest(){
        Reporter.log("testing for RadioButton");
        WebElement radioEle=driver.findElement(By.cssSelector("input[value='radio1']"));
        radioEle.click();
    }

    @Test
    public void autoSuggest(){
        Reporter.log("testing for AutoSuggest");

       WebElement box=driver.findElement(By.id("autocomplete"));
       box.sendKeys("Ind");
       driver.findElement(By.xpath("//ul[@id='ui-id-1']/li[2]")).click();


       List<WebElement> op=driver.findElements(By.xpath("//ul[@id='ui-id-1']/li"));
       op.forEach(e->{
           if(e.getText().equalsIgnoreCase("India"))
               e.click();
       });

    }

   @Test
    public void SelectTest(){
        WebElement sl=driver.findElement(By.id("dropdown-class-example"));
        Select select=new Select(sl);
        select.selectByValue("option2");
    }


    @Test
    public void chckBoxTest(){
       WebElement firstbox= driver.findElement(By.xpath("//input[@id='checkBoxOption1']"));
       firstbox.click();
       Assert.assertTrue(firstbox.isSelected());
       List<WebElement> l=driver.findElements(By.cssSelector("#checkbox-example input[type='checkbox']"));
       l.stream().forEach(e->e.click());
        System.out.println(l.size());
        Assert.assertFalse(firstbox.isSelected());
    }

    @Test
    public void windowTabhandle(){
        driver.findElement(By.cssSelector("#openwindow")).click();
        driver.findElement(By.cssSelector("#opentab")).click();
        String parent=driver.getWindowHandle();
        Set<String>windows=driver.getWindowHandles();
        for(String e:windows){
            if(!e.equals(parent)){
                driver.switchTo().window(e);
                System.out.println("Child->"+driver.getTitle());
                driver.close();
            }
        }
        driver.switchTo().window(parent);

        System.out.println("Parent-->"+ driver.getTitle());

    }

    @Test
    public void Alerthandle(){
        driver.findElement(By.cssSelector("#name")).sendKeys("Anomitro");
        driver.findElement(By.cssSelector("#alertbtn")).click();
       String s= driver.switchTo().alert().getText();
        System.out.println("Alert->"+s);
        driver.switchTo().alert().accept();

        driver.findElement(By.cssSelector("#name")).sendKeys("Anomitro");
        driver.findElement(By.cssSelector("#confirmbtn")).click();

        String w= driver.switchTo().alert().getText();
        System.out.println("2nd Alert->"+w);
        driver.switchTo().alert().dismiss();
    }


  /* //Explicit wait
    public void pracWait(){
        WebDriverWait wait=new WebDriverWait(driver,5);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
    }
*/


    @Test
    public void handleIfRame(){

        WebElement element=driver.findElement(By.id("courses-iframe"));
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",element);

        driver.switchTo().frame("courses-iframe");
        WebElement joinNow=driver.findElement(By.cssSelector(".btn.btn-theme.btn-sm.btn-min-block"));
        js.executeScript("arguments[0].scrollIntoView(true);",joinNow);
        joinNow.click();
    }




    @AfterMethod
    public void teardown(){
       driver.quit();
    }

}
