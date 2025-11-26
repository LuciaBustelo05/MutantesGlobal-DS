### **API de Detección de Mutantes – MercadoLibre**

👤 **Lucía Bustelo Fernández**
📘 **Legajo: 50818**
📚 **Materia: Desarrollo de Software**
📅 **Examen Global**

---

# 🧬 Descripción del Proyecto

Este proyecto implementa el desafío técnico de MercadoLibre:
**determinar si una secuencia de ADN pertenece a un mutante**.

Un mutante es aquel que presenta **más de una secuencia** de 4 letras iguales consecutivas (A, T, C o G) en alguna de estas direcciones:

* Horizontal →
* Vertical ↓
* Diagonal ↘
* Diagonal ↗

El sistema además:

* 🚀 Expone una API REST en Spring Boot
* 🧾 Documenta todo con Swagger (OpenAPI 3)
* 🗄 Guarda cada análisis en una base H2
* 💾 Evita análisis duplicados mediante hash SHA-256
* 📊 Calcula estadísticas globales
* 🧪 Implementa **35 tests** automáticos
* 📈 Integra un reporte de cobertura **JaCoCo**
* 🐳 Permite ejecución con Docker
* ☁ Está desplegado en **Render**

---

# 🧰 Tecnologías utilizadas

| Componente             | Versión   |
| ---------------------- | --------- |
| Java                   | 17        |
| Spring Boot            | 3.5.8     |
| Gradle                 | 8.x       |
| H2 Database            | In-Memory |
| Spring Data JPA        | ✔         |
| Lombok                 | ✔         |
| Spring Validation      | ✔         |
| Springdoc / Swagger UI | ✔         |
| JUnit 5                | ✔         |
| JaCoCo                 | ✔         |
| Docker                 | ✔         |
| Render                 | ✔         |

---

# 🚀 Cómo ejecutar el proyecto

## ▶️ **1. Ejecución local con Gradle**

```bash
./gradlew bootRun
```

## ▶️ **2. Endpoints principales**

| Servicio     | URL                                                                                        | Descripción             |
| ------------ | ------------------------------------------------------------------------------------------ | ----------------------- |
| Swagger UI   | [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) | Documentación + pruebas |
| Base H2      | [http://localhost:8080/h2-console](http://localhost:8080/h2-console)                       | Exploración de la BD    |
| POST /mutant | [http://localhost:8080/mutant](http://localhost:8080/mutant)                               | Determina si es mutante |
| GET /stats   | [http://localhost:8080/stats](http://localhost:8080/stats)                                 | Retorna estadísticas    |

### Configuración H2 (para entrar a la consola):

* **JDBC URL:** `jdbc:h2:mem:mutantsdb`
* **Usuario:** `sa`
* **Contraseña:** *(vacío)*

---

# ☁️ Deploy en Render

El proyecto se ejecuta en Docker utilizando la variable de entorno `PORT` provista por Render.

📌 **Enlace al despliegue:**
👉 [https://mutantesglobal-ds.onrender.com](https://mutantesglobal-ds.onrender.com)
(Se redirige automáticamente al Swagger gracias a `HomeController`)

---

# 🗂️ Arquitectura del Proyecto

El proyecto sigue una arquitectura clara y evaluable:

```
src/main/java/com/luciabustelo/mutantes
│
├── controller          → API REST (MutantController, HomeController)
├── dto                 → DnaRequest, StatsResponse
├── service             → MutantService, StatsService, MutantDetector
├── repository          → DnaRecordRepository
├── entity              → DnaRecord
├── validation          → ValidDnaSequence + Validator
├── exception           → Manejo global de errores
├── config              → OpenAPI Config
└── MutantesApplication → Main
```

### ✔ Buenas prácticas aplicadas

* Separación estricta de capas
* Validaciones con anotaciones
* Repos Repository Pattern
* DTOs para entrada/salida
* Servicio puro para lógica
* Early return en detección
* Hash + persistencia
* Manejo de excepciones centralizado
* OpenAPI 3 documentado automáticamente

---

# 🧪 Testing

La aplicación cuenta con **35 tests**, cubriendo:

* ✔ MutantDetector (unitarios completos)
* ✔ MutantService (mocks + flujo lógico)
* ✔ StatsService
* ✔ MutantController (MockMvc)
* ✔ Repository tests (H2 real)
* ✔ Integration tests (servidor real con TestRestTemplate)

---

# 📈 Cobertura — JaCoCo

Para generar el reporte:

```bash
./gradlew test jacocoTestReport
```

El reporte queda en:

```
build/reports/jacoco/test/html/index.html
```

<img width="1440" height="294" alt="Captura de pantalla 2025-11-25 a la(s) 21 57 00" src="https://github.com/user-attachments/assets/86766f0f-6188-4902-bf9d-b3e7cd3e30a7" />

---

# 📊 Diagrama de Secuencia – Completo

Abarca:

* DTOs
* Controller
* Services
* Detector
* Repository
* Entity
* Base de datos
* Validaciones
* Flujo mutante / humano
* Flujo de estadísticas

<img width="1671" height="1482" alt="bLLRSjCm5FtNAkxqXuG-w9H-UQPCI1neAN4OAy07PJgAhQPY72d8SeQm01R03bW3I-XEM0bNZpXnOnxrZnywzvvpxpsIUwLXsbJpWh3AA5dDfrmJmZAZD0mAmQNXm4eO4B90dCZ4Waa3UwFAu7sWfD6gABZUiw3He0cA9KlvvugN1ZhnPDnr87p0ZqqWDSoKAIyNIfQyYNLBRToKwwN8k2UcROYO6sxhy92uHLkcT9xoXIe5" src="https://github.com/user-attachments/assets/69930718-ad27-408a-90df-463b445513c9" />

---

# 🔍 Ejemplos de uso

## Ejemplo mutante (200 OK)

```json
{
  "dna": [
    "AAAA",
    "CAGT",
    "TTAT",
    "AGAA"
  ]
}
```

## Ejemplo humano (403 Forbidden)

```json
{
  "dna": [
    "ATGC",
    "TGCA",
    "CATG",
    "GCAT"
  ]
}
```

## Respuesta de /stats

```json
{
  "count_mutant_dna": 12,
  "count_human_dna": 25,
  "ratio": 0.48
}
```

---

# 🐳 Docker

El proyecto incluye un `Dockerfile` multiphase:

* Etapa 1 → Compila con Gradle
* Etapa 2 → Ejecuta con Temurin JRE
* Respeta el puerto dinámico de Render (`$PORT`)

Build local:

```bash
docker build -t mutantes-api .
```

Ejecutar:

```bash
docker run -p 8080:8080 mutantes-api
```

---

# ✔ Checklist de la rúbrica (cumplido)

| Ítem                      | Estado |
| ------------------------- | ------ |
| API REST /mutant y /stats | ✔      |
| Validaciones              | ✔      |
| DTOs                      | ✔      |
| Servicios                 | ✔      |
| Repositorios              | ✔      |
| Entidades                 | ✔      |
| Hash de ADN               | ✔      |
| Persistencia              | ✔      |
| BD H2                     | ✔      |
| Swagger                   | ✔      |
| Docker                    | ✔      |
| Render                    | ✔      |
| 35 Tests                  | ✔      |
| JaCoCo                    | ✔      |
| DS completo               | ✔      |
| README completo           | ✔      |

---

Si querés, puedo armar también el **PDF final** para entregar, basado en este README.
