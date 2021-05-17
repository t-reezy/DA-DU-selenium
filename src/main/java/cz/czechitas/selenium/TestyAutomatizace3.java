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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestyAutomatizace3 {

    WebDriver prohlizec;
    private static final String APP_URL = "https://cz-test-dva.herokuapp.com/";

    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }


    @Test
    public void parentLogin() {
        prohlizec.navigate().to(APP_URL);
        logParent("karel.vomyty@gmail.cz", "Asdf1234");
        WebElement logged = prohlizec.findElement(By.xpath("//*[contains(text(),'Přihlášen')]"));
        Assertions.assertNotNull(logged);
    }

    @Test
    public void courseBookingWithoutPreviousLogin() {
        prohlizec.navigate().to(APP_URL);
        chooseCourseDAoop();
        logParent("karel.vomyty@gmail.cz", "Asdf1234");
        fillAndSubmitEnrolment("14.", "Jan", "Vomytý", "01.01.2008");
    }

    @Test
    public void courseBookingPreviouslyLogged() {
        prohlizec.navigate().to(APP_URL);
        prohlizec.manage().window().maximize();
        logParent("karel.vomyty@gmail.cz", "Asdf1234");
        makeNewEnrolmentWhenLogged();
        chooseCourseHTML1();
        fillAndSubmitEnrolment("21.", "Jan", "Vomytý", "01.01.2008");
    }


    @Test
    public void myTestScenario() throws InterruptedException {
        //změna platby u predposledniho vypsaneho kurzu na 'Hotově'
        prohlizec.navigate().to(APP_URL);
        logParent("karel.vomyty@gmail.cz", "Asdf1234");
        List<WebElement> enrolmentEdits = prohlizec.findElements(By.className("qa-edit-button"));
        int courseNo = enrolmentEdits.size() - 2;
        WebElement chosenCourse = enrolmentEdits.get(courseNo);
        chosenCourse.click();
        choosePaymentMethodCash();
        submitEnrolment();
        List<WebElement> enrolmentDetails = prohlizec.findElements(By.className("qa-detail-button"));
        WebElement chosenDetail = enrolmentDetails.get(courseNo);
        chosenDetail.click();
        String methodOfPayment = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/table/tbody/tr[2]/td[2]")).getText();
        Assertions.assertEquals("Hotově", methodOfPayment);

    }


    @AfterEach
    public void tearDown() {
        prohlizec.close();
    }

    //-----------------------------------------------------------------------------------------

    public void logParent(String parentEmail, String parentPassword) {
        WebElement loginLink = prohlizec.findElement(By.className("qa-login-button"));
        loginLink.click();
        WebElement emailForm = prohlizec.findElement(By.id("email"));
        emailForm.sendKeys(parentEmail);
        WebElement passwordFrom = prohlizec.findElement(By.id("password"));
        passwordFrom.sendKeys(parentPassword);
        WebElement loginButton = prohlizec.findElement(By.xpath("//button[contains(text(),'Přihlásit')]"));
        loginButton.click();
    }

    public void makeNewEnrolmentWhenLogged() {
        WebElement newEnrolment = prohlizec.findElement(By.xpath("//*[text() = 'Vytvořit novou přihlášku']"));
        newEnrolment.click();
    }

    public void chooseCourseHTML1() {
        WebElement webCourse = prohlizec.findElement(By.xpath("/html/body/div/div/div[1]/div[4]/div/div[2]/a"));
        webCourse.click();
        WebElement HTML1Course = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/div/div[2]/a"));
        HTML1Course.click();
    }

    public void chooseCourseDAoop() {
        WebElement digiAcademy = prohlizec.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div/div[2]/a"));
        digiAcademy.click();
        WebElement oop = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div[2]/div/div[2]/a"));
        oop.click();
    }

    public void fillAndSubmitEnrolment(String partialCourseDate, String kidName, String kidSurname, String kidBirthday) {
        chooseCourseDate(partialCourseDate);
        fillKidName(kidName);
        fillKidSurname(kidSurname + System.currentTimeMillis());
        fillKidBirthday(kidBirthday);
        choosePaymentMethodBankTransfer();
        agreeWithConditions();
        submitEnrolment();
        checkEnrolment();
    }

    public void chooseCourseDate(String partialDate) {
        WebElement dropdownDate = prohlizec.findElement(By.xpath("html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/button"));
        dropdownDate.click();
        WebDriverWait wait = new WebDriverWait(prohlizec, 20);
        wait.until(ExpectedConditions.visibilityOf(prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/div"))));
        WebElement courseDate = prohlizec.findElement(By.xpath("/html/body/div/div/div/div/div/form/table/tbody/tr[2]/td[2]/div/div/div[1]/input"));
        courseDate.sendKeys(partialDate);
        courseDate.sendKeys(Keys.RETURN);
    }

    public void fillKidBirthday(String birthdayDate) {
        WebElement birthdayForm = prohlizec.findElement(By.id("birthday"));
        birthdayForm.sendKeys(birthdayDate);
    }

    public void fillKidSurname(String surname) {
        WebElement surnameForm = prohlizec.findElement(By.id("surname"));
        surnameForm.sendKeys(surname);
    }

    public void fillKidName(String name) {

        WebElement forenameForm = prohlizec.findElement(By.id("forename"));
        forenameForm.sendKeys(name);
    }

    public void choosePaymentMethodBankTransfer() {
        WebElement bankTransfer = prohlizec.findElement(By.xpath("//label[contains(@for, 'payment_transfer')]"));
        bankTransfer.click();
    }

    public void choosePaymentMethodCash() {
        WebElement paymentMethodCash = prohlizec.findElement(By.xpath("//label[contains(@for, 'payment_cash')]"));
        paymentMethodCash.click();
    }

    public void agreeWithConditions() {
        WebElement conditions = prohlizec.findElement(By.xpath("//label[contains(@for, terms_conditions)]"));
        conditions.click();
    }

    public void submitEnrolment() {
        WebElement submitButton = prohlizec.findElement(By.className("qa-submit-button"));
        submitButton.click();
    }

    public void checkEnrolment() {
        WebDriverWait wait = new WebDriverWait(prohlizec, 20);
        wait.until(ExpectedConditions.elementToBeClickable(prohlizec.findElement(By.className("qa-confirmation-certificate-button"))));
        Assertions.assertNotNull(prohlizec.findElement(By.className("qa-confirmation-certificate-button")));
    }


}
