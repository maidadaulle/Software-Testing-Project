package views;

import static org.junit.jupiter.api.Assertions.*;


import com.example.librarymanagementsystem.Main;
import javafx.stage.Stage;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;

    public class MainTest extends ApplicationTest {


        @Override
        public void start(Stage stage) throws Exception {
            Main mainApp = new Main();
            mainApp.start(stage);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Test  
        public void testLaunchMainApp() {
            FxAssert.verifyThat("Log In", NodeMatchers.isVisible());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Test
        public void testLoginWithInvalidCredentials() {
            clickOn("#usernameTxtField").write("invalidUser");
            try {
                Thread.sleep(2000); // 2-second break
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clickOn("#passwordField").write("wrongPassword");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clickOn("#submitButton");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String systemFieldText = lookup("#systemField").queryText().getText();
            assertEquals("Wrong Credentials!", systemFieldText);
        }


        @Test
        public void testLoginWithValidAdminCredentials() {
            waitForNode("#usernameTxtField");

            clickOn("#usernameTxtField").write("admin");
            clickOn("#passwordField").write("admin");
            clickOn("#submitButton");

            waitForNode("Admin Home Page");

            FxAssert.verifyThat("Admin Home Page", NodeMatchers.isVisible());
        }

        private void waitForNode(String query) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        }



