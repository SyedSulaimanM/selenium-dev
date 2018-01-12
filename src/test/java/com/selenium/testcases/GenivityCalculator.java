package com.selenium.testcases;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class GenivityCalculator {

    private static Properties haloUIXPathProp;
    private static Properties haloInputsProp;
    private static WebDriver driver;

    @BeforeTest
    @Parameters({ "browser", "url", "inputFile" })
    public void init(String browser, String url, String inputFile) {
        if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
            driver = new FirefoxDriver();
        } else if(browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            driver = new ChromeDriver();
        }

        driver.get(url);
        haloUIXPathProp = new Properties();
        haloInputsProp = new Properties();
        String haloInputs = inputFile;
        String xpath = "haloUIXPath.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream haloStream = loader.getResourceAsStream(haloInputs);
                InputStream xpathStream = loader.getResourceAsStream(xpath)) {
            haloInputsProp.load(haloStream);
            haloUIXPathProp.load(xpathStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pageLaunch() {
        driver.findElement(By.xpath(haloUIXPathProp.getProperty("genivityButton"))).click();
        clickNext();
    }

    @Test(dependsOnMethods = { "pageLaunch" })
    public void haloCalcWizard() {
        String gender = haloInputsProp.getProperty("gender");
        if (gender.equalsIgnoreCase("male")) {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("male"))).click();
        } else {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("female"))).click();
        }
        clickNext();
        driver.findElement(By.xpath(haloUIXPathProp.getProperty("age"))).sendKeys(haloInputsProp.getProperty("age"));
        clickNext();
        driver.findElement(By.xpath(haloUIXPathProp.getProperty("feet"))).sendKeys(haloInputsProp.getProperty("feet"));
        driver.findElement(By.xpath(haloUIXPathProp.getProperty("inches")))
                .sendKeys(haloInputsProp.getProperty("inch"));
        clickNext();
        driver.findElement(By.xpath(haloUIXPathProp.getProperty("weight")))
                .sendKeys(haloInputsProp.getProperty("weight"));
        clickNext();
        String ancestry = haloInputsProp.getProperty("ancestry");
        switch (ancestry) {
        case "european":
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("european"))).click();
            clickNext();
            break;
        case "african":
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("african"))).click();
            clickNext();
            break;
        case "asian":
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("asian"))).click();
            clickNext();
            break;
        case "hispanic":
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("hispanic"))).click();
            clickNext();
            break;
        default:
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("mixed"))).click();
            clickNext();
            if (haloInputsProp.containsKey("europeanPercentage")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("europeanPercentage")))
                        .sendKeys(haloInputsProp.getProperty("europenPercentage"));
            }
            if (haloInputsProp.containsKey("africanPercentage")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("africanPercentage")))
                        .sendKeys(haloInputsProp.getProperty("africanPercentage"));
            }
            if (haloInputsProp.containsKey("asianPercentage")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("asianPercentage")))
                        .sendKeys(haloInputsProp.getProperty("asianPercentage"));
            }
            if (haloInputsProp.containsKey("hispanicPercentage")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("hispanicPercentage")))
                        .sendKeys(haloInputsProp.getProperty("hispanicPercentage"));
            }
            if (haloInputsProp.containsKey("othersPercentage")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("othersPercentage")))
                        .sendKeys(haloInputsProp.getProperty("othersPercentage"));
            }
            clickNext();
            break;
        }
        if (haloInputsProp.containsKey("exercise")) {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("exerciseyes"))).click();
        } else {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("exerciseno"))).click();
        }
        clickNext();
        if (haloInputsProp.containsKey("smoke")) {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("smokeyes"))).click();
        } else {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("smokeno"))).click();
        }
        clickNext();
        if (haloInputsProp.containsKey("diabetes")) {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("diabetesyes"))).click();
        } else {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("diabetesno"))).click();
        }
        clickNext();
        if (haloInputsProp.containsKey("heart")) {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("heartdiseaseyes"))).click();
        } else {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("heardiseaseno"))).click();
        }
        clickNext();
        if (haloInputsProp.containsKey("cancer")) {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("canceryes"))).click();
            clickNext();
            if (haloInputsProp.containsKey("bladder")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("bladder"))).click();
            }
            if (haloInputsProp.containsKey("breast")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("breast"))).click();
            }
            if (haloInputsProp.containsKey("colorectal")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("colorectal"))).click();
            }
            if (haloInputsProp.containsKey("kidney")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("kidney"))).click();
            }
            if (haloInputsProp.containsKey("lung")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("lung"))).click();
            }
            if (haloInputsProp.containsKey("ovarian")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("ovarian"))).click();
            }
            if (haloInputsProp.containsKey("pancreatic")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("pancreatic"))).click();
            }
            if (haloInputsProp.containsKey("prostate")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("prostate"))).click();
            }
            if (haloInputsProp.containsKey("skin")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("skin"))).click();
            }
            if (haloInputsProp.containsKey("other")) {
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("other"))).click();
            }
        } else {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("cancerno"))).click();
        }
        clickNext();
        if (haloInputsProp.containsKey("stroke")) {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("strokeyes"))).click();
        } else {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("strokeno"))).click();
        }
        clickNext();
        if (haloInputsProp.containsKey("alzheimer")) {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("alzheimeryes"))).click();
        } else {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("alzheimerno"))).click();
        }
        clickNext();
        makeDelay(5000L);
        String doctorVisit = haloInputsProp.getProperty("doctorvisit");
        switch (doctorVisit) {
        case "0-2":
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("doctorvisit02"))).click();
            break;
        case "3-5":
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("doctorvisit35"))).click();
            break;
        case "6+":
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("doctorvisit6+"))).click();
            break;
        }
        clickNext();
        WebElement retirmentAge = driver.findElement(By.xpath(haloUIXPathProp.getProperty("retirementage")));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].innerHTML = " + haloInputsProp.getProperty("retirementAge"), retirmentAge);
        makeDelay(2000L);
        clickNext();
        driver.findElement(By.xpath(haloUIXPathProp.getProperty("retirementplace")))
                .sendKeys(haloInputsProp.getProperty("retirementPlace"));
        clickNext();
        if (haloInputsProp.containsKey("parentsLongevity")) {
            String longevity = haloInputsProp.getProperty("parentsLongevity");
            switch (longevity) {
            case "1":
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("longevity1"))).click();
                break;
            case "2":
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("longevity2"))).click();
                break;
            case "3":
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("longevity3"))).click();
                break;
            case "4":
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("longevity4"))).click();
                break;
            case "5":
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("longevity5"))).click();
                break;
            case "6":
                driver.findElement(By.xpath(haloUIXPathProp.getProperty("longevity6"))).click();
                break;
            }
        } else {
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("longevitynone"))).click();
        }
        clickNext();
        driver.findElement(By.xpath(haloUIXPathProp.getProperty("firstname")))
                .sendKeys(haloInputsProp.getProperty("firstName"));
        driver.findElement(By.xpath(haloUIXPathProp.getProperty("lastname")))
                .sendKeys(haloInputsProp.getProperty("lastName"));
        driver.findElement(By.xpath(haloUIXPathProp.getProperty("email")))
                .sendKeys(haloInputsProp.getProperty("email"));
        driver.findElement(By.linkText("NEXT")).click();
        if (haloInputsProp.containsKey("smokeFreeLife")) {
            makeDelay(2000L);
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("smokefreelife"))).click();
        }
        if (haloInputsProp.containsKey("RegularExercise")) {
            makeDelay(2000L);
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("regularexercise"))).click();
        }
        if (haloInputsProp.containsKey("idealBMI")) {
            makeDelay(2000L);
            driver.findElement(By.xpath(haloUIXPathProp.getProperty("idealbmilongevity"))).click();
        }
    }

    @Test(dependsOnMethods = { "haloCalcWizard" })
    public void checkHaloResults() {
        if (driver.findElement(By.xpath(haloUIXPathProp.getProperty("activeWorking"))).isDisplayed()) {
            String activeWorking = driver.findElement(By.xpath(haloUIXPathProp.getProperty("activeWorking"))).getText();
            Assert.assertEquals(activeWorking, haloInputsProp.getProperty("activeWorking"));
        }
        if (driver.findElement(By.xpath(haloUIXPathProp.getProperty("activeRetirement"))).isDisplayed()) {
            String activeRetirement = driver.findElement(By.xpath(haloUIXPathProp.getProperty("activeRetirement")))
                    .getText();
            Assert.assertEquals(activeRetirement, haloInputsProp.getProperty("activeRetirement"));
        }
        if (driver.findElement(By.xpath(haloUIXPathProp.getProperty("assistedYears"))).isDisplayed()) {
            String assistedYears = driver.findElement(By.xpath(haloUIXPathProp.getProperty("assistedYears"))).getText();
            Assert.assertEquals(assistedYears, haloInputsProp.getProperty("assistedYears"));
        }
        if (driver.findElement(By.xpath(haloUIXPathProp.getProperty("yearsLost"))).isDisplayed()) {
            String yearsLost = driver.findElement(By.xpath(haloUIXPathProp.getProperty("yearsLost"))).getText();
            Assert.assertEquals(yearsLost, haloInputsProp.getProperty("yearsLost"));
        }
    }

    @AfterClass
    public void closeDriver() {
        makeDelay(8000L);
        driver.quit();
    }

    private void makeDelay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clickNext() {
        driver.findElement(By.xpath(haloUIXPathProp.getProperty("next"))).click();
    }

}
