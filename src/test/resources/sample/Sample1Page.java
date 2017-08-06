package sample;

public class Sample1Page extends sample.Sample1 {

    public Sample1Page fooを設定する(final java.lang.String text) {
        foo.val(text);
        return this;
    }

    public Sample1Page fooをクリックする() {
        foo.click();
        return this;
    }

    public Sample1Page fooを検証する(final com.codeborne.selenide.Condition... condition) {
        foo.should(condition);
        return this;
    }

    public Sample1Page barを設定する(final java.lang.String text) {
        bar.val(text);
        return this;
    }

    public Sample1Page barをクリックする() {
        bar.click();
        return this;
    }

    public Sample1Page barを検証する(final com.codeborne.selenide.Condition... condition) {
        bar.should(condition);
        return this;
    }

    public Sample1Page bazを設定する(final java.lang.String text) {
        baz().val(text);
        return this;
    }

    public Sample1Page bazをクリックする() {
        baz().click();
        return this;
    }

    public Sample1Page bazを検証する(final com.codeborne.selenide.Condition... condition) {
        baz().should(condition);
        return this;
    }
}
