import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class OtusTest {

    WebDriver driver;
    private String login = "ceyogo9446@bitvoo.com";
    private String pas = "Ceyogo9446!";

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        //options.addArguments("headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterEach
    public void close(){
        if (driver != null)
            driver.quit();
    }

    @Test
    public void firstTest(){
//        Открыть https://otus.ru
        driver.get("https://otus.ru");
//        Авторизоваться на сайте
        loginInOtus();
//        Войти в личный кабинет
        enterLK();
//        В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        clearAndEnter(By.id("id_fname"), "Антон");
        clearAndEnter(By.id("id_lname"), "Тест");
        clearAndEnter(By.id("id_fname_latin"), "Anton");
        clearAndEnter(By.id("id_lname_latin"), "Test");
//        Нажать сохранить
        driver.findElement(By.cssSelector("button.button_md-4:nth-child(1)")).submit();
//        Открыть https://otus.ru в “чистом браузере”
        driver.quit();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        //options.addArguments("headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://otus.ru");
//        Авторизоваться на сайте
        loginInOtus();
//        Войти в личный кабинет
        enterLK();
//        Проверить, что в разделе "О себе" отображаются указанные ранее данные
        Assertions.assertEquals("Антон", driver.findElement(By.id("id_fname")).getAttribute("value"));
    }

    private void loginInOtus(){
        driver.findElement(By.cssSelector(".header3__button-sign-in")).click();
        clearAndEnter(By.xpath("//*[@class='new-log-reg__form js-login']/descendant::*[@placeholder='Электронная почта']"), login);
        clearAndEnter(By.xpath("//*[@class='new-log-reg__form js-login']/descendant::*[@placeholder='Введите пароль']"), pas);
        driver.findElement(By.cssSelector("div.new-input-line_last:nth-child(5) > button:nth-child(1)")).submit();
    }

    private void clearAndEnter(By by, String text){
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text,Keys.ENTER);
    }

    private void enterLK(){
        new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".sc-1r3ji37-0.klcrtf"))));
        driver.get("https://otus.ru/lk/biography/personal/");
    }

}
