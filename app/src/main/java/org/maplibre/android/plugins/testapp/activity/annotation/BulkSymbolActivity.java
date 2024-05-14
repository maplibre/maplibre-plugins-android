package org.maplibre.android.plugins.testapp.activity.annotation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.maplibre.android.plugins.annotation.Symbol;
import org.maplibre.android.plugins.annotation.SymbolManager;
import org.maplibre.android.plugins.annotation.SymbolOptions;
import org.maplibre.android.plugins.testapp.R;
import org.maplibre.android.plugins.testapp.BuildConfig;
import org.maplibre.android.plugins.testapp.R;import org.maplibre.android.plugins.testapp.Utils;

import org.maplibre.android.MapLibre;
import org.maplibre.android.WellKnownTileServer;
import org.maplibre.android.camera.CameraUpdateFactory;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.MapView;
import org.maplibre.android.maps.Style;
import org.maplibre.geojson.Feature;
import org.maplibre.geojson.FeatureCollection;
import org.maplibre.geojson.Point;

import timber.log.Timber;

import java.io.*;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Test activity showcasing adding a large amount of Symbols.
 */
public class BulkSymbolActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String IMAGE_ID_FIRE_HYDRANT = "fire-hydrant";

    private SymbolManager symbolManager;
    private List<Symbol> symbols = new ArrayList<>();

    private MapLibreMap mapLibreMap;
    private MapView mapView;
    private FeatureCollection locations;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        MapLibre.getInstance(this, BuildConfig.MAPTILER_API_KEY, WellKnownTileServer.MapTiler);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this::initMap);
    }

    private void initMap(MapLibreMap mapLibreMap) {
        this.mapLibreMap = mapLibreMap;
        mapLibreMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                new LatLng(38.87031, -77.00897), 10
            )
        );

        mapLibreMap.setStyle(new Style.Builder()
                .fromUri(Style.getPredefinedStyle("Streets"))
                .withImage(IMAGE_ID_FIRE_HYDRANT, getDrawable(R.drawable.ic_fire_hydrant)),
            style -> {
                findViewById(R.id.fabStyles).setOnClickListener(v -> mapLibreMap.setStyle(Utils.INSTANCE.getNextStyle()));
                symbolManager = new SymbolManager(mapView, mapLibreMap, style);
                symbolManager.setIconAllowOverlap(true);
                loadData(0);
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
            this, R.array.bulk_marker_list, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getMenuInflater().inflate(R.menu.menu_bulk_symbol, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(BulkSymbolActivity.this);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mapLibreMap.getStyle() == null || !mapLibreMap.getStyle().isFullyLoaded()) {
            return;
        }

        loadData(position);
    }

    private void loadData(int position) {
        int amount = Integer.valueOf(getResources().getStringArray(R.array.bulk_marker_list)[position]);
        if (locations == null) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(this, "Loading", "Fetching markers", false, true);
            }
            new LoadLocationTask(this, amount).execute();
        } else {
            showMarkers(amount);
        }
    }

    private void onLatLngListLoaded(FeatureCollection featureCollection, int amount) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        locations = featureCollection;
        showMarkers(amount);
    }

    private void showMarkers(int amount) {
        Timber.i("Showing markers");
        if (mapLibreMap == null || locations == null || locations.features() == null || mapView.isDestroyed()) {
            Timber.i("Not showing markers");
            return;
        }

        // delete old symbols
        symbolManager.delete(symbols);

        if (locations.features().size() < amount) {
            amount = locations.features().size();
        }

        showSymbols(amount);
    }

    private void showSymbols(int amount) {
        List<SymbolOptions> options = new ArrayList<>();
        Random random = new Random();
        int randomIndex;

        List<Feature> features = locations.features();
        if (features == null) {
            Timber.i("No features");
            return;
        }

        for (int i = 0; i < amount; i++) {
            randomIndex = random.nextInt(features.size());
            Feature feature = features.get(randomIndex);
            options.add(new SymbolOptions()
                .withGeometry((Point) feature.geometry())
                .withIconImage(IMAGE_ID_FIRE_HYDRANT)
            );
        }
        symbols = symbolManager.create(options);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // nothing selected, nothing to do!
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (symbolManager != null) {
            symbolManager.onDestroy();
        }

        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private static class LoadLocationTask extends AsyncTask<Void, Integer, FeatureCollection> {

        private WeakReference<BulkSymbolActivity> activity;
        private int amount;

        private LoadLocationTask(BulkSymbolActivity activity, int amount) {
            this.amount = amount;
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected FeatureCollection doInBackground(Void... params) {
            BulkSymbolActivity activity = this.activity.get();
            if (activity != null) {
                String json = null;
                try {
                    json = GeoParseUtil.loadStringFromAssets(activity.getApplicationContext());
                } catch (IOException exception) {
                    Timber.e(exception, "Could not add markers");
                }

                if (json != null) {
                    return FeatureCollection.fromJson(json);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(FeatureCollection locations) {
            super.onPostExecute(locations);
            BulkSymbolActivity activity = this.activity.get();
            if (activity != null) {
                activity.onLatLngListLoaded(locations, amount);
            }
        }
    }

    public static class GeoParseUtil {

        static String loadStringFromAssets(final Context context) throws IOException {
            InputStream is = context.getAssets().open("points.geojson");
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readAll(rd);
        }

        private static String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }
    }
}
