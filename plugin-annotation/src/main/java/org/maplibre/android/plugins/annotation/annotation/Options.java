package org.maplibre.android.plugins.annotation.annotation;

public abstract class Options<T extends Annotation> {

    abstract T build(long id, AnnotationManager<?, T, ?, ?, ?, ?> annotationManager);
}
