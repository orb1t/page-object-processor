package com.github.backpaper0.page.model;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.lang.model.util.SimpleTypeVisitor8;

public final class TypeCasts {

    public static DeclaredType asDeclaredType(final TypeMirror typeMirror) {
        return typeMirror.accept(new SimpleTypeVisitor8<DeclaredType, Void>() {
            @Override
            public DeclaredType visitDeclared(final DeclaredType t, final Void p) {
                return t;
            }
        }, null);
    }

    public static TypeElement asTypeElement(final Element element) {
        return element.accept(new SimpleElementVisitor8<TypeElement, Void>() {
            @Override
            public TypeElement visitType(final TypeElement e, final Void p) {
                return e;
            }
        }, null);
    }

    public static PackageElement asPackageElement(final Element element) {
        return element.accept(new SimpleElementVisitor8<PackageElement, Void>() {
            @Override
            public PackageElement visitPackage(final PackageElement e, final Void p) {
                return e;
            }
        }, null);
    }
}
