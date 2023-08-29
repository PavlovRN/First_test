import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import java.io.IOException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static java.time.Duration.*;
import static java.time.Duration.ofSeconds;

public class Test_FirstTest {
    private WebDriver driver = null;
    private Logger logger = LogManager.getLogger(Test_FirstTest.class);

    By mainMenuButton = By.cssSelector(".sc-mrx253-0");
    By inputEmail = By.cssSelector("[name='email']");
    By inputPassword = By.cssSelector("[type='password']");
    By confirmationButton = By.cssSelector(".sc-9a4spb-0.gYNtqF.sc-11ptd2v-2-Component.cElCrZ");
    By name = By.cssSelector("#id_fname");
    By nameLatin = By.cssSelector("#id_fname_latin");
    By lName = By.cssSelector("#id_lname");
    By lNameLatin = By.cssSelector("#id_lname_latin");
    By blockName = By.cssSelector("#id_blog_name");
    By selectCountry = By.cssSelector(".input_full.lk-cv-block__input");
    By countryBelarus = By.cssSelector(".lk-cv-block__select-option:nth-child(3)");
    By selectCity = By.cssSelector(".js-lk-cv-dependent-slave-city.js-lk-cv-custom-select");
    By cityBobruisk = By.cssSelector(".lk-cv-block__select-scroll_city.js-custom-select-options :nth-child(2)");
    By englishLevel = By.xpath("//input[@name='english_level']/following-sibling::div");
    By beginnerEnglishLevel = By.cssSelector("button[title='Начальный уровень (Beginner)']");
    By dateOfBirth = By.cssSelector("[name='date_of_birth']");
    By readyToRelocateNo = By.cssSelector("#id_ready_to_relocate_0");
    By workFormat = By.cssSelector("input[value = 'full']");
    By HabrCommunication = By.xpath("//input[@name='contact-1-preferable']/following::span");
    By addContactButton = By.cssSelector(".lk-cv-block__action.lk-cv-block__action_md-no-spacing.js-formset-add");
    By addFirstContact = By.xpath("//input[@name='contact-1-service']/following-sibling::div");
    By addContactHabr = By.cssSelector("div[data-num='1'] button:nth-child(9)");
    By addContactHabrSend= By.cssSelector("input[name='contact-1-value']");
    By habrContact= By.cssSelector("input[name='contact-0-value']");
    By gender = By.cssSelector("#id_gender");
    By genderM = By.cssSelector("[value='m']");
    By company = By.cssSelector("#id_company");
    By work =By.cssSelector("#id_work");
    By saveAndContinueButton = By.cssSelector("button.button_md-4:nth-child(1)");

    private  int i;
    String DeleteContact = "div[data-num='"+i+"'] .container__col_md-0 .js-formset-delete";

    @BeforeAll
    public static void webDriverSutup() {
        WebDriverManager.chromedriver().setup();

    }

    @AfterEach
    public void closeAll() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void otus_about_me() throws IOException {

        System.getProperties().load(ClassLoader.getSystemResourceAsStream("prop.properties"));

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.get(System.getProperty("url_otus"));

        //Авторизоваться на сайте

        loginOTUS();

        //Вход в личный кабинет

        enterLK();

        completion(name, "Георгий");
        completion(nameLatin, "Georgiy");
        completion(lName, "Леопардов");
        completion(lNameLatin, "Leopardoff");
        completion(blockName, "Жорик");

        driver.findElement(dateOfBirth).clear();
        driver.findElement(dateOfBirth).sendKeys("14.11.2005",Keys.ENTER);

        //Выбор страны

        driver.findElement(selectCountry).click();
        WebDriverWait wait = new WebDriverWait(driver,15);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(countryBelarus));
        driver.findElement(countryBelarus).click();

       //Выбор города

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selectCity));
        driver.findElement(selectCity).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cityBobruisk));
        driver.findElement(cityBobruisk).click();

        //Выбор уровня языка

        driver.findElement(englishLevel).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(beginnerEnglishLevel));
        driver.findElement(beginnerEnglishLevel).click();

        //готовность к переезду

        if (driver.findElement(readyToRelocateNo).isSelected()){

        }else {
            driver.findElement(readyToRelocateNo).click();
        }

       // Формат работы

        if (driver.findElement(workFormat).isSelected()){

        }else {
            driver.findElement(workFormat).click();
        }

        //Проверка удаления контактов

        for (int i = 0;i <= 10; i++){

            if (driver.findElement(By.cssSelector(DeleteContact)).isDisplayed()){
                driver.findElement(By.cssSelector(DeleteContact)).click();

            }

        }
       // Добавление контактов

        driver.findElement(addContactButton).click();
        driver.findElement(addFirstContact).click();
        driver.findElement(addContactHabr).click();
        driver.findElement(addContactHabrSend).sendKeys("https://www.hubr.com/kreedmusic");

        //Предпочтительный способ связи

        if (driver.findElement(HabrCommunication).isSelected()){

        }else {
            driver.findElement(HabrCommunication).click();
        }

        //Выбор пола

        driver.findElement(gender).click();
        driver.findElement(genderM).click();

        //Компания и должность

        completion(company, "ООО Фелион");
        completion(work, "Кладовщик");

        //Сохранение изменений

          driver.findElement(saveAndContinueButton).click();

          //Открытие "чистого" браузера
        driver.quit();
        driver = new ChromeDriver(options);
        driver.get(System.getProperty("url_otus"));
        loginOTUS();
        enterLK();

        //Проверки текстовых полей

        assertField(name, "Георгий");
        assertField(nameLatin, "Georgiy");
        assertField(lName, "Леопардов");
        assertField(lNameLatin, "Leopardoff");
        assertField(blockName, "Жорик");
        assertField(work, "Кладовщик");
        assertField(dateOfBirth,"14.11.2005");
        assertField(company, "ООО Фелион");
        assertList(countryBelarus, "Республика Беларусь");
        assertList(cityBobruisk, "Бобруйск");
        assertList(beginnerEnglishLevel, "Начальный уровень (Beginner)");
        assertField(habrContact, "https://www.hubr.com/kreedmusic");

    }
    private void loginOTUS(){

        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(mainMenuButton));
        driver.findElement(mainMenuButton).click();
        driver.findElement(inputEmail).sendKeys(System.getProperty("login"));
        driver.findElement(inputPassword).sendKeys(System.getProperty("password"));
         driver.findElement(confirmationButton).click();
    }
    private void enterLK(){

        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".sc-199a3eq-0.fJMWHf")));
        WebElement menu = driver.findElement(By.cssSelector(".sc-199a3eq-0.fJMWHf"));
        WebElement personal_account = driver.findElement(By.xpath("//a[contains(text(), 'Мой профиль')]"));
        Actions actions = new Actions(driver);
        actions
                .moveToElement(menu)
                .moveToElement(personal_account)
                .click()
                .build()
                .perform();

    }
    private void completion(By by, String text){

        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(text);
    }

    private void assertField(By by, String text){
        Assertions.assertEquals(text, driver.findElement(by).getAttribute("value"));
    }
    private  void assertList(By by,String text){
        Assertions.assertEquals(text, driver.findElement(by).getAttribute("title"));
    }
}
