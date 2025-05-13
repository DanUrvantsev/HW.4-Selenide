package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    private String generateDate(int daysToAdd, String pattern) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSubmitFormWithDirectInput() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Новосибирск");
        String deliveryDate = generateDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME),Keys.DELETE);
        $("[data-test-id='date'] input").doubleClick().sendKeys(deliveryDate);
        $("[data-test-id='name'] input").setValue("Анна-Мария Петрова");
        $("[data-test-id='phone'] input").setValue("+79211234567");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content]").should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на" + deliveryDate));
    }
}
