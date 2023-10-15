import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Base {
    private static String url = "https://www.amazon.eg/?language=en_AE";
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static String myEmail = "omaramazon804@gmail.com";
    private static String myPassword = "123456Oo$";

    public static void home() {

        System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            // Open the Amazon website
            driver.get(url);
            driver.manage().window().maximize();
            //Sign In
            WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"nav-link-accountList\"]/div")));
            signIn.click();
            //Add email
            WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("ap_email")));
            email.sendKeys(myEmail);
            // Click on the continue button
            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
            continueButton.click();
            //Add password
            WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.id("ap_password")));
            password.sendKeys(myPassword);
            // Click on the sign in button
            WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("signInSubmit")));
            signInButton.click();
            // Click on the hamburger menu
            WebElement hamburgerMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-hamburger-menu")));
            hamburgerMenu.click();
            // Click on "See all" in the menu
            WebElement seeAllMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"hmenu-content\"]/ul[1]/li[14]/a[1]/div")));
            seeAllMenu.click();
            // Click on "Video Games" category
            WebElement videoGamesCategory = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"hmenu-content\"]/ul[1]/ul/li[11]/a/div")));
            videoGamesCategory.click();
            // Click on "All Video Games"
            WebElement allVideoGames = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"hmenu-content\"]/ul[32]/li[3]/a")));
            allVideoGames.click();
            // Click on "Free Shipping"
            WebElement freeShippingFilter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"s-refinements\"]/div[2]/ul/li/span/a/div[2]/span")));
            freeShippingFilter.click();
            // Click on "New Condition"
            WebElement newConditionFilter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"p_n_condition-type/28071525031\"]/span/a/span")));
            newConditionFilter.click();
            int page = 1;
            boolean foundItems = false;
            while (!foundItems) {
                if (page == 1) {
                    // Click on the "Sort" menu
                    WebElement sortMenu = wait.until(ExpectedConditions.elementToBeClickable(By.id("a-autoid-0-announce")));
                    sortMenu.click();
                    // Select "Price: High to Low" from the sort menu
                    WebElement highToLowOption = wait.until(ExpectedConditions.elementToBeClickable(By.id("s-result-sort-select_2")));
                    highToLowOption.click();
                }
                // Identify the parent element
                //WebElement parentElement = driver.findElement(By.cssSelector("[class='sg-col-inner']"));

                // Identify the child elements with item prices
                //List<WebElement> prices = parentElement.findElements(By.cssSelector("[class='a-price-whole']"));
                // Find and process items with a price less than 15000
                List<WebElement> prices = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[class='a-price-whole']")));
                for (int i = 0; i < prices.size(); i++) {
                    try {
                        String priceText = prices.get(i).getText().replaceAll("[^0-9]", "");
                        int price = Integer.parseInt(priceText);
                        if (price < 15000) {
                            // Click on the item to open its details page
                            WebElement item = wait.until(ExpectedConditions.elementToBeClickable(prices.get(i)));
                            item.click();
                            WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
                            addToCart.click();
                            Thread.sleep(3000);
                            if (driver.getPageSource().contains("1 Year Extended Warranty Plan"))
                            {
                                driver.navigate().back();
                                Thread.sleep(3000);
                                driver.navigate().forward();
                            }
                            driver.navigate().back();
                            Thread.sleep(3000);
                            driver.navigate().back();
                            Thread.sleep(3000);
                            prices = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[class='a-price-whole']")));
                        }
                    } catch (StaleElementReferenceException e) {
                        // Handle stale element exception gracefully
                        System.out.println("Stale Element Exception: " + e.getMessage());
                        // Re-locate the element and continue
                        prices = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("[class='a-price-whole']")));
                    }
                }
                // Check if there's a next page and navigate to it
                WebElement nextPageButton = driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[18]/div/div/span/a[3]"));
                if (nextPageButton.isEnabled()) {
                    nextPageButton.click();
                    page++;
                } else {
                    // No more pages, exit the loop
                    foundItems = true;
                   }
            }
            //Open the cart
            WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-cart")));
            cart.click();
            //Proceed to buy
            WebElement proceed = wait.until(ExpectedConditions.elementToBeClickable(By.id("sc-buy-box-ptc-button")));
            proceed.click();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}