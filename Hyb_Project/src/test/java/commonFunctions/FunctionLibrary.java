package commonFunctions;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
public static WebDriver driver;
public static Properties conpro;
//method for launching bowser
public static WebDriver startBrowser()throws Throwable
{
	conpro = new Properties();
	//load file
	conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(conpro.getProperty("Browser").equalsIgnoreCase("edge"))
	{
		driver = new EdgeDriver();
		driver.manage().window().maximize();
	}
	else
	{
		Reporter.log("Browser value is Not matching",true);
	}
	return driver;
}
//method for launch url
public static void openUrl()
{
	driver.get(conpro.getProperty("Url"));
}
//method for wait for element
public static void waitForElement(String Locator_Type,String Locator_Value,String TestData)
{
	WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));	
	}
}
//method for textboxes
public static void typeAction(String Locator_Type,String Locator_Value,String Test_Data)
{
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_Value)).clear();
		driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_Value)).clear();
		driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_Value)).clear();
		driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
	}
}
//method for buttons,checkboxes,radio buttons,links and images
public static void clickAction(String Locator_Type,String Locator_value)
{
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_value)).click();
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_value)).click();
		
	}
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_value)).sendKeys(Keys.ENTER);
	}
}
//method for validating title
public static void validateTitle(String Expected_Title)
{
	String Actual_Title = driver.getTitle();
	try {
	Assert.assertEquals(Expected_Title, Actual_Title, "Title is Not Matching");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
}
//method for closing browser
public static void closeBrowser()
{
	driver.quit();
}

//method for date generate
public static String generateDate()
{
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
	return df.format(date);
}
//Method for listBoxes
public static void dropDrownAction(String Locator_Type,String Locator_Value,String TestData) {
	if(Locator_Type.equalsIgnoreCase("xpath")) {
	int value =Integer.parseInt(TestData);
	Select element = new Select(driver.findElement(By.xpath(Locator_Value)));
	element.selectByIndex(value);
	}
	if(Locator_Type.equalsIgnoreCase("name")) {
		int value =Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.name(Locator_Value)));
		element.selectByIndex(value);
	}
	if(Locator_Type.equalsIgnoreCase("id")) {
		int value =Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.id(Locator_Value)));
		element.selectByIndex(value);
	}
}
//method for capturing stock number into note pad
public static void captureStockNum(String Locator_Type, String Locator_Value) throws Throwable {
	String StockNum="";
	if(Locator_Type.equalsIgnoreCase("id")) {
		StockNum=driver.findElement(By.id(Locator_Value)).getAttribute("value");
	}
	if (Locator_Type.equalsIgnoreCase("name")) {
		StockNum =driver.findElement(By.name(Locator_Value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("xpath")) {
		StockNum =driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
	}
	FileWriter fw =new FileWriter("./CaptureData/stocknumber.txt");
	BufferedWriter bw =new BufferedWriter(fw);
	bw.write(StockNum);
	bw.flush();
	bw.close();
}
//method for stock table
public static void stockTable() throws Throwable {
	FileReader fr =new FileReader("./CaptureData/stocknumber.txt");
	BufferedReader br =new BufferedReader(fr);
	String Exp_Data =br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
	driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(4000);
	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	Reporter.log(Exp_Data+"    "+ Act_Data,true);
	try {
		Assert.assertEquals(Exp_Data, Act_Data,"Stock Number Not Matching");
	
	} catch (AssertionError a) {
		System.out.println(a.getMessage());
	}
	
}
}










