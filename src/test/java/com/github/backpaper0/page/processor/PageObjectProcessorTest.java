package com.github.backpaper0.page.processor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.tools.JavaFileObject;

import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

public class PageObjectProcessorTest {

    @Test
    public void test() throws Exception {

        final JavaFileObject javaFile = javaFile("sample.Sample1");

        final PageObjectProcessor processor = new PageObjectProcessor();

        final Compilation compilation = Compiler.javac()
                .withProcessors(processor)
                .compile(javaFile);

        CompilationSubject.assertThat(compilation)
                .generatedSourceFile("sample.Sample1Page")
                .hasSourceEquivalentTo(javaFile("sample.Sample1Page"));
    }

    static JavaFileObject javaFile(final String fullyQualifiedName) throws IOException {
        final String source = new String(Files.readAllBytes(
                Paths.get("src/test/resources/" + fullyQualifiedName.replace('.', '/') + ".java")),
                StandardCharsets.UTF_8);
        return JavaFileObjects.forSourceString(fullyQualifiedName, source);
    }
}
