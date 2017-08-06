package com.github.backpaper0.page.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

import javax.annotation.processing.Filer;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

public final class PageObjectModel {

    private final TypeElement pageBase;
    private final CharSequence packageName;
    private final CharSequence classSimpleName;
    private final CharSequence baseClass;
    private final List<CharSequence> fields;
    private final List<CharSequence> methods;

    public PageObjectModel(final TypeElement pageBase, final CharSequence packageName,
            final CharSequence classSimpleName,
            final CharSequence baseClass, final List<CharSequence> fields,
            final List<CharSequence> methods) {
        this.pageBase = Objects.requireNonNull(pageBase);
        this.packageName = Objects.requireNonNull(packageName);
        this.classSimpleName = Objects.requireNonNull(classSimpleName);
        this.baseClass = Objects.requireNonNull(baseClass);
        this.fields = Objects.requireNonNull(fields);
        this.methods = Objects.requireNonNull(methods);
    }

    public JavaFileObject createSourceFile(final Filer filer) throws IOException {
        return filer.createSourceFile(packageName + "." + classSimpleName, pageBase);
    }

    public String generate() {
        final StringWriter s = new StringWriter();
        final PrintWriter out = new PrintWriter(s);

        out.printf("package %1$s;%n", packageName);

        out.printf("%n");
        out.printf("public class %1$s extends %2$s {%n", classSimpleName, baseClass);

        for (final CharSequence field : fields) {
            final String source = generate(field, field);
            out.print(source);
        }

        for (final CharSequence method : methods) {
            final String source = generate(method, method + "()");
            out.print(source);
        }

        out.printf("}%n");
        out.flush();
        return s.toString();
    }

    private String generate(final CharSequence name, final CharSequence ref) {
        final StringWriter s = new StringWriter();
        final PrintWriter out = new PrintWriter(s);
        out.printf("%n");
        out.printf("    public %1$s %2$sを設定する(final %3$s text) {%n", classSimpleName, name,
                String.class.getName());
        out.printf("        %1$s.val(text);%n", ref);
        out.printf("        return this;%n");
        out.printf("    }%n");

        out.printf("%n");
        out.printf("    public %1$s %2$sをクリックする() {%n", classSimpleName, name,
                String.class.getName());
        out.printf("        %1$s.click();%n", ref);
        out.printf("        return this;%n");
        out.printf("    }%n");

        out.printf("%n");
        out.printf(
                "    public %1$s %2$sを検証する(final com.codeborne.selenide.Condition ... condition) {%n",
                classSimpleName, name);
        out.printf("        %1$s.should(condition);%n", ref);
        out.printf("        return this;%n");
        out.printf("    }%n");
        out.flush();
        return s.toString();
    }
}
