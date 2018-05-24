import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class EmailTest {
    private WebDriver driver;
    private String startingUrl;
    private String login;
    private String password;
    private String invalidPassword;
    private String driverPath;
    private String destination;
    private String body;
    private String configName = "config.properties";

    private long wdwTimeout = 10;

    @Before
    public void setup() {
        Properties prop = new Properties();
        try (InputStream input = EmailTest.class
                .getClassLoader().getResourceAsStream(configName);) {
            if (input == null) {
                System.out.println("no config, error");
                System.exit(1);
            }
            prop.load(input);
            driverPath = prop.getProperty("driverPath");
            startingUrl = prop.getProperty("startingUrl");
            login = prop.getProperty("login");
            password = prop.getProperty("password");
            invalidPassword = prop.getProperty("invalidPassword");
            destination = prop.getProperty("destination");
            body = prop.getProperty("body");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(login + password);
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1920, 1200));
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.close();
            driver = null;
        }
    }

    synchronized private void inputIntoLogin(String login, String password) {
        WebElement iframe = null;
        try {
            iframe = new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//iframe[@class='ag-popup__frame__layout__iframe']")
                    ));
        } catch (TimeoutException e) {
            Assert.fail();
        }
        driver.switchTo().frame(iframe);
        WebElement loginElem = new WebDriverWait(driver, wdwTimeout)
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[@type='email' and @name='Username']")
                ));

        loginElem.clear();
        loginElem.sendKeys(login);


        WebElement passwordElem = driver.findElement(
                By.xpath("//input[@type='password' and @name='Password']"));
        passwordElem.clear();
        passwordElem.sendKeys(password);
        driver.findElement(By.xpath("//div[@class='b-form-field__input']//span[@class='btn__text']")).click();
    }

    private void fillEmailFields() {
        driver.findElement(By.xpath("(//a[@class='b-toolbar__btn js-shortcut']/span[contains(@class, 'b-toolbar__btn__text')])[1]")).click();
        try {
            WebElement destinationElem = new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[contains(@class, compose-head__content)]//div[@class='label-input']" +
                                    "//textarea[@data-original-name='To']")
                    ));
            destinationElem.sendKeys(destination);

            WebElement bodyIframe = driver.findElement(By.xpath("//iframe[contains(@id, 'composeEditor')]"));
            driver.switchTo().frame(bodyIframe);
            WebElement bodyElem = driver.findElement(By.xpath("//body[contains(@class, 'mceContentBody')]"));
            bodyElem.sendKeys(body);
            driver.switchTo().defaultContent();
        } catch (TimeoutException e) {

        }
    }

    @Test
    public void loginSuccess() throws InterruptedException {
        driver.get(startingUrl);
        inputIntoLogin(login, password);
        try {
            WebElement elem = new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//a[@class='b-toolbar__btn js-shortcut']/span[contains(@class, 'b-toolbar__btn__text')]")
                    ));
        } catch (TimeoutException e) {
            Assert.fail();
        }
    }

    @Test
    public void loginInvalidPairLoginPassword() throws InterruptedException {
        driver.get(startingUrl);
        inputIntoLogin(login, invalidPassword);
        try {
            new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(@class, 'b-login')]/div[@class='b-login__header']")
                    ));
        } catch (TimeoutException e) {
            Assert.fail();
        }
    }

    @Test
    public void sendEmail() throws InterruptedException {
        loginSuccess();
        fillEmailFields();
        try {
//            try {
//                Thread.sleep(200000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            WebElement sendElem = driver.findElement(By.xpath("//div[@data-name='send']/span[@class='b-toolbar__btn__text']"));
            sendElem.click();
            new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@class='message-sent__title']")
                    ));
        } catch (TimeoutException e) {
            Assert.fail();
        }
    }

    @Test
    public void deleteEmail() throws InterruptedException {
        loginSuccess();
        WebElement checkboxElem = driver.findElement(
                By.xpath("//div[@id='b-letters']/div[@class='b-datalists']//div[contains(@class, 'js-item-checkbox b-datalist__item__cbx')][1]")
        );
        checkboxElem.click();
        Thread.sleep(5000);
        WebElement deleteElem = driver.findElement(
                By.xpath("(//div[@data-name='remove']/span[contains(@class, 'b-toolbar__btn__text')])[1]")
        );
        deleteElem.click();
        Thread.sleep(5000);
        try {
            checkboxElem.isDisplayed();
        } catch (StaleElementReferenceException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void archiveEmail() throws InterruptedException {
        loginSuccess();
        WebElement checkboxElem = driver.findElement(
                By.xpath("(//div[@id='b-letters']/div[@class='b-datalists']//div[contains(@class, 'js-item-checkbox b-datalist__item__cbx')])[1]")
        );
        checkboxElem.click();
        Thread.sleep(5000);
        WebElement archiveElem = driver.findElement(
                By.xpath("(//div[@data-name='archive']//span[contains(@class, 'b-toolbar__btn__text')])[1]")
        );
        archiveElem.click();
        Thread.sleep(5000);
        try {
            checkboxElem.isDisplayed();
        } catch (StaleElementReferenceException e) {
            return;
        }
        Assert.fail();
    }

    public void sendToSpam() throws InterruptedException {
        WebElement checkboxElem = driver.findElement(
                By.xpath("//div[@id='b-letters']/div[@class='b-datalists']//div[contains(@class, 'js-item-checkbox b-datalist__item__cbx')][1]")
        );
        checkboxElem.click();
        Thread.sleep(5000);
        WebElement archiveElem = driver.findElement(
                By.xpath("(//div[@data-name='spam']//span[contains(@class, 'b-toolbar__btn__text')])[1]")
        );
        archiveElem.click();
        WebElement approveElem = null;
        try {
            approveElem = new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//button[@data-id='approve']/span[@class='btn__text']")
                    ));
        } catch (TimeoutException e) {

        }
        if (approveElem != null) {
            approveElem.click();
        }
        Thread.sleep(5000);
        try {
            checkboxElem.isDisplayed();
        } catch (StaleElementReferenceException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSendToSpam() throws InterruptedException {
        loginSuccess();
        sendToSpam();
    }

    private void clearSpam() throws InterruptedException {
        WebElement clearSpamElem = driver.findElement(
                By.xpath("//a[@href='/messages/spam/']/span[@data-name='clear-folder']")
        );
        clearSpamElem.click();

        WebElement confirmClearElem = new WebDriverWait(driver, wdwTimeout)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='balloon__message']//span[@class='btn__text']")
                ));
        confirmClearElem.click();
        Thread.sleep(5000);
    }
                                                                                            
    @Test
    public void testClearSpam() throws InterruptedException {
        loginSuccess();
        sendToSpam();
        clearSpam();
        try {
            new WebDriverWait(driver, wdwTimeout).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/messages/spam/']/span[@data-name='clear-folder']")));
        } catch (Exception e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSaveToDraft() throws InterruptedException {
        loginSuccess();
        fillEmailFields();
        try {
            WebElement saveElem = driver.findElement(
                    By.xpath("//div[@class='b-toolbar__item']//div[@data-name='saveDraft']" +
                            "/span[@class='b-toolbar__btn__text']")
            );
            saveElem.click();
            new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@class='b-toolbar__item']/div[@class='b-toolbar__message' and @data-mnemo='saveStatus' " +
                                    "and @style != 'display:none;'][1]")
                    ));
        } catch (TimeoutException e) {
            Assert.fail();
        }
    }

    @Test
    public void testReply() throws InterruptedException {
        loginSuccess();
        WebElement messageElem = driver.findElement(
                By.xpath("//div[@id='b-letters']/div[@class='b-datalists']//div[contains(@class, 'b-datalist__item ')][1]")
        );
        messageElem.click();
        WebElement openBodyElem = new WebDriverWait(driver, wdwTimeout)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[@data-compose-act='reply']")
                ));
        openBodyElem.click();
        WebElement bodyIframe =
                new WebDriverWait(driver, wdwTimeout)
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//iframe[contains(@id, 'composeEditor')]")
                        ));

        driver.switchTo().frame(bodyIframe);
        WebElement bodyElem = driver.findElement(By.xpath("//body[contains(@class, 'mceContentBody')]"));
        bodyElem.sendKeys(body);
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//div[@data-name='send']/span[contains(@class, 'b-toolbar__btn__text')]")).click();
        new WebDriverWait(driver, wdwTimeout)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@class='message-sent__title']")
                ));
    }

    @Test
    public void testFlagMessage() throws InterruptedException {
        loginSuccess();
        WebElement checkboxElem = driver.findElement(
                By.xpath("//div[@id='b-letters']/div[@class='b-datalists']//div[contains(@class, 'js-item-checkbox b-datalist__item__cbx')][1]")
        );
        checkboxElem.click();
        Thread.sleep(5000);
        try {
            new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@class='b-datalist__item__flag']/div[contains(@class,'b-flag_yes')]")
                    ));
        } catch (TimeoutException e) {
            Assert.fail();
        }
    }

    @Test
    public void testLogout() throws InterruptedException {
        loginSuccess();
        WebElement logoutElem = driver.findElement(
                By.xpath("(//table[@class='x-ph__auth']//a[contains(@class, 'x-ph__link')])[1]")
        );
        logoutElem.click();
        try {
            new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@id='mailbox']")
                    ));
        } catch (TimeoutException e) {
            Assert.fail();
        }
    }
}
