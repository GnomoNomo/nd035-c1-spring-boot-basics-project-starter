package com.udacity.jwdnd.course1.cloudstorage.loginpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "error-msg")
    private WebElement errorText;

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void pressSubmitButton(){
        submitButton.click();
    }

    public void pressSignupLink() { signupLink.click(); }

    public void writeUserName(String username){
        usernameInput.sendKeys(username);
    }

    public void writePassword(String password) {
        passwordInput.sendKeys(password);
    }

    public String getErrorMsg(){
        return errorText.getText();
    }
}
