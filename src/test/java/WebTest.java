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


public class WebTest extends BaseTest {

    @Test
    public void loginTest() throws IOException {

        //Navigate
        driver.get("https://www.zara.com/tr/");
        waitSecond(3);

        WebElement acceptCookies = waitElement(By.id("onetrust-accept-btn-handler"));
        acceptCookies.click();

        WebElement loginPageButton = waitElement(By.xpath("//a[@class= 'layout-actionable layout-desktop-account-action layout-header-desktop-action-account link']"));
        loginPageButton.click();
        System.out.println("Giriş sayfası açıldı");
        waitSecond(2);

        //login
        WebElement emailTextbox = waitElement(By.xpath("//input[@type='email']"));
        emailTextbox.click();
        emailTextbox.sendKeys("dilaraakbiyik3@gmail.com");
        waitSecond(2);

        WebElement continueButton = waitElement(By.xpath("//button[text()='Devam et']"));
        continueButton.click();
        waitSecond(2);

        WebElement loginWithPassword = waitElement(By.xpath("//a[@aria-label='Parola ile giriş yap']"));
        loginWithPassword.click();
        waitSecond(2);

        WebElement passwordText = waitElement(By.xpath("//input[@name='password']"));
        passwordText.click();
        passwordText.sendKeys("");

        WebElement loginButton = waitElement(By.xpath("//button[text()='Oturum aç']"));
        loginButton.click();
        System.out.println("Kullanıcı ile giriş yapıldı");
        waitSecond(2);

        //Menu
        WebElement menuIcon = waitElement(By.xpath("//button[@aria-label='Menüyü aç']/div"));
        menuIcon.click();
        waitSecond(1);

        WebElement maleCategory = waitElement(By.xpath("//div[@aria-label='Kategoriler']//span[text()='ERKEK']"));
        maleCategory.click();
        waitSecond(2);

        WebElement allProducts = waitElement(By.xpath("//span[text()='TÜMÜNÜ GÖR']"));
        allProducts.click();
        System.out.println("Tüm ürünler listelendi");
        waitSecond(2);

        //Product search
        String excelPath = "/Users/dilaraakbiyik/Desktop/ornekExcel.xlsx";
        FileInputStream excelFile = new FileInputStream(excelPath);

        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet("Sheet1");

        String firstValue = sheet.getRow(0).getCell(0).getStringCellValue();
        String secondValue = sheet.getRow(0).getCell(1).getStringCellValue();

        workbook.close();
        excelFile.close();

        WebElement searchButton = waitElement(By.xpath("//a[@data-qa-id='header-search-text-link']"));
        searchButton.click();
        waitSecond(1);

        //First data
        WebElement searchButtonEnter = waitElement(By.xpath("//input[@id='search-home-form-combo-input']"));
        searchButtonEnter.click();
        searchButtonEnter.sendKeys(firstValue);
        searchButtonEnter.clear();
        waitSecond(1);

        //Second data
        searchButtonEnter.click();
        searchButtonEnter.sendKeys(secondValue);
        searchButtonEnter.sendKeys(Keys.ENTER);
        waitSecond(2);

        WebElement firstProduct = waitElement(By.xpath("//ul[@class='product-grid__product-list']/li[1]//a[@class='product-link product-grid-product__link link']"));
        firstProduct.click();
        waitSecond(2);

        //Get product info
        WebElement productInfo = waitElement(By.xpath("//h1[@data-qa-qualifier='product-detail-info-name']"));
        String productI = productInfo.getText();

        WebElement productAmount = waitElement(By.xpath("//span[@class='money-amount__main']"));
        String productA = productAmount.getText();

        //Add txt
        String txtPath = System.getProperty("user.dir") + "/productInfo.txt";
        waitSecond(1);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtPath, true))) {
            if (!(productI.isEmpty())){
                writer.write(productI);
                writer.newLine();
                System.out.println("Ürün bilgisi yazıldı: " + txtPath);
                if (!(productA.isEmpty())){
                    writer.write(productA);
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
        WebElement addToCart = waitElement(By.xpath("//button[@data-qa-action='add-to-cart']"));
        addToCart.click();
        waitSecond(1);

        //size
        WebElement productSize = waitElement(By.xpath("//ul[@class='size-selector-sizes']//div[text()='S (US S)']"));
        productSize.click();
        WebElement popUpMessageNo = waitElement(By.xpath("//button[text()='Hayır, teşekkürler']"));
        popUpMessageNo.click();
        System.out.println("Ürün sepete eklendi");

        //See cart
        WebElement seeCart = waitElement(By.xpath("//span[text()='Alışveriş sepetini gör']"));
        seeCart.click();
        waitSecond(5);

        //Price
        WebElement priceCart = waitElement(By.xpath("//div[@class='shop-cart-item-pricing__current']//span[@class='money-amount__main']"));
        String priceInfo = priceCart.getText();
        System.out.println("Ürün tutarı görüldü");
        waitSecond(1);

        if (priceInfo.equals(productA)){
            System.out.println("Ürün tutarı ile sepetteki tutar eşleşti");
        }else{
            Assert.fail("Tutarlar eşleşmedi");
        }

        //Add second product
        WebElement secondProduct = waitElement(By.xpath("//div[@class='shop-cart-item-image']//img']"));
        secondProduct.click();
        addToCart.click();
        productSize.click();
        waitSecond(3);
        seeCart.click();
        System.out.println("İkinci ürün sepete eklendi");

        WebElement productCount = waitElement(By.xpath("//div[@class='shop-cart-item-quantity__container']//input"));
        String productC = productCount.getAttribute("value");

        if (productC != null && productC.equals("2")) {
            System.out.println("Ürün sayısı doğrulandı");
        }else {
            Assert.fail("Ürün sayısı doğrulanmadı");
        }

        //Delete
        WebElement deleteProductCart = waitElement(By.xpath("//span[text()='Sil']"));
        deleteProductCart.click();
        waitSecond(1);

        WebElement cartMessage = waitElement(By.xpath("//div[@class='zds-empty-state__title']"));
        String message = cartMessage.getText();
        waitSecond(1);

        if (message.equals("SEPETİNİZ BOŞ")){
            System.out.println("Sepette ürün mevcut değil");
        }else{
            Assert.fail("Mesaj bulunamadı");
        }

    }
}

