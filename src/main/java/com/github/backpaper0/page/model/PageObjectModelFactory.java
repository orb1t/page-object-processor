package com.github.backpaper0.page.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

public final class PageObjectModelFactory {

    private final ProcessingEnvironment processingEnv;

    public PageObjectModelFactory(final ProcessingEnvironment processingEnv) {
        this.processingEnv = Objects.requireNonNull(processingEnv);
    }

    public PageObjectModel create(final TypeElement pageBase) {

        final CharSequence packageName = TypeCasts
                .asPackageElement(pageBase.getEnclosingElement()).getQualifiedName();

        final CharSequence baseClass = pageBase.getQualifiedName();

        final CharSequence classSimpleName = pageBase.getSimpleName() + "Page";

        final List<CharSequence> fields = collectMembers(pageBase, ElementFilter::fieldsIn,
                VariableElement::asType);

        final List<CharSequence> methods = collectMembers(pageBase, ElementFilter::methodsIn,
                ExecutableElement::getReturnType);

        return new PageObjectModel(pageBase, packageName, classSimpleName, baseClass, fields,
                methods);
    }

    private <T extends Element> List<CharSequence> collectMembers(final TypeElement pageBase,
            final Function<Iterable<? extends Element>, List<T>> f,
            final Function<T, TypeMirror> g) {
        final List<CharSequence> members = new ArrayList<>();
        for (final T member : f.apply(pageBase.getEnclosedElements())) {
            final DeclaredType declaredType = TypeCasts.asDeclaredType(g.apply(member));
            if (declaredType != null) {
                final TypeElement typeElement = TypeCasts.asTypeElement(declaredType.asElement());
                if (typeElement.equals(processingEnv.getElementUtils()
                        .getTypeElement("com.codeborne.selenide.SelenideElement"))) {
                    final CharSequence name = member.getSimpleName();
                    if (name != null) {
                        members.add(name);
                    }
                }
            }
        }
        return members;
    }
}
