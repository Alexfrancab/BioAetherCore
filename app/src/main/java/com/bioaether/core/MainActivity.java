package com.bioaether.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private static final int FILE_CHOOSER_REQUEST = 100;
    private ValueCallback<Uri[]> filePathCallback;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Pantalla completa sin ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // StatusBar transparente con fondo oscuro
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#050b12"));
        window.setNavigationBarColor(Color.parseColor("#050b12"));

        // Inmersivo suave
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false);
        } else {
            window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }

        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);

        // ── Configuración de WebView ──────────────────────────
        WebSettings settings = webView.getSettings();

        // JavaScript habilitado
        settings.setJavaScriptEnabled(true);

        // Soporte DOM storage (para localStorage)
        settings.setDomStorageEnabled(true);

        // Base de datos local
        settings.setDatabaseEnabled(true);

        // Zoom desactivado (app nativa feel)
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        // Viewport responsive
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Caché
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // Rendering de alta calidad
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        // Acceso a archivos locales (assets)
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        // UserAgent personalizado
        settings.setUserAgentString(
            "BioAetherCore/4.0 Android/" + Build.VERSION.RELEASE
        );

        // Aceleración hardware
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        // Fondo negro para evitar flash blanco al cargar
        webView.setBackgroundColor(Color.parseColor("#050b12"));

        // ── WebViewClient: manejo de navegación ──────────────
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                // Links externos → abrir en navegador del sistema
                if (!url.startsWith("file://") && !url.startsWith("about:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Ocultar splash cuando la página termina de cargar
                view.setVisibility(View.VISIBLE);
            }
        });

        // ── WebChromeClient: consola JS + file picker ─────────
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage msg) {
                // En debug podrías logear: Log.d("BAC_JS", msg.message())
                return true;
            }

            // Soporte para input type="file"
            @Override
            public boolean onShowFileChooser(WebView webView,
                    ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams) {
                if (MainActivity.this.filePathCallback != null) {
                    MainActivity.this.filePathCallback.onReceiveValue(null);
                }
                MainActivity.this.filePathCallback = filePathCallback;
                Intent intent = fileChooserParams.createIntent();
                try {
                    startActivityForResult(intent, FILE_CHOOSER_REQUEST);
                } catch (Exception e) {
                    MainActivity.this.filePathCallback = null;
                    return false;
                }
                return true;
            }
        });

        // ── Cargar la app desde assets ────────────────────────
        if (savedInstanceState == null) {
            webView.loadUrl("file:///android_asset/www/index.html");
        }
    }

    // ── Botón atrás: navegar dentro de la WebView ─────────────
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    // ── Pausar/reanudar WebView con la Activity ───────────────
    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        webView.pauseTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        webView.resumeTimers();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
        }
        super.onDestroy();
    }

    // ── Resultado del file picker ─────────────────────────────
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_REQUEST) {
            if (filePathCallback != null) {
                Uri[] results = null;
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
                filePathCallback.onReceiveValue(results);
                filePathCallback = null;
            }
        }
    }

    // ── Guardar estado de WebView ────────────────────────────
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }
}
