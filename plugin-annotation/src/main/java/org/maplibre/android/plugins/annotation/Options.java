package org.maplibre.android.plugins.annotation;

public abstract class Options<T extends Annotation> {

  abstract T build(long id, AnnotationManager<?, T, ?, ?, ?, ?> annotationManager);
}
