# Changelog

## 2.0.0 - 2024-01-24

### Sprint learnings

- Paginación en ´findAll´
- Crear joins entre entidades
- Agregar funciones nuevas en el repositorio. 
- Crear documentación de ´Swagger´

### Added

- Paginación de trayectorias
- Join de taxis y trayectorias
- TrajectoryImpl: getAllByTaxi trae la información paginada para un taxi, por ID y la última ubicación. 
- Documentación de ´Swagger´
- Primer test sin mock: ok, segundo test con mock: return mal configurado

### Fixed

- Se reorganizaron los endpoints y las paginaciones de Taxis y Trayectorias

### Removed

- Se quitó ´SEQUENCE´ en las entidades ya que genera problemas en el verbo post

## 1.0.0 - 2024-01-17

### Sprint learnings

- Conección con Vercel
- Arquitectura de N capas
- Mejor uso de interfaces e implementación de las mismas
- Decoradores

### Added

- Creación de carpetas en estructura de N capas
- Codeo inicial de repositorio, servicio y controlador de Taxis, también se agregó la capa Dto
- Versión inicial de endpoints

### Fixed

- Configuración de dependencias y conección a vercel

