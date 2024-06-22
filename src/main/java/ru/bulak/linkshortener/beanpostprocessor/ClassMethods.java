package ru.bulak.linkshortener.beanpostprocessor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.List;

@Getter
@AllArgsConstructor
public class ClassMethods {
    private final Class<?> clazz;
    private final List<Method> methods;
}
