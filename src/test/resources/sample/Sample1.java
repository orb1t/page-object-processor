package sample;

import org.openqa.selenium.support.FindBy;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.backpaper0.page.PageObject;

@PageObject
public abstract class Sample1 {

    @FindBy(css = "#foo")
    SelenideElement foo;

    @FindBy(css = "#bar")
    SelenideElement bar;

    SelenideElement baz() {
        return Selenide.$("#baz");
    }

    String qux;

    String qux() {
        return null;
    }
}
