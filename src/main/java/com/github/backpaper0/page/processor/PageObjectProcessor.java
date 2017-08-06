package com.github.backpaper0.page.processor;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

import com.github.backpaper0.page.PageObject;
import com.github.backpaper0.page.model.PageObjectModel;
import com.github.backpaper0.page.model.PageObjectModelFactory;

public class PageObjectProcessor extends AbstractProcessor {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
            final RoundEnvironment roundEnv) {

        if (roundEnv.processingOver()) {
            return false;
        }

        final PageObjectModelFactory factory = new PageObjectModelFactory(processingEnv);

        final List<PageObjectModel> models = new ArrayList<>();
        for (final TypeElement a : annotations) {
            for (final TypeElement pageBase : ElementFilter
                    .typesIn(roundEnv.getElementsAnnotatedWith(a))) {
                final PageObjectModel model = factory.create(pageBase);
                models.add(model);
            }
        }

        for (final PageObjectModel model : models) {
            final String source = model.generate();
            try {
                final JavaFileObject file = model.createSourceFile(processingEnv.getFiler());
                try (Writer writer = new OutputStreamWriter(file.openOutputStream(),
                        StandardCharsets.UTF_8)) {
                    writer.write(source);
                }
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(PageObject.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}
