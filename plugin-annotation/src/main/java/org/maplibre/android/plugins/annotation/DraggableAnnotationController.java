package org.maplibre.android.plugins.annotation;

import android.annotation.SuppressLint;
import android.graphics.PointF;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;

import org.maplibre.android.gestures.AndroidGesturesManager;
import org.maplibre.android.gestures.MoveDistancesObject;
import org.maplibre.android.gestures.MoveGestureDetector;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.MapView;
import org.maplibre.geojson.Geometry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@UiThread
final class DraggableAnnotationController {

    private static DraggableAnnotationController INSTANCE = null;

    public static DraggableAnnotationController getInstance(MapView mapView, MapLibreMap maplibreMap) {
        if (INSTANCE == null || INSTANCE.mapView != mapView || INSTANCE.maplibreMap != maplibreMap) {
            INSTANCE = new DraggableAnnotationController(mapView, maplibreMap);
        }
        return INSTANCE;
    }

    private static void clearInstance() {
        if (INSTANCE != null) {
            INSTANCE.mapView = null;
            INSTANCE.maplibreMap = null;
            INSTANCE = null;
        }
    }

    private MapView mapView;
    private MapLibreMap maplibreMap;
    private List<AnnotationManager> annotationManagers = new LinkedList<>();
    private HashMap<String, AnnotationManager> annotationManagersById = new HashMap<>();

    private final int touchAreaShiftX;
    private final int touchAreaShiftY;
    private final int touchAreaMaxX;
    private final int touchAreaMaxY;

    @Nullable
    private Annotation draggedAnnotation;
    @Nullable
    private AnnotationManager draggedAnnotationManager;

    private AndroidGesturesManager androidGesturesManager;

    @SuppressLint("ClickableViewAccessibility")
    DraggableAnnotationController(MapView mapView, MapLibreMap maplibreMap) {
        this(mapView, maplibreMap, new AndroidGesturesManager(mapView.getContext(), false),
            mapView.getScrollX(), mapView.getScrollY(), mapView.getMeasuredWidth(), mapView.getMeasuredHeight());
    }

    @VisibleForTesting
    public DraggableAnnotationController(MapView mapView, MapLibreMap maplibreMap,
                                         final AndroidGesturesManager androidGesturesManager,
                                         int touchAreaShiftX, int touchAreaShiftY,
                                         int touchAreaMaxX, int touchAreaMaxY) {
        this.mapView = mapView;
        this.maplibreMap = maplibreMap;
        this.touchAreaShiftX = touchAreaShiftX;
        this.touchAreaShiftY = touchAreaShiftY;
        this.touchAreaMaxX = touchAreaMaxX;
        this.touchAreaMaxY = touchAreaMaxY;

        this.androidGesturesManager = androidGesturesManager;
    }

    void setEnable(boolean enable) {
        if (enable) {
            androidGesturesManager.setMoveGestureListener(new AnnotationMoveGestureListener());

            mapView.setOnTouchListener((v, event) -> {
                // Using active gesture manager
                Annotation oldAnnotation = draggedAnnotation;
                androidGesturesManager.onTouchEvent(event);
                // if drag is started or drag is finished, don't pass motion events further
                boolean t = draggedAnnotation != null || oldAnnotation != null;
                return t;
            });
        } else {
            androidGesturesManager.removeMoveGestureListener();
            mapView.setOnTouchListener(null);
        }
    }

    void addAnnotationManager(AnnotationManager annotationManager) {
        if (annotationManager.getBelowLayerId() != null) {
            // Insert above the corresponding id
            AnnotationManager below = annotationManagersById.get(annotationManager.getBelowLayerId());
            int belowIndex = annotationManagers.indexOf(below);
            annotationManagers.add(belowIndex + 1, annotationManager);
        } else if (annotationManager.getAboveLayerId() != null) {
            // Insert below the corresponding id
            AnnotationManager above = annotationManagersById.get(annotationManager.getAboveLayerId());
            int aboveIndex = annotationManagers.indexOf(above);
            annotationManagers.add(aboveIndex, annotationManager);
        } else {
            // Insert at the beginning
            this.annotationManagers.add(0, annotationManager);
        }

        annotationManagersById.put(annotationManager.getLayerId(), annotationManager);
    }

    void removeAnnotationManager(AnnotationManager annotationManager) {
        this.annotationManagers.remove(annotationManager);
        this.annotationManagersById.remove(annotationManager.getLayerId());
        if (annotationManagers.isEmpty()) {
            clearInstance();
        }
    }

    void onAnnotationDeleted(Annotation annotation) {
        if (annotation == draggedAnnotation) {
            stopDragging(draggedAnnotation, draggedAnnotationManager);
        }
    }

    boolean onMoveBegin(MoveGestureDetector detector) {
        for (AnnotationManager annotationManager : annotationManagers) {
            if (detector.getPointersCount() == 1) {
                Annotation annotation = annotationManager.queryMapForFeatures(detector.getFocalPoint());
                if (annotation != null && startDragging(annotation, annotationManager)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean onMove(MoveGestureDetector detector) {
        if (draggedAnnotation != null && (detector.getPointersCount() > 1 || !draggedAnnotation.isDraggable())) {
            // Stopping the drag when we don't work with a simple, on-pointer move anymore
            stopDragging(draggedAnnotation, draggedAnnotationManager);
            return true;
        }

        // Updating symbol's position
        if (draggedAnnotation != null) {
            MoveDistancesObject moveObject = detector.getMoveObject(0);

            float x = moveObject.getCurrentX() - touchAreaShiftX;
            float y = moveObject.getCurrentY() - touchAreaShiftY;

            PointF pointF = new PointF(x, y);

            if (pointF.x < 0 || pointF.y < 0 || pointF.x > touchAreaMaxX || pointF.y > touchAreaMaxY) {
                stopDragging(draggedAnnotation, draggedAnnotationManager);
                return true;
            }

            Geometry shiftedGeometry = draggedAnnotation.getOffsetGeometry(
                maplibreMap.getProjection(), moveObject, touchAreaShiftX, touchAreaShiftY
            );

            if (shiftedGeometry != null) {
                draggedAnnotation.setGeometry(
                    shiftedGeometry
                );
                draggedAnnotationManager.updateSource();
                for (OnAnnotationDragListener d : (List<OnAnnotationDragListener>) draggedAnnotationManager.getDragListeners()) {
                    d.onAnnotationDrag(draggedAnnotation);
                }
                return true;
            }
        }

        return false;
    }

    void onMoveEnd() {
        // Stopping the drag when move ends
        stopDragging(draggedAnnotation, draggedAnnotationManager);
    }

    boolean startDragging(@NonNull Annotation annotation, @NonNull AnnotationManager annotationManager) {
        if (annotation.isDraggable()) {
            for (OnAnnotationDragListener d : (List<OnAnnotationDragListener>) annotationManager.getDragListeners()) {
                d.onAnnotationDragStarted(annotation);
            }
            draggedAnnotation = annotation;
            draggedAnnotationManager = annotationManager;
            return true;
        }
        return false;
    }

    void stopDragging(@Nullable Annotation annotation, @Nullable AnnotationManager annotationManager) {
        if (annotation != null && annotationManager != null) {
            for (OnAnnotationDragListener d : (List<OnAnnotationDragListener>) annotationManager.getDragListeners()) {
                d.onAnnotationDragFinished(annotation);
            }
        }
        draggedAnnotation = null;
        draggedAnnotationManager = null;
    }

    private class AnnotationMoveGestureListener implements MoveGestureDetector.OnMoveGestureListener {

        @Override
        public boolean onMoveBegin(MoveGestureDetector detector) {
            return DraggableAnnotationController.this.onMoveBegin(detector);
        }

        @Override
        public boolean onMove(MoveGestureDetector detector, float distanceX, float distanceY) {
            return DraggableAnnotationController.this.onMove(detector);
        }

        @Override
        public void onMoveEnd(MoveGestureDetector detector, float velocityX, float velocityY) {
            DraggableAnnotationController.this.onMoveEnd();
        }
    }
}
