# Bio-Aether Core v4.0 — Proyecto Android Studio
## Instrucciones para compilar el APK

---

## REQUISITOS PREVIOS

- **Android Studio** (Hedgehog 2023.1 o superior)
  → Descargar gratis: https://developer.android.com/studio
- **JDK 17** (viene incluido con Android Studio)
- **~3 GB de espacio** en disco (SDK Android)

---

## PASO 1 — Abrir el proyecto

1. Abre **Android Studio**
2. Selecciona **"Open"** (NO "New Project")
3. Navega hasta la carpeta **`BioAetherCore`** y selecciónala
4. Haz clic en **OK**
5. Espera que aparezca el mensaje **"Gradle sync finished"** (~2-4 min la primera vez)

---

## PASO 2 — Aceptar SDK si se pide

Si Android Studio pide instalar componentes del SDK:
- Haz clic en **"Install missing SDK"** o **"Accept"**
- Espera la descarga (~500 MB)

---

## PASO 3 — Compilar el APK (debug — para pruebas)

```
Menú superior → Build → Build Bundle(s) / APK(s) → Build APK(s)
```

Espera ~1-2 minutos. Cuando termine verás:

```
✅ Build successful
```

Con un enlace **"locate"** que abre la carpeta del APK.

### Ubicación del APK generado:
```
BioAetherCore/
  app/
    build/
      outputs/
        apk/
          debug/
            BioAetherCore-v4.0-debug.apk   ← ESTE ES TU APK
```

---

## PASO 4 — Instalar en tu teléfono

### Opción A — Cable USB (más fácil)
1. Conecta tu teléfono por USB
2. En Android Studio: **Run → Run 'app'** (botón ▶)
3. Selecciona tu teléfono y haz clic OK
4. La app se instala y abre automáticamente ✅

### Opción B — Copiar APK manualmente
1. Copia el archivo `BioAetherCore-v4.0-debug.apk` al teléfono
2. En el teléfono: **Ajustes → Seguridad → "Fuentes desconocidas"** → Activar
3. Abre el APK desde el gestor de archivos
4. Toca **"Instalar"** ✅

---

## PASO 5 (OPCIONAL) — APK de producción firmado

Para distribuir en Google Play o compartir sin restricciones:

```
Build → Generate Signed Bundle / APK
→ APK → Next
→ Create new keystore (guárdalo seguro, lo necesitas siempre)
→ Build Type: release
→ Finish
```

El APK release estará en:
```
app/build/outputs/apk/release/BioAetherCore-v4.0-release.apk
```

---

## ESTRUCTURA DEL PROYECTO

```
BioAetherCore/
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml      ← Configuración principal
│   │   ├── assets/www/
│   │   │   └── index.html           ← Tu app Bio-Aether completa
│   │   ├── java/com/bioaether/core/
│   │   │   ├── MainActivity.java    ← WebView + configuración Android
│   │   │   └── SplashActivity.java  ← Pantalla de carga animada
│   │   └── res/
│   │       ├── layout/              ← Layouts XML
│   │       ├── mipmap-*/            ← Íconos de la app
│   │       └── values/              ← Colores, strings, temas
│   └── build.gradle                 ← Config del módulo app
├── build.gradle                     ← Config raíz
├── settings.gradle                  ← Nombre del proyecto
└── gradle.properties                ← Opciones de compilación
```

---

## CARACTERÍSTICAS DE LA APP

| Feature | Detalle |
|---|---|
| **Android mínimo** | API 24 (Android 7.0 Nougat) |
| **Target** | API 34 (Android 14) |
| **Orientación** | Portrait (vertical) |
| **Internet** | Requerido (para fuentes Google) |
| **StatusBar** | Color #050b12 (oscuro) |
| **Splash** | Animación fade + scale, 2.2 segundos |
| **Back button** | Navega dentro de la WebView |
| **Tamaño estimado APK** | ~1.5 MB |

---

## SOLUCIÓN DE PROBLEMAS

**"Gradle sync failed"**
→ Verifica conexión a internet y acepta las licencias del SDK

**"SDK location not found"**
→ File → Project Structure → SDK Location → apunta a tu carpeta Android SDK

**"minSdk version is too low"**
→ Edita `app/build.gradle` y cambia `minSdk 24` a `minSdk 26`

**La app no carga el HTML**
→ Verifica que `app/src/main/assets/www/index.html` existe

---

## CONTACTO / SOPORTE

Proyecto: **Bio-Aether Core v4.0**
Generado con Claude (Anthropic)
