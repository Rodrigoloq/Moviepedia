# Moviepedia

Moviepedia es una aplicación integral para Android construida con Kotlin moderno y Jetpack Compose. Permite a los usuarios explorar un amplio catálogo de películas, gestionar listas personales para ver y compartir reseñas. La aplicación obtiene datos desde la API de The Movie Database (TMDB) para ofrecer información actualizada sobre películas.

## Características

- **Autenticación de usuarios**: Funcionalidad segura de registro e inicio de sesión. Las sesiones de usuario se gestionan mediante Jetpack DataStore.  
- **Descubrimiento de películas**:
  - **Pantalla principal**: Muestra carruseles con las películas más recientes y populares.
  - **Búsqueda**: Encuentra películas por título con resultados en tiempo real.
  - **Mejor valoradas**: Explora una cuadrícula de películas mejor calificadas para obtener recomendaciones de calidad.
- **Información detallada de películas**: Accede a detalles completos de cualquier película, incluyendo:
  - Sinopsis, póster e imágenes de fondo.
  - Información del elenco y equipo.
  - Detalles de producción como presupuesto, ingresos y compañías productoras.
  - Fechas de estreno regionales y títulos alternativos.
- **Personalización**:
  - **Favoritos**: Añade o elimina películas de una lista personal de favoritos para un seguimiento sencillo.
  - **Reseñas**: Escribe, visualiza, edita y elimina reseñas personales con calificación por estrellas.
  - **Reseñas de la comunidad**: Lee reseñas de otros usuarios en la página de detalles de cada película.
- **Gestión de cuenta**:
  - Edita la información del perfil, incluyendo nombre de usuario, correo electrónico y foto de perfil.
  - Visualiza una lista dedicada de todas tus reseñas pasadas.
  - Función de cierre de sesión para terminar la sesión actual.

## Arquitectura y tecnologías utilizadas

Este proyecto sigue el patrón de arquitectura **MVVM (Model-View-ViewModel)**, garantizando una separación clara de responsabilidades y una base de código escalable.

- **UI**: Construida completamente con **Jetpack Compose** para una interfaz moderna y declarativa.  
- **Lenguaje**: **Kotlin** (100%).  
- **Programación asíncrona**: **Kotlin Coroutines** para gestionar hilos en segundo plano y llamadas a la API.  
- **Networking**: **Retrofit** para solicitudes HTTP seguras hacia la API de TMDB, con **Gson** para la serialización JSON.  
- **Base de datos**:
  - **Room**: Para persistencia local de datos de usuario, favoritos y reseñas.
  - **Jetpack DataStore**: Para gestionar sesiones de usuario y preferencias.
- **Navegación**: **Jetpack Navigation para Compose** para manejar todos los flujos de navegación dentro de la app.  
- **Carga de imágenes**: **Coil 3** para carga eficiente y moderna de imágenes desde la red.  

