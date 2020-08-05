package com.udacity.jwdnd.course1.cloudstorage.loginpage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginPageTests {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private LoginPage loginPage;

    private final String TEST_USER = "testUser";
    private final String TEST_PASSWORD = "testPw";

    @BeforeAll
    public static void beforeAll(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() throws InterruptedException {
        driver.quit();
    }

    @BeforeEach
    public void beforeEach(){
        driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(driver);
    }

    @Test
    public void failLoginWhenNoExistingCredentials(){
        loginPage.writeUserName("test");
        loginPage.writePassword("password");
        loginPage.pressSubmitButton();
        String errorMsg = loginPage.getErrorMsg();
        assertNotNull(errorMsg);
        String url = driver.getCurrentUrl();
        assertEquals(url, "http://localhost:" + port +  "/login?error");
    }

    @Test
    public void successfulLoginWhenExistingCredentials(){
        loginPage.writeUserName(TEST_USER);
        loginPage.writePassword(TEST_PASSWORD);
        loginPage.pressSubmitButton();
        String url = driver.getCurrentUrl();
        assertEquals(url, "http://localhost:" + port +  "/home");
    }

    @Test
    public void navigateToRegisterPage(){
        loginPage.pressSignupLink();
        String url = driver.getCurrentUrl();
        assertEquals(url, "http://localhost:" + port +  "/signup");
    }
}
