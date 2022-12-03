package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class test1 {
    public static void main(String[] args) throws FileNotFoundException {
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //URL adresine gidildi ve URL adresi kontrol edildi.
        driver.get("https://www.network.com.tr/");
        if (driver.getCurrentUrl().endsWith("network.com.tr/")){
            System.out.println("URL Passed");
        }
        else {
            System.out.println("URL Failed");
        }
        //

        //Arama sekmesine "ceket" yazdırıldı ve arama yapıldı.
        WebElement searchElement=driver.findElement(By.id("search"));
        searchElement.sendKeys("ceket");
        driver.findElement(By.id("search")).sendKeys(Keys.ENTER);
        //

        //Daha fazla göster butonuna kadar scroll yapıldı ve butona tıklandı.
        WebElement Btn = driver.findElement(new By.ByCssSelector("button.-secondary.-sm.relative"));
        Btn.click();
        js.executeScript("arguments[0].scrollIntoView();", Btn);
        //

        //Programı buradan başlayarak birkaç yerde daha beklettim yoksa program hata veriyordu.
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException ie){
        }
        //

        //2. Sayfaya geçildiğinin URL kontrolü yapıldı.
        if (driver.getCurrentUrl().endsWith("network.com.tr/search?searchKey=ceket&page=2")){
            System.out.println("URL2 Passed");
        }
        else {
            System.out.println("URL2 Failed");
        }
        //

        //Ürünün indirim yüzdesini ve fiyatını String değişkenlere atandı.
        String discountPercent = driver.findElement(By.xpath("//div[@class='product__discountPercent']/span")).getText();
        System.out.println("Price Discount Percent= "+discountPercent);

        String price = driver.findElement(By.xpath("//div/span[@class='product__price -actual']")).getText();
        System.out.println("Price= "+price);
        //


        //Burada ürünün bedeninin rastgele seçilmesi için gerekli kodu yazdım fakat bedenler ürünlere göre çok
        //değişkenlik gösterdiğinden ve ilk üründe sadece tek beden kaldığından olan bedeni kullandım.
        //
        //String [] sizeArray={"size_XS","size_S","size_M","size_L","size_XL"};
        //String [] sizeArrayCheck={"XS","S","M","L","XL"};
        //Random r=new Random();
        //int randomSize=r.nextInt(6)-1;
        //
        //


        //İlk ürüne hover edildikten sonra  elimizde  olan bedene tıklandı.
        String sizeArray="size_L";
        String sizeArrayCheck="L";
        WebElement size=driver.findElement(By.xpath("//div//label[@for='"+sizeArray+"']"));
        Actions act=new Actions(driver);
        act.moveToElement(size).perform();
        size.click();
        //

        //Program bekletildi.
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException ie){
        }
        //

        //Sağ altta açılan sepete git butonuna tıklandı.
        driver.findElement(By.xpath("//div//a[@class='button -primary header__basket--checkout header__basketModal -checkout']")).click();
        //

        //Program bekletildi.
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException ie){
        }
        //

        //Daha önceden elimizde bulunan beden ve fiyat bilgileri sepetteki bilgilerle karşılaştırıldı.
        String sizeBasket=driver.findElement(By.xpath("//div/span[@class='cartItem__attrValue']")).getText();
        System.out.println("Basket Size= "+sizeBasket);
        if(sizeArrayCheck.equals(sizeBasket)){
            System.out.println("Size True");
        }
        else {
            System.out.println("Size False");
        }

        String priceBasket=driver.findElement(By.xpath("//div/span[@class='cartItem__price -sale']")).getText();
        System.out.println("Basket Price= "+priceBasket);
        if(price.equals(priceBasket)){
            System.out.println("Price True");
        }
        else {
            System.out.println("Price False");
        }
        //

        //Elimizde olan fiyatın yanına indirimden önceki fiyatta çekildi.
        String oldPrice=driver.findElement(By.xpath("//div/span[@class='cartItem__price -old -labelPrice']")).getText();
        System.out.println("Old Price= "+oldPrice);
        //

        //Değişkenleri karşılaştırabilmek amacıyla string türünden float türüne çevirebilmek için değişkenlerdeki
        //" ","T","L","." karakterleri kaldırılıp "," yerine "." konuldu.
        String oldPriceNonTL = oldPrice.replace(" TL","");
        String oldPriceNonTL2 = oldPriceNonTL.replace(".","");
        String oldPriceNonTL3 = oldPriceNonTL2.replace(",",".");

        String priceNonTL = price.replace(" TL","");
        String priceNonTL2 = priceNonTL.replace(".","");
        String priceNonTL3 = priceNonTL2.replace(",",".");
        //

        //Değişkenlerimiz string türünedn float türüne çevrildi.
        Float oldPriceFloat=Float.parseFloat(oldPriceNonTL3);
        Float priceFloat=Float.parseFloat(priceNonTL3);
        //

        //Eski fiyatla indirimli fiyat karşılastırıldı.
        if(priceFloat<oldPriceFloat){
            System.out.println("Discount Done");
        }
        else if(priceFloat.equals(oldPriceFloat)){
            System.out.println("Price Equals");
        }
        else{
            System.out.println("Discount None");
        }
        //

        //Devam et butonuna tıklandı.
        driver.findElement(By.xpath("//div/button[@class='continueButton n-button large block text-center -primary']")).click();
        //

        //Cvs dosyası çekilip kontrolü yapıldı
        File giris=new File("C:\\Users\\can\\java\\SeleniumProject\\giris.csv");
        if (giris.exists()){
            System.out.println("File Find");
        }
        else {
            System.out.println("File Not Found");
        }
        //

        //Cvs dosyasından aldığımız bilgileri okuyup giriş için gerekli olan bilgileri değişkenlere atadık.
        String EmailNew=new String();
        String passwordNew=new String();
        Scanner s= new Scanner(giris);
        while(s.hasNextLine()){
            String satir=s.nextLine();
            String[] split=satir.split(";");
            EmailNew=split[0];
            passwordNew=split[1];
        }
        s.close();
        //

        //Program bekletildi.
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException ie){
        }
        //

        //Yukarıda dosyadan aldığımız verilerle kullanıcı bilgileri dolduruldu.
        WebElement Email=driver.findElement(By.xpath("//div/input[@data-val-required='Lütfen e-posta adresi giriniz.']"));
        Email.click();
        Email.sendKeys(EmailNew);

        WebElement password=driver.findElement(By.xpath("//div/input[@data-val-required='Lütfen şifrenizi giriniz.']"));
        password.click();
        password.sendKeys(passwordNew);
        //

        //Giriş yap butonuna gelindi.
        WebElement girisYap=driver.findElement(By.xpath("//form/button[@type='submit']"));
        Actions act2=new Actions(driver);
        act2.moveToElement(girisYap).perform();
        //

        //NetWork logosuna tıklanıp ana sayfaya gidildi.
        driver.findElement(By.xpath("//div/a[@class='headerCheckout__logo']")).click();
        //

        //Sepet imgesine tıklandı.
        driver.findElement(By.xpath("//button[@class='header__basketTrigger js-basket-trigger -desktop']")).click();
        //

        //Program bekkletildi.
        try{
            Thread.sleep(3000);
        }
        catch(InterruptedException ie){
        }
        //

        //Ürün sepetten çıkarmak için çöp kutusu imgesine tıklandı.
        driver.findElement(By.xpath("//div[@class='header__basketProductBtn header__basketModal -remove']")).click();
        //

        //Karşımıza gelen uyarı ekranında çıkart butonuna basıldı.
        driver.findElement(By.xpath("//button[@class='btn -black o-removeCartModal__button']")).click();
        //

        //Sepetin yeni içeriğini görmek için tekrar sepet imgesine tıklandı ve sepetin boş olup olmadığı
        //kontrolü yapıldı.
        driver.findElement(By.xpath("//button[@class='header__basketTrigger js-basket-trigger -desktop']")).click();
        String emptyBasket=driver.findElement(By.xpath("//div/span[@class='header__emptyBasketText']")).getText();
        String emptyBasketCheck="Sepetiniz Henüz Boş";
        if (emptyBasketCheck.equals(emptyBasket)){
            System.out.println("Sepetiniz Boş");
        }
        else {
            System.out.println("Sepetiniz Dolu");
        }
        //
    }
}


