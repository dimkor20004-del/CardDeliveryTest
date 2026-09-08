package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;

public class CardDeliveryTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    private String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void testValidCardDeliveryOrder() {
        String planningDate = generateDate(4);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();

        sleep(3000);
        $(".notification__title").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(30));
    }

    @Test
    public void testInvalidCityShowsError() {
        String planningDate = generateDate(4);
        System.out.println("Дата: " + planningDate);

        $("[data-test-id='city'] input").setValue("Лондон");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();

        $("[data-test-id='city'].input_invalid").shouldBe(Condition.visible);
    }

    @Test
    public void testInvalidNameShowsError() {
        String planningDate = generateDate(4);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Ivanov Ivan");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();

        $("[data-test-id='name'].input_invalid").shouldBe(Condition.visible);
    }

    @Test
    public void testInvalidPhoneShowsError() {
        String planningDate = generateDate(4);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("89999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.text("Забронировать")).click();

        $("[data-test-id='phone'].input_invalid").shouldBe(Condition.visible);
    }

    @Test
    public void testWithoutCheckboxShowsError() {
        String planningDate = generateDate(4);

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $$("button").findBy(Condition.text("Забронировать")).click();

        $("[data-test-id='agreement'].input_invalid").shouldBe(Condition.visible);
    }
}