package webOtomasyon;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;


public class WebTest extends webOtomasyon.BaseTest {

    @Test
    public void loginTest() throws IOException {
        //Navigate
        driver.get("https://www.zara.com/tr/");

        WebElement acceptCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
        acceptCookies.click();

        WebElement loginPageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class= 'layout-actionable layout-desktop-account-action layout-header-desktop-action-account link']")));
        loginPageButton.click();

        System.out.println("Giriş sayfası açıldı");

        //login

        WebElement emailTextbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='email']")));
        emailTextbox.click();
        emailTextbox.sendKeys("dilaraakbiyik3@gmail.com");

        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Devam et']")));
        continueButton.click();

        WebElement loginWithPassword = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@aria-label='Parola ile giriş yap']")));
        loginWithPassword.click();

        WebElement passwordText = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='password']")));
        passwordText.click();
        passwordText.sendKeys("");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Oturum aç']")));
        loginButton.click();
        System.out.println("Kullanıcı ile giriş yapıldı");

        //Menu
        WebElement menuIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Menüyü aç']/div")));
        menuIcon.click();

        WebElement maleCategory = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-label='Kategoriler']//span[text()='ERKEK']")));
        maleCategory.click();

        WebElement allProducts = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='TÜMÜNÜ GÖR']")));
        allProducts.click();
        System.out.println("Tüm ürünler listelendi");

        //Product search
        //File path
        String excelPath = System.getProperty("user.home") + "/Desktop/ornekExcel.xlsx";
        FileInputStream excelFile = new FileInputStream(excelPath);

        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet("Sheet1");

        String firstValue = sheet.getRow(0).getCell(0).getStringCellValue();
        String secondValue = sheet.getRow(0).getCell(1).getStringCellValue();

        workbook.close();
        excelFile.close();

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-qa-id='header-search-text-link']")));
        searchButton.click();

        //First data
        WebElement searchButtonEnter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search-home-form-combo-input']")));
        searchButtonEnter.click();
        searchButtonEnter.sendKeys(firstValue);
        searchButtonEnter.clear();

        //Second data
        searchButtonEnter.click();
        searchButtonEnter.sendKeys(secondValue);
        searchButtonEnter.sendKeys(Keys.ENTER);

        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='product-grid__product-list']/li[1]//a[@class='product-link product-grid-product__link link']")));
        firstProduct.click();

        //Get product info
        WebElement productInfo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1[@data-qa-qualifier='product-detail-info-name']")));
        String productI = productInfo.getText();

        WebElement productAmount = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='money-amount__main']")));
        String productA = productAmount.getText();

        //Add txt
        String txtPath = System.getProperty("user.dir") + "/productInfo.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtPath, true))) {
            if (!(productI.isEmpty())){
                writer.write(productI);
                writer.newLine();
                System.out.println("Ürün bilgisi yazıldı: " + txtPath);
                if (!(productA.isEmpty())){
                    writer.write(productI);
                    writer.newLine();
                    System.out.println("Tutar bilgisi yazıldı: " + txtPath);
                }else {
                    Assert.fail("Tutar bilgisi mevcut değil");
                }
            }else {
                Assert.fail("Ürün bilgisi mevcut değil");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //add to cart
        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-qa-action='add-to-cart']")));
        addToCart.click();

        //size
        WebElement productSize = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='size-selector-sizes']//div[text()='S (US S)']")));
        productSize.click();
        WebElement popUpMessageNo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Hayır, teşekkürler']")));
        popUpMessageNo.click();

        WebElement seeCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Alışveriş sepetini gör']")));
        seeCart.click();
        System.out.println("Ürün sepete eklendi");

        WebElement priceCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='shop-cart-item-pricing__current']//span[@class='money-amount__main']")));
        String priceInfo = priceCart.getText();
        System.out.println("Ürün tutarı görüldü");

        if (priceInfo.equals(productA)){
            System.out.println("Ürün tutarı ile sepetteki tutar eşleşti");
        }else{
            Assert.fail("Tutarlar eşleşmedi");
        }

        //Add second product
        WebElement secondProduct = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='shop-cart-item-image']//img']")));
        secondProduct.click();
        addToCart.click();
        productSize.click();
        seeCart.click();
        System.out.println("İkinci ürün sepete eklendi");

        WebElement productCount = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='shop-cart-item-quantity__container']//input")));
        String productC = productCount.getAttribute("value");

        if (productC.equals(2)){
            System.out.println("Ürün sayısı dıoğrulandı");
        }else{
            Assert.fail("Ürün sayısı doğrulanamadı");
        }

        //Delete
        WebElement deleteProductCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Sil']")));
        deleteProductCart.click();

        WebElement cartMessage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='zds-empty-state__title']")));
        String message = cartMessage.getText();

        if (message.equals("SEPETİNİZ BOŞ")){
            System.out.println("Sepette ürün mevcut değil");
        }else{
            Assert.fail("Mesaj bulunamadı");
        }

    }
}

