import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    private long wdwTimeout = 60;

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
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.close();
            driver = null;
        }
    }

    synchronized private void inputIntoLogin(String login, String password) {
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
        driver.findElement(By.xpath("//div[@class='login-row last'//button[@type='submit']")).click();
    }

    private void fillEmailFields() {
        driver.findElement(By.xpath("//a[@class='b-toolbar__btn js-shortcut']/span[text()='Написать письмо']")).click();
        try {
            WebElement destinationElem = new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[contains(@class, compose-head__content)]//div[@class='label-input']" +
                                    "//textarea[@data-original-name='To']")
                    ));
            destinationElem.sendKeys(destination);
            WebElement bodyElem = driver.findElement(By.xpath("//body[contains(@class, 'mceContentBody')]"));
            bodyElem.sendKeys(body);
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
                            By.xpath("//a[@class='b-toolbar__btn js-shortcut']/span[text()='Написать письмо']")
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
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@class='login-panel']//form[@method='POST']//h1[text()='Вход']")
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
            WebElement sendElem = driver.findElement(By.xpath("//span[@class='b-toolbar__btn__text' and text()='Отправить'][1]"));
            sendElem.click();
            new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@class='message-sent__title]")
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
        wait(5);
        WebElement deleteElem = driver.findElement(
                By.xpath("//div[@class='b-toolbar__item']//span[text()='Удалить'][1]")
        );
        deleteElem.click();
        wait(5);
        Assert.assertFalse(checkboxElem.isDisplayed());
    }

    @Test
    public void archiveEmail() throws InterruptedException {
        loginSuccess();
        WebElement checkboxElem = driver.findElement(
                By.xpath("//div[@id='b-letters']/div[@class='b-datalists']//div[contains(@class, 'js-item-checkbox b-datalist__item__cbx')][1]")
        );
        checkboxElem.click();
        wait(5);
        WebElement archiveElem = driver.findElement(
                By.xpath("//div[@class='b-toolbar__item']//span[text()='В архив'][1]")
        );
        archiveElem.click();
        wait(5);
        Assert.assertFalse(checkboxElem.isDisplayed());
    }

    @Test
    public void sendToSpam() throws InterruptedException {
        loginSuccess();
        WebElement checkboxElem = driver.findElement(
                By.xpath("//div[@id='b-letters']/div[@class='b-datalists']//div[contains(@class, 'js-item-checkbox b-datalist__item__cbx')][1]")
        );
        checkboxElem.click();
        wait(5);
        WebElement archiveElem = driver.findElement(
                By.xpath("//div[contains(@class, 'b-toolbar__item')]//span[text()='Спам'][1]")
        );
        archiveElem.click();
        wait(5);
        Assert.assertFalse(checkboxElem.isDisplayed());
    }

    @Test
    public void testClearSpam() throws InterruptedException {
        loginSuccess();
        sendToSpam();
        WebElement clearSpamElem = driver.findElement(
            By.xpath("//a[@href='/messages/spam']//span[text()='очистить']")
        );
        clearSpamElem.click();
        WebElement confirmClearElem = new WebDriverWait(driver, wdwTimeout)
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class, 'balloon_content')]//span[@class='btn__text' and text()='Очистить']")
                ));
        confirmClearElem.click();
        Assert.assertFalse(clearSpamElem.isDisplayed());
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
        try {
            WebElement messageElem = driver.findElement(
                    By.xpath("//div[@id='b-letters']/div[@class='b-datalists']//div[contains(@class, 'b-datalist__item ')][1]")
            );
            messageElem.click();
            WebElement bodyElem = new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@class='composeEditorFrame']")
                    ));
            bodyElem.sendKeys(body);
            driver.findElement(By.xpath("//span[@class='b-toolbar__btn__text' and text()='Отправить'][1]")).click();
            new WebDriverWait(driver, wdwTimeout)
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//div[@class='message-sent__title]")
                    ));
        } catch (TimeoutException e) {
            Assert.fail();
        }
    }

    @Test
    public void testFlagMessage() throws InterruptedException {
        loginSuccess();
        WebElement checkboxElem = driver.findElement(
                By.xpath("//div[@id='b-letters']/div[@class='b-datalists']//div[contains(@class, 'js-item-checkbox b-datalist__item__cbx')][1]")
        );
        checkboxElem.click();
        wait(5);
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
