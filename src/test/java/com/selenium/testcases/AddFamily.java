package com.selenium.testcases;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class AddFamily {
    
    private static Properties addfamilyInputProp;
    private static Properties elementPath;
    private static WebDriver driver;
    
    @BeforeTest
    @Parameters({ "browser", "url","loginid","password","input","elementpath"})
    public void init(String browser, String url, String loginid, String password, String input, String elementpath) {
        if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");
            driver = new FirefoxDriver();
        } else if(browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            driver = new ChromeDriver();
        }
        driver.get(url);
        driver.manage().window().maximize();
        addfamilyInputProp = new Properties();
        elementPath = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream addfamily = loader.getResourceAsStream(input);
            InputStream xpathStream = loader.getResourceAsStream(elementpath);
            addfamilyInputProp.load(addfamily);
            elementPath.load(xpathStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        makeDelayBy(5000L);
        driver.findElement(By.linkText("Tapgenes Login")).click();
        makeDelayBy(10000L);
        driver.findElement(By.xpath(elementPath.getProperty("username"))).sendKeys(addfamilyInputProp.getProperty("loginid"));
        driver.findElement(By.xpath(elementPath.getProperty("password"))).sendKeys(addfamilyInputProp.getProperty("password"));
        driver.findElement(By.xpath(elementPath.getProperty("submit"))).click();
        makeDelayBy(10000L);
    }

    @Test
    public void addSelfDetails() {
        driver.findElement(By.xpath(elementPath.getProperty("loginperson"))).click();
        try {
            makeDelayBy(3000L);
            switch(addfamilyInputProp.getProperty("loginpersongender")) {
            case "Male":
                driver
                .findElement(By.xpath(elementPath.getProperty("loginpersongender1"))).click();
                break;
            case "Female":
                driver
                .findElement(By.xpath(elementPath.getProperty("loginpersongender2"))).click();
                break;
            default:
                driver
                .findElement(By.xpath(elementPath.getProperty("loginpersongender3"))).click();
                break;
            }
            
            driver.findElement(By.xpath(elementPath.getProperty("initialnext"))).click();
            Thread.sleep(3000L);
            driver.findElement(By.id("dob_month")).sendKeys(addfamilyInputProp.getProperty("loginpersonday"));
            driver.findElement(By.xpath(elementPath.getProperty("loginpersondate"))).sendKeys(addfamilyInputProp.getProperty("loginpersonmonth"));
            driver.findElement(By.xpath(elementPath.getProperty("loginpersonyear"))).sendKeys(addfamilyInputProp.getProperty("loginpersonyear"));
            driver.findElement(By.xpath(elementPath.getProperty("initialnext"))).click();
            makeDelayBy(3000L);
        } catch (Exception e) {
            
        }
//        driver.navigate().refresh();
        makeDelayBy(3000L);
    }
    private String parentxName;
    private String parentyName;
    @Test(dependsOnMethods = { "addSelfDetails" }) 
    public void addParents() {
        driver.findElement(By.xpath(elementPath.getProperty("loginperson"))).click();
        makeDelayBy(3000L);
        // PARENTX
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        makeDelayBy(3000L);
        Select relation = new Select(driver.findElement(By.id("r_relation")));
        relation.selectByVisibleText("Parent");
        makeDelayBy(2000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("parentxfname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("parentxlname"));
        parentxName = concatName(addfamilyInputProp.getProperty("parentxfname"), addfamilyInputProp.getProperty("parentxlname"));
        Select relationGender = new Select(driver.findElement(By.id("r_gender")));
        relationGender.selectByVisibleText(addfamilyInputProp.getProperty("parentxgender"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("parentxday"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("parentxmonth"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("parentxyear"));
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
        makeDelayBy(5000L);

        // PARENTY
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        makeDelayBy(3000L);
        Select relationMother = new Select(driver.findElement(By.id("r_relation")));
        relationMother.selectByVisibleText("Parent");
        makeDelayBy(2000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("parentyfname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("parentylname"));
        parentyName = concatName(addfamilyInputProp.getProperty("parentyfname"), addfamilyInputProp.getProperty("parentylname"));
        Select relationMotherGender = new Select(driver.findElement(By.id("r_gender")));
        relationMotherGender.selectByVisibleText(addfamilyInputProp.getProperty("parentygender"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("parentyday"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("parentymonth"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("parentyyear"));
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
        makeDelayBy(5000L);
    }
    
    private String sibling1Name;
    @Test(dependsOnMethods = { "addParents" }) 
    public void addSibling() {
        //sibling
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        makeDelayBy(3000L);
        Select relationSibling = new Select(driver.findElement(By.id("r_relation")));
        relationSibling.selectByVisibleText("Sibling");
        makeDelayBy(2000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("sibling1fname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("sibling1lname"));
        sibling1Name = concatName(addfamilyInputProp.getProperty("sibling1fname"), addfamilyInputProp.getProperty("sibling1lname"));
        Select relationSiblingGender = new Select(driver.findElement(By.id("r_gender")));
        relationSiblingGender.selectByVisibleText(addfamilyInputProp.getProperty("sibling1gender"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("sibling1day"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("sibling1month"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("sibling1year"));
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
        makeDelayBy(5000L);
    }
    
    private String partnerName;
    @Test(dependsOnMethods = { "addSibling" })
    public void addPartner() {
        // partner
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        makeDelayBy(3000L);
        Select relationPartner = new Select(driver.findElement(By.id("r_relation")));
        relationPartner.selectByVisibleText("Partner");
        makeDelayBy(3000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("partnerfname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("partnerlname"));
        partnerName = concatName(addfamilyInputProp.getProperty("partnerfname"), addfamilyInputProp.getProperty("partnerlname"));
        Select relationPartnerGender = new Select(driver.findElement(By.id("r_gender")));
        relationPartnerGender.selectByVisibleText(addfamilyInputProp.getProperty("partnergender"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("partnerday"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("partnermonth"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("partneryear"));
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
        makeDelayBy(5000L);
    }
    
    private String child1Name;
    @Test(dependsOnMethods = { "addPartner" })
    public void addChildren() {
        // child
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        makeDelayBy(3000L);
        Select relationChild = new Select(driver.findElement(By.id("r_relation")));
        relationChild.selectByVisibleText("Child");
        makeDelayBy(2000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("child1fname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("child1lname"));
        child1Name = concatName(addfamilyInputProp.getProperty("child1fname"), addfamilyInputProp.getProperty("child1lname"));
        Select relationChildGender = new Select(driver.findElement(By.id("r_gender")));
        relationChildGender.selectByVisibleText(addfamilyInputProp.getProperty("child1gender"));
        
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("child1day"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("child1month"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("child1year"));
        driver.findElement(
                By.xpath("//Select[@id='targetPersonSecondParent']/option[normalize-space(text())='"+(addfamilyInputProp.getProperty("partnerfname").concat(" ").concat(addfamilyInputProp.getProperty("partnerlname")))+"']"))
                .click();
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
        makeDelayBy(5000L);
    }
    
    private String parentxparentxName;
    private String parentxparentyName;
    @Test(dependsOnMethods = { "addChildren" })
    public void addFatherParents() {
        // Parentx Parentx
        driver.findElement(By.xpath("//*[contains(@id,'"+parentxName+"-')]")).click();
        makeDelayBy(5000L);
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        Select relationFatherFather = new Select(driver.findElement(By.id("r_relation")));
        makeDelayBy(4000L);
        relationFatherFather.selectByVisibleText("Parent");
        makeDelayBy(2000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("parentxparentxfname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("parentxparentxlname"));
        parentxparentxName = concatName(addfamilyInputProp.getProperty("parentxparentxfname"), addfamilyInputProp.getProperty("parentxparentxlname"));
        Select relationFFGender = new Select(driver.findElement(By.id("r_gender")));
        relationFFGender.selectByVisibleText(addfamilyInputProp.getProperty("parentxparentxgender"));
        
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("parentxparentxday"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("parentxparentxmonth"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("parentxparentxyear"));
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
        makeDelayBy(5000L);
        
        // Parentx Parenty
        driver.findElement(By.xpath("//*[contains(@id,'"+parentxName+"-')]")).click();
        makeDelayBy(5000L);
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        Select relationFatherMother = new Select(driver.findElement(By.id("r_relation")));
        makeDelayBy(4000L);
        relationFatherMother.selectByVisibleText("Parent");
        makeDelayBy(2000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("parentxparentyfname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("parentxparentylname"));
        parentxparentyName = concatName(addfamilyInputProp.getProperty("parentxparentyfname"), addfamilyInputProp.getProperty("parentxparentylname"));
        Select relationFMGender = new Select(driver.findElement(By.id("r_gender")));
        relationFMGender.selectByVisibleText(addfamilyInputProp.getProperty("parentxparentygender"));
        
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("parentxparentyday"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("parentxparentymonth"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("parentxparentyyear"));
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
    }
    
    private String parentyparentxName;
    private String parentyparentyName;
    @Test(dependsOnMethods = { "addFatherParents" })
    public void addMotherParents() {
        // Father father(grandpa)
        makeDelayBy(5000L);
        driver.findElement(By.xpath("//*[contains(@id,'"+parentyName+"-')]")).click();
        makeDelayBy(5000L);
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        Select relationMotherFather = new Select(driver.findElement(By.id("r_relation")));
        makeDelayBy(4000L);
        relationMotherFather.selectByVisibleText("Parent");
        makeDelayBy(2000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("parentyparentxfname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("parentyparentxlname"));
        parentyparentxName = concatName(addfamilyInputProp.getProperty("parentyparentxfname"), addfamilyInputProp.getProperty("parentyparentxlname"));
        Select relationFFGender = new Select(driver.findElement(By.id("r_gender")));
        relationFFGender.selectByVisibleText(addfamilyInputProp.getProperty("parentyparentxgender"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("parentyparentxday"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("parentyparentxmonth"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("parentyparentxyear"));
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
        makeDelayBy(5000L);
        
        // father mother
        driver.findElement(By.xpath("//*[contains(@id,'"+parentyName+"-')]")).click();
        makeDelayBy(5000L);
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        Select relationMotherMother = new Select(driver.findElement(By.id("r_relation")));
        makeDelayBy(4000L);
        relationMotherMother.selectByVisibleText("Parent");
        makeDelayBy(2000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("parentyparentyfname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("parentyparentylname"));
        parentyparentyName = concatName(addfamilyInputProp.getProperty("parentyparentyfname"), addfamilyInputProp.getProperty("parentyparentylname"));
        Select relationMFGender = new Select(driver.findElement(By.id("r_gender")));
        relationMFGender.selectByVisibleText(addfamilyInputProp.getProperty("parentyparentygender"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("parentyparentyday"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("parentyparentymonth"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("parentyparentyyear"));
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
    }
    
    private String child1partnerName;
    private String child1childName;
    @Test(dependsOnMethods = { "addMotherParents" })
    public void addGrandChildrenWithPartner() {
        makeDelayBy(5000L);
        driver.findElement(By.xpath("//*[contains(@id,'"+child1Name+"-')]")).click();
        makeDelayBy(5000L);
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        Select relationMotherFather = new Select(driver.findElement(By.id("r_relation")));
        makeDelayBy(4000L);
        relationMotherFather.selectByVisibleText("Partner");
        makeDelayBy(2000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("child1partnerfname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("child1partnerlname"));
        child1partnerName = concatName(addfamilyInputProp.getProperty("child1partnerfname"), addfamilyInputProp.getProperty("child1partnerlname"));
        Select relationFFGender = new Select(driver.findElement(By.id("r_gender")));
        relationFFGender.selectByVisibleText(addfamilyInputProp.getProperty("child1partnergender"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("child1partnerday"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("child1partnermonth"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("child1partneryear"));
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
        makeDelayBy(5000L);
        
        // grandchild
        driver.findElement(By.xpath("//*[contains(@id,'"+child1Name+"-')]")).click();
        makeDelayBy(5000L);
        driver.findElement(By.xpath(elementPath.getProperty("addrelationbutton"))).click();
        Select relationMotherMother = new Select(driver.findElement(By.id("r_relation")));
        makeDelayBy(4000L);
        relationMotherMother.selectByVisibleText("Child");
        makeDelayBy(2000L);
        driver.findElement(By.id("r_f_name")).sendKeys(addfamilyInputProp.getProperty("grandchild1fname"));
        driver.findElement(By.id("r_l_name")).sendKeys(addfamilyInputProp.getProperty("grandchild1lname"));
        child1childName = concatName(addfamilyInputProp.getProperty("grandchild1fname"), addfamilyInputProp.getProperty("grandchild1lname"));
        Select relationMFGender = new Select(driver.findElement(By.id("r_gender")));
        relationMFGender.selectByVisibleText(addfamilyInputProp.getProperty("grandchild1gender"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationday"))).sendKeys(addfamilyInputProp.getProperty("grandchild1day"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationmonth"))).sendKeys(addfamilyInputProp.getProperty("grandchild1month"));
        driver.findElement(By.xpath(elementPath.getProperty("addrelationyear"))).sendKeys(addfamilyInputProp.getProperty("grandchild1year"));
        driver.findElement(
                By.xpath("//Select[@id='targetPersonSecondParent']/option[normalize-space(text())='"+addfamilyInputProp.getProperty("child1partnerfname").concat(" ").concat(addfamilyInputProp.getProperty("child1partnerlname"))+"']"))
                .click();
        driver.findElement(By.xpath(elementPath.getProperty("saverelation"))).click();
    }
    
    private static void makeDelayBy(Long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    private static String concatName(String firstName,String lastName) {
        return firstName.toLowerCase().replaceAll(" ", "_").concat("_").concat(lastName.toLowerCase().replaceAll(" ", "_"));
        
    }
    
}
