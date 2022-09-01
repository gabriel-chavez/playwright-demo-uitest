import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.MouseButton;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

public class FirstTestPlaywright {
    @Test @Ignore
    public void LaunchBrowserTest(){
        try(Playwright playwright=Playwright.create()){
            // Playwright por defecto corre en memoria por lo que para visualizar el navegador se deben establecer parametros:
            //Page page=playwright.chromium().launch().newPage();
            //para visualizar agregamos un tiempo
            Page page=playwright.chromium().launch( new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(500)).newPage();
            page.setViewportSize(1920,1080);
            page.navigate("https://www.saucedemo.com/");
            //click
            //por texto
            //page.click("text=login");
            //por id
            //page.click("id=login-button");
            //page.click("#login-button");
            //por selector css
            //page.click("input.submit-button.btn_action");
            //por placeholder
            //page.type("[placeholder=Username]","admin");
            //page.type("[placeholder=Password]","password");
            //por xpath
            //page.click("//form/input[@value='Login']");
            //REALIZAR ACCIONES
            //click derecho
            //page.click("text=login",new Page.ClickOptions().setButton(MouseButton.RIGHT));
            //INTERACTUANDO
            page.type("[placeholder=Username]","standard_user");
            page.type("[placeholder=Password]","secret_sauce");
            page.click("#login-button");
            //get text
            //String textElement=page.innerText("a#item_4_title_link div");
            //System.out.println(textElement);
            //elemento select
            //page.selectOption("select.product_sort_container","hilo");
        }

    }
    @Test @Ignore
    public void VerifyUserIsLogged() {
        try(Playwright playwright=Playwright.create()) {
            Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(400)).newPage();
            page.setViewportSize(1920, 1080);
            page.navigate("https://www.saucedemo.com/");
            page.type("[placeholder=Username]", "standard_user");
            page.type("[placeholder=Password]", "secret_sauce");
            page.click("#login-button");
            boolean imInHomePage = page.isVisible("span.title");
            Assert.assertTrue(imInHomePage);
        }
    }
    @Test
    public void verificaLabsBackPack(){
        try(Playwright playwright=Playwright.create()) {
            Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(0)).newPage();
            page.setViewportSize(1920, 1080);
            page.navigate("https://www.saucedemo.com/");
            page.type("[placeholder=Username]", "standard_user");
            page.type("[placeholder=Password]", "secret_sauce");
            page.click("#login-button");
            page.click("text=Sauce Labs Backpack");
            boolean itemIsDisplay = page.isVisible("text=Sauce Labs Backpack");
            boolean itemPrice= page.isVisible("div.inventory_details_price");
            Assert.assertTrue(itemIsDisplay && itemPrice);
        }
    }
    @Test
    public void verificaItemAgregadoCarrito(){
        try(Playwright playwright=Playwright.create()) {
            Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(0)).newPage();
            page.setViewportSize(1920, 1080);
            page.navigate("https://www.saucedemo.com/");
            page.type("[placeholder=Username]", "standard_user");
            page.type("[placeholder=Password]", "secret_sauce");
            page.click("#login-button");
            page.click("text=Sauce Labs Backpack");
            page.click("id=add-to-cart-sauce-labs-backpack");
            page.click("a.shopping_cart_link");
            boolean itemIsDisplay = page.isVisible("text=Sauce Labs Backpack");
            int cantidadItems = Integer.parseInt(page.innerText("span.shopping_cart_badge"));
            System.out.println(cantidadItems);
            Assert.assertTrue(itemIsDisplay && (cantidadItems>0));
        }
    }
    @Test
    public void verificaUsuarioBloqueado(){
        try(Playwright playwright=Playwright.create()) {
            Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(0)).newPage();
            page.setViewportSize(1920, 1080);
            page.navigate("https://www.saucedemo.com/");
            page.type("[placeholder=Username]", "locked_out_user");
            page.type("[placeholder=Password]", "secret_sauce");
            page.click("#login-button");

            boolean usuarioBloqeuado = page.isVisible("text=Epic sadface: Sorry, this user has been locked out.");

            Assert.assertTrue(usuarioBloqeuado);
        }
    }
    @Test
    public void verificaCheckOutItems(){
        try(Playwright playwright=Playwright.create()) {
            Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(0)).newPage();
            page.setViewportSize(1920, 1080);
            page.navigate("https://www.saucedemo.com/");
            page.type("[placeholder=Username]", "standard_user");
            page.type("[placeholder=Password]", "secret_sauce");
            page.click("#login-button");
            page.click("text=Sauce Labs Backpack");
            page.click("id=add-to-cart-sauce-labs-backpack");
            page.click("a.shopping_cart_link");
            page.click("#checkout");
            page.type("#first-name", "Gabriel");
            page.type("#last-name", "Chavez");
            page.type("#postal-code", "591");
            page.click("#continue");
            page.click("#finish");
            boolean finCheckout = page.isVisible("text=THANK YOU FOR YOUR ORDER");
            page.click("#back-to-products");

            String cantidadItems = page.innerText("a.shopping_cart_link");

            boolean sinItems=cantidadItems.trim().isEmpty();
            Assert.assertTrue(finCheckout && sinItems);
        }

    }
    @Test
    public void verificaRemoverItems() {
        try (Playwright playwright = Playwright.create()) {
            Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(0)).newPage();
            page.setViewportSize(1920, 1080);
            page.navigate("https://www.saucedemo.com/");
            page.type("[placeholder=Username]", "standard_user");
            page.type("[placeholder=Password]", "secret_sauce");
            page.click("#login-button");
            page.click("text=Sauce Labs Backpack");
            page.click("id=add-to-cart-sauce-labs-backpack");
            page.click("a.shopping_cart_link");
            boolean itemIsDisplay = page.isVisible("text=Sauce Labs Backpack");
            int cantidadItems = Integer.parseInt(page.innerText("span.shopping_cart_badge"));
            System.out.println(cantidadItems);
            Assert.assertFalse(cantidadItems==0);
            page.click("#remove-sauce-labs-backpack");
            int cantidadItemsRemove;
            if(page.innerText("a.shopping_cart_link").trim().isEmpty())
                cantidadItemsRemove=0;
            else{
                cantidadItemsRemove= Integer.parseInt(page.innerText("span.shopping_cart_badge"));
            }
            Assert.assertTrue(itemIsDisplay && (cantidadItems-1 == cantidadItemsRemove));
        }
    }
}
