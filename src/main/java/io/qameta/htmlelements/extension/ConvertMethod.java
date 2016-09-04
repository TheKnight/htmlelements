package io.qameta.htmlelements.extension;

import io.qameta.htmlelements.context.Context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@HandleWith(ConvertMethod.Extension.class)
@ExtendWith(ConvertMethod.Extension.class)
public @interface ConvertMethod {

    class Extension implements ContextEnricher, TargetModifier<List>, MethodHandler {

        static final String CONVERTER_KEY = "convert";

        @Override
        public void enrich(Context context, Method method, Object[] args) {
            context.getStore().put(CONVERTER_KEY, (Function) o -> o);
        }

        @Override
        @SuppressWarnings("unchecked")
        public List modify(Context context, List target) {
            Function converter = (Function) context.getStore().get(CONVERTER_KEY);
            return (List) target.stream().map(converter).collect(Collectors.toList());
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object handle(Context context, Object proxy, Object[] args) {
            Function converter = (Function) context.getStore().get(CONVERTER_KEY);
            context.getStore().put(CONVERTER_KEY, converter.andThen((Function) args[0]));
            return proxy;
        }
    }

}