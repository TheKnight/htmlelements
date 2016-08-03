package io.qameta.htmlelements.decorator;

import io.qameta.htmlelements.locator.Annotations;
import org.openqa.selenium.SearchContext;

import java.lang.reflect.Method;

public interface MethodDecorator {

    boolean canDecorate(Method method);

    Object decorate(SearchContext searchContext, Annotations annotations);

}
