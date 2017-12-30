package com.seimos.commons.reflection;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author moesio.medeiros
 * @date 30 de dez de 2017 00:02:08 
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface GenerateLayers {
}
