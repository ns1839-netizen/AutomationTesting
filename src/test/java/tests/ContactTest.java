package tests;

import Pages.ContactPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ContactTest extends Testbase {

    ContactPage contactPage;

    // Assumptions:
    // - Required fields : Name, Email, Message
    // - Optional fields : Subject, File Upload
    // - Max file size   : 5 MB
    // - Allowed types for  file  : pdf, jpg, png
    // - Max message len : 2000 characters

    @BeforeMethod
    public void goToContactPage() {
        contactPage = new ContactPage(driver);
        driver.navigate().to("https://automationexercise.com/contact_us");
    }

    //  create a temp file
    private String createTempFile(String fileName, long sizeInBytes) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir"), fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            long remaining = sizeInBytes;
            while (remaining > 0) {
                int chunk = (int) Math.min(buffer.length, remaining);
                fos.write(buffer, 0, chunk);
                remaining -= chunk;
            }
        }
        return file.getAbsolutePath();
    }

    // CONT_1
    // Scenario : Submit with all valid data
    // Expected : "Success! Your details have been submitted successfully."
    // Status   : PASS
    @Test
    public void CONT_1_validSubmission() {
        contactPage.submitForm(
                "Ahmed Ali",
                "ahmed.ali@gmail.com",
                "Inquiry about services",
                "I would like to know more about your services"
        );

        String actual = contactPage.getSuccessMessage();
        System.out.println("CONT_1 actual: " + actual);
        Assert.assertTrue(
                actual.contains("Success! Your details have been submitted successfully."),
                "Expected success message but got: " + actual );

    }

    // CONT_2
    // Scenario : Empty Name (Name is required per assumption)
    // Expected : "Name is Required." — form should NOT submit
    // BUG    : App accepts empty name and submits successfully
    @Test
    public void CONT_2_emptyName() {
        // leave name
        contactPage.enterEmail("ahmed.ali@gmail.com");
        contactPage.enterSubject("Support");
        contactPage.enterMessage("I need help with my account");
        contactPage.clickSubmit();

        boolean submitted;
        try {
            contactPage.acceptAlert();
            submitted = contactPage.isSuccessMessageVisible();
        } catch (Exception e) {
            submitted = false;
        }

        //  BUG: form submits even with empty Name
        Assert.assertFalse(submitted,
                "BUG: Form should NOT submit when Name is empty, but it did!");
    }


//    @Override
//    public void testSomething() {
//        super.testSomething();
//    }

    // CONT_3
    // Scenario : Completely empty form
    // Expected : Browser validation error — form should NOT submit
    // Status   : PASS  (Email has required="required" so browser blocks it)
    @Test
    public void CONT_3_emptyFormSubmission() {
        contactPage.clickSubmit();

        // Browser blocks submission because Email is required
        Assert.assertFalse(
                contactPage.isSuccessMessageVisible(),
                "Form should NOT submit when all fields are empty"
        );
    }

    // CONT_4
    // Scenario : Empty Email
    // Expected : "Email is Required." — browser validation blocks submit
    // Status   : PASS
    @Test
    public void CONT_4_emptyEmail() {
        contactPage.enterName("Sara Mohamed");
        // leave email
        contactPage.enterSubject("Feedback");
        contactPage.enterMessage("Your website is great");
        contactPage.clickSubmit();

        // Browser required="required" on email → submission blocked
        Assert.assertFalse(
                contactPage.isSuccessMessageVisible(),
                "Form should NOT submit when Email is empty"
        );
    }

    // CONT_5
    // Scenario : Invalid email format (missing @)
    // Expected : "please Enter Valid Email" — browser validation blocks submit
    // Status   : PASS
    @Test
    public void CONT_5_invalidEmailFormat() {
        contactPage.enterName("Omar Hassen");
        contactPage.enterEmail("omar.gmail.com");
        contactPage.enterSubject("Complaint");
        contactPage.enterMessage("I faced an issue through using");
        contactPage.clickSubmit();

        // 1) Email field is flagged as invalid by the browser
        Assert.assertTrue(
                contactPage.isEmailFieldInvalid(),
                "Email 'omar.gmail.com' should be flagged as invalid"
        );

        // 2) Form was NOT submitted
        Assert.assertFalse(
                contactPage.isSuccessMessageVisible(),
                "Form should NOT submit with invalid email format"
        );
    }

    // CONT_6
    // Scenario : Empty Subject (Subject is optional per assumption)
    // Expected : "Success! Your details have been submitted successfully."
    // Status   : PAS
    @Test
    public void CONT_6_emptySubject() {
        contactPage.enterName("Mona Adel");
        contactPage.enterEmail("mona.adel@gmail.com");
        // LEAVE SUBJECT
        contactPage.enterMessage("I faced an issue through using");
        contactPage.clickSubmit();
        contactPage.acceptAlert();

        String actual = contactPage.getSuccessMessage();
        System.out.println("CONT_6 actual: " + actual);
        Assert.assertTrue(
                actual.contains("Success! Your details have been submitted successfully."),
                "Expected success with empty optional subject but got: " + actual
        );
    }

    //  CONT_7
    // Scenario : Empty Message (Message is required per assumption)
    // Expected : "Please Enter Message, this Message is Required"
    // BUG   : App accepts empty message and submits successfully
    @Test
    public void CONT_7_emptyMessage() {
        contactPage.enterName("Karim Nabil");
        contactPage.enterEmail("karim.nabil@gmail.com");
        contactPage.enterSubject("Request");
        // leave message
        contactPage.clickSubmit();

        boolean submitted;
        try {
            contactPage.acceptAlert();
            submitted = contactPage.isSuccessMessageVisible();
        } catch (Exception e) {
            submitted = false;
        }

        //  BUG: form submits even with empty Message
        Assert.assertFalse(submitted,
                "BUG: Form should NOT submit when Message is empty, but it did!");
    }

    // CONT_8
    // Scenario : Upload valid PDF file (2 MB — within 5 MB limit)
    // Expected : "Success! Your details have been submitted successfully."
    // Status   : PASS
    @Test
    public void CONT_8_uploadValidFile() throws IOException {
        String filePath = createTempFile("file.pdf", 1024L * 1024 * 2); // 2 MB
        System.out.println("CONT_8 file: " + filePath);

        contactPage.submitFormWithFile(
                "Ahmed Ali",
                "ahmed.ali@gmail.com",
                "Uploade File ",
                "Please find attached file",
                filePath
        );

        String actual = contactPage.getSuccessMessage();
        System.out.println("CONT_8 actual: " + actual);
        Assert.assertTrue(
                actual.contains("Success! Your details have been submitted successfully."),
                "Expected success with valid PDF but got: " + actual
        );
    }

    // CONT_9
    // Scenario : Upload invalid file type (.exe — only pdf/jpg/png allowed)
    // Expected : "The FileType Is Invalid" — form should NOT submit
    // BUG : App accepts .exe and submits successfully
    @Test
    public void CONT_9_uploadInvalidFileType() throws IOException {
        String filePath = createTempFile("virus.exe", 1024); // 1 KB
        System.out.println("CONT_9 file: " + filePath);

        contactPage.enterName("Ahmed Ali");
        contactPage.enterEmail("ahmed.ali@gmail.com");
        contactPage.enterSubject("File test");
        contactPage.enterMessage("Testing invalid file");
        contactPage.uploadFile(filePath);
        contactPage.clickSubmit();

        boolean submitted;
        try {
            contactPage.acceptAlert();
            submitted = contactPage.isSuccessMessageVisible();
        } catch (Exception e) {
            submitted = false;
        }

        // BUG: App accepts .exe — no file-type validation exists
        Assert.assertFalse(submitted,
                "BUG: Form should NOT accept .exe file type, but it did!");
    }

    // CONT_10
    // Scenario : Upload large file (25 MB — exceeds 5 MB limit)
    // Expected : "File Size Exceeds Limit" — form should NOT submit
    // BUG  : App accepts 25 MB and submits successfully
    @Test
    public void CONT_10_uploadLargeFile() throws IOException {
        String filePath = createTempFile("File_25MB.pdf", 1024L * 1024 * 25); // 25 MB
        System.out.println("CONT_10 file: " + filePath);

        contactPage.enterName("Ahmed Ali");
        contactPage.enterEmail("ahmed.ali@gmail.com");
        contactPage.enterSubject("Large file");
        contactPage.enterMessage("Testing large file upload");
        contactPage.uploadFile(filePath);
        contactPage.clickSubmit();

        boolean submitted;
        try {
            contactPage.acceptAlert();
            submitted = contactPage.isSuccessMessageVisible();
        } catch (Exception e) {
            submitted = false;
        }

        // BUG: App accepts files larger than 5 MB — no size validation exists
        Assert.assertFalse(submitted,
                "BUG: Form should NOT accept files > 5 MB, but it did!");
    }

    // CONT_11
    // Scenario : Email with  spaces
    // Expected : "Success! Your details have been submitted successfully."
    //            (browser trims spaces before validation)
    // Status   : PASS
    @Test
    public void CONT_11_emailWithSpaces() {
        contactPage.enterName("Ahmed Ali");
        contactPage.enterEmail("   ahmed.ali@gmail.com"); // leading spaces
        contactPage.enterSubject("inquiry");
        contactPage.enterMessage("hello");
        contactPage.clickSubmit();

        boolean submitted;
        try {
            contactPage.acceptAlert();
            submitted = contactPage.isSuccessMessageVisible();
        } catch (Exception e) {
            submitted = false;
        }

        String actual = submitted ? contactPage.getSuccessMessage() : "not submitted";
        System.out.println("CONT_11 actual: " + actual);
        Assert.assertTrue(submitted,
                "Expected form to submit successfully with spaces in email but got: " + actual
        );
    }

    // CONT_12
    // Scenario : Very long message (> 20000 chars — exceeds 2000 char limit)
    // Expected : "Message is very long." — form should NOT submit
    // BUG   : App likely accepts any length — no max length validation
    @Test
    public void CONT_12_veryLongMessage() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20001; i++) {
            sb.append("A");
        }
        String longMessage = sb.toString(); // 20001 chars > 2000 limit

        contactPage.enterName("Ahmed Ali");
        contactPage.enterEmail("ahmed.ali@gmail.com");
        contactPage.enterSubject("Long message test");
        contactPage.enterMessage(longMessage);
        contactPage.clickSubmit();

        boolean submitted;
        try {
            contactPage.acceptAlert();
            submitted = contactPage.isSuccessMessageVisible();
        } catch (Exception e) {
            submitted = false;
        }

        // BUG: App should reject messages longer than 2000 chars
        Assert.assertFalse(submitted,
                "BUG: Form should NOT accept messages longer than 2000 characters, but it did!");
    }

    // CONT_13
    // Scenario : Special characters in Message (@#$)
    // Expected : System accepts input and submits successfully
    // Status   : PASS
    @Test
    public void CONT_13_specialCharactersInMessage() {
        contactPage.submitForm(
                "Ali Ahmed",
                "ahmed.ali@gmail.com",
                "inquiry",
                "@#$"
        );

        String actual = contactPage.getSuccessMessage();
        System.out.println("CONT_13 actual: " + actual);
        Assert.assertTrue(
                actual.contains("Success! Your details have been submitted successfully."),
                "Expected success with special characters but got: " + actual
        );
    }
}