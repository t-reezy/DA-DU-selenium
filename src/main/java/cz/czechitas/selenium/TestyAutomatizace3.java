package cz.czechitas.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.plaf.TableHeaderUI;
import java.util.concurrent.TimeUnit;

public class TestyAutomatizace3 {

    WebDriver prohlizec;

    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }


    @Test
    public void parentLogin() {
        // účet rodiče: Karel Vomytý, karel.vomyty@gmail.cz, Asdf1234
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/");
         parentLogging();
        WebElement logged = prohlizec.findElement(By.xpath("//*[contains(text(),'Přihlášen')]"));
        Assertions.assertNotNull(logged);
    }

    @Test
    public void courseBookingWithoutPreviousLogin() throws InterruptedException {
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/");
        prohlizec.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div/div[2]/a")).click();
        prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[2]/div/div[2]/a")).click();
        parentLogging();

        prohlizec.findElement(By.xpath("html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/button")).click();
        WebDriverWait wait = new WebDriverWait(prohlizec, 20);
        wait.until(ExpectedConditions.visibilityOf(prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/div"))));
        WebElement termin = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/div/div[1]/input"));
        termin.sendKeys("14.");
        termin.sendKeys(Keys.RETURN);

        prohlizec.findElement(By.id("forename")).sendKeys("Jan");
        prohlizec.findElement(By.id("surname")).sendKeys("Vomytý");
        prohlizec.findElement(By.id("birthday")).sendKeys("01.01.2008");


        WebElement zpusobPlatby = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[8]/td[2]/span[1]/label"));
        zpusobPlatby.click();

        WebElement conditions = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[11]/td[2]/span"));
        conditions.click();


        prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[11]/td[2]/input")).click();
        wait.until(ExpectedConditions.elementToBeClickable(prohlizec.findElement((By.xpath("//a[text() = 'Stáhnout potvrzení o přihlášení']")))));
        Assertions.assertNotNull(prohlizec.findElement(By.xpath("//a[text() = 'Stáhnout potvrzení o přihlášení']")));


    }

    @Test
    public void courseBookingPreviouslyLogged() throws InterruptedException {
        prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/");
        prohlizec.manage().window().maximize();
        parentLogging();
        WebElement newApplication = prohlizec.findElement(By.xpath("//*[text() = 'Vytvořit novou přihlášku']"));
        newApplication.click();
        prohlizec.findElement(By.xpath("/html/body/div/div/div[1]/div[4]/div/div[2]/a")).click();
        prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/div/div[2]/a")).click();
       //vybrat termín
        prohlizec.findElement(By.xpath("html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/button")).click();
        WebDriverWait wait = new WebDriverWait(prohlizec, 20);
        wait.until(ExpectedConditions.visibilityOf(prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/div"))));
        WebElement termin = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/div/div[1]/input"));
        termin.sendKeys("21");
        termin.sendKeys(Keys.RETURN);

        prohlizec.findElement(By.id("forename")).sendKeys("Jan");
        prohlizec.findElement(By.id("surname")).sendKeys("Vomytý");
        prohlizec.findElement(By.id("birthday")).sendKeys("01.01.2008");

        WebElement zpusobPlatby = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[8]/td[2]/span[1]/label"));
        zpusobPlatby.click();

        WebElement conditions = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[11]/td[2]/span"));
        conditions.click();
        Thread.sleep(2000);

        prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[11]/td[2]/input")).click();
        wait.until(ExpectedConditions.elementToBeClickable(prohlizec.findElement((By.xpath("//a[text() = 'Stáhnout potvrzení o přihlášení']")))));
        Assertions.assertNotNull(prohlizec.findElement(By.xpath("//a[text() = 'Stáhnout potvrzení o přihlášení']")));
        }

        @Test
        public void myTestScenario() throws InterruptedException {
        //změna platby u kurzu z Převodem na Hotově
            prohlizec.navigate().to("https://cz-test-jedna.herokuapp.com/");
            parentLogging();
            Thread.sleep(5000);

            prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/div[2]/div/table/tbody/tr[2]/td[5]/div/a[2]")).click();
            WebDriverWait wait = new WebDriverWait(prohlizec, 20);
            Thread.sleep(1000);
            prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[7]/td[2]/span[4]/label")).click();

            prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[10]/td[2]/input")).click();
            Thread.sleep(2000);

            prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/div[2]/div[2]/div/table/tbody/tr[2]/td[5]/div/a[1]")).click();
            String jakPlatit = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/table/tbody/tr[2]/td[2]")).getText();

            Assertions.assertEquals("Hotově", jakPlatit);

        }



    @AfterEach
    public void tearDown() {
        prohlizec.close();
    }

    public void parentLogging() {
        WebElement loginLink = prohlizec.findElement(By.xpath("//a[text()='Přihlásit                ']"));
        loginLink.click();
        WebElement emailForm = prohlizec.findElement(By.id("email"));
        emailForm.sendKeys("karel.vomyty@gmail.cz");
        WebElement passwordFrom = prohlizec.findElement(By.id("password"));
        passwordFrom.sendKeys("Asdf1234");
        WebElement loginButton = prohlizec.findElement(By.xpath("//*[contains(text(),'Přihlásit')]"));
        loginButton.click();
    }



}
