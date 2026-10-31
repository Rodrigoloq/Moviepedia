# Moviepedia

## Descripción

Moviepedia es una aplicación Android para explorar películas, guardar favoritos y escribir reseñas. Está desarrollada con Kotlin y Jetpack Compose, y obtiene la información del catálogo desde la API de [The Movie Database (TMDB)](https://www.themoviedb.org/). Las cuentas de usuario, los favoritos y las reseñas se almacenan localmente en el dispositivo.

## Características

- **Registro e inicio de sesión**: las cuentas se guardan en una base de datos local y la sesión se mantiene entre aperturas de la app.
- **Inicio**: carruseles con las películas en cartelera y las más populares.
- **Búsqueda**: búsqueda de películas por título y cuadrícula con las mejor valoradas.
- **Detalle de película** organizado en cuatro pestañas:
  - **Detalles**: productoras, países, idiomas, duración, presupuesto, recaudación y títulos alternativos.
  - **Elenco**: actores y personajes.
  - **Equipo**: integrantes del equipo de producción.
  - **Estreno**: fechas de estreno por país.
- **Favoritos**: agrega o quita películas de tu lista personal.
- **Reseñas**: escribe una reseña con calificación de 1 a 5 estrellas, y consúltala, edítala o elimínala. También puedes leer las reseñas de otros usuarios registrados en el dispositivo.
- **Cuenta**: consulta tu perfil, revisa todas tus reseñas, edita tu nombre de usuario, correo y foto, y cierra sesión.

## Tecnologías utilizadas

| Categoría | Tecnología |
|---|---|
| Lenguaje | Kotlin 2.2.10 |
| Interfaz de usuario | Jetpack Compose (BOM 2024.09.00) y Material 3 |
| Arquitectura | MVVM con repositorios y `StateFlow` |
| Inyección de dependencias | Hilt 2.60.1 |
| Base de datos local | Room 2.8.4 (usuarios, favoritos y reseñas) |
| Sesión y preferencias | Jetpack DataStore (Preferences) 1.2.1 |
| Red | Retrofit 3.0.0 con conversor Gson |
| Carga de imágenes | Coil 3.4.0 |
| Navegación | Navigation Compose 2.9.8 |
| Asincronía | Kotlin Coroutines |
| Procesamiento de anotaciones | KSP |
| Compilación | Gradle (Kotlin DSL), `minSdk` 26, `targetSdk` 36 |
| API externa | [TMDB API v3](https://developer.themoviedb.org/docs) |

## Estructura del proyecto

```
Moviepedia/
├── app/
│   └── src/
│       ├── main/
│       │   ├── java/com/rodrigoloq/moviepedia/
│       │   │   ├── data/
│       │   │   │   ├── retrofit/
│       │   │   │   │   └── entities/
│       │   │   │   └── room/
│       │   │   │       └── entities/
│       │   │   ├── di/
│       │   │   ├── feature/
│       │   │   │   ├── account/
│       │   │   │   ├── favorites/
│       │   │   │   ├── home/
│       │   │   │   ├── login/
│       │   │   │   ├── moviedetail/
│       │   │   │   ├── register/
│       │   │   │   ├── reviews/
│       │   │   │   │   ├── reviewform/
│       │   │   │   │   ├── reviews/
│       │   │   │   │   └── userreviews/
│       │   │   │   └── search/
│       │   │   ├── navigation/
│       │   │   ├── session/
│       │   │   ├── ui/
│       │   │   │   └── theme/
│       │   │   └── utils/
│       │   └── res/
│       ├── test/
│       └── androidTest/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

| Carpeta | Descripción |
|---|---|
| `app/` | Módulo principal de la aplicación Android. |
| `data/retrofit/` | Interfaz `MovieService` con los endpoints de TMDB. |
| `data/retrofit/entities/` | Modelos de las respuestas de la API: películas, detalle, elenco, equipo, estrenos, géneros y títulos alternativos, junto con mapeadores de países y géneros. |
| `data/room/` | Base de datos local (`AppDatabase`) y sus DAO para usuarios, favoritos y reseñas. |
| `data/room/entities/` | Tablas `users`, `favorites` y `reviews`. |
| `di/` | Módulos de Hilt que proveen Retrofit, la base de datos, el administrador de sesión y la configuración de la app. |
| `feature/login/` y `feature/register/` | Autenticación local de usuarios. |
| `feature/home/` | Pantalla de inicio con las películas en cartelera y populares. |
| `feature/search/` | Búsqueda de películas y listado de las mejor valoradas. |
| `feature/moviedetail/` | Detalle de una película con las pestañas de detalles, elenco, equipo y estreno. |
| `feature/favorites/` | Lista de películas favoritas. |
| `feature/reviews/` | Reseñas: formulario para crear o editar (`reviewform/`), reseñas de una película (`reviews/`) y reseñas del usuario (`userreviews/`). |
| `feature/account/` | Perfil del usuario y edición de su información. |
| `navigation/` | Grafo de navegación y barra de navegación inferior. |
| `session/` | `SessionManager`, que guarda la sesión del usuario con DataStore. |
| `ui/theme/` | Colores, tipografía y tema de la aplicación. |
| `utils/` | Funciones auxiliares, como el formato de fechas. |
| `res/` | Recursos: íconos, colores, textos y temas. |
| `test/` y `androidTest/` | Pruebas unitarias e instrumentadas. |
| `gradle/` | Catálogo de versiones (`libs.versions.toml`) y Gradle Wrapper. |

Dentro de cada carpeta de `feature/`, el código sigue el patrón MVVM: la pantalla (`Screen`), un subpaquete `model/` con el repositorio y un subpaquete `viewmodel/` con el estado de la UI.

## Configuración

La app necesita un token de acceso de TMDB:

1. Crea una cuenta en [themoviedb.org](https://www.themoviedb.org/) y obtén tu *API Read Access Token*.
2. Agrégalo al archivo `local.properties` en la raíz del proyecto:

   ```properties
   API_KEY=tu_token_de_tmdb
   ```

3. Sincroniza con Gradle y ejecuta la app en un dispositivo o emulador con Android 8.0 (API 26) o superior.
