# Conozca Prototype - Backend API

Una API REST moderna desarrollada con Spring Boot para el sistema de gestión de contenidos Conozca.

## 🚀 Características

- **Spring Boot 3.1.4** con Java 21
- **Autenticación JWT** completa 
- **Base de datos PostgreSQL** (compatible con Supabase)
- **Seguridad robusta** con Spring Security
- **Tests unitarios y de integración**
- **Documentación de API** detallada
- **CORS configurado** para frontend

## 🏗️ Arquitectura

```
src/
├── main/java/com/conozca/prototype/
│   ├── config/           # Configuraciones (Security, JWT, CORS)
│   ├── controller/       # Controladores REST
│   ├── DTO/             # Data Transfer Objects
│   ├── exception/       # Manejo de excepciones
│   ├── model/           # Entidades JPA
│   ├── repository/      # Repositorios de datos
│   └── service/         # Lógica de negocio
└── test/                # Tests unitarios e integración
```

## 🛠️ Configuración con Supabase

### 1. Crear proyecto en Supabase

1. Ve a [supabase.com](https://supabase.com) y crea una cuenta
2. Crea un nuevo proyecto
3. Ve a Settings > Database
4. Copia la cadena de conexión

### 2. Variables de entorno

Crea un archivo `.env` en la raíz del proyecto:

```env
# Database Configuration (Supabase)
DATABASE_URL=jdbc:postgresql://db.xxxxxxxxxxxxx.supabase.co:5432/postgres
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=tu_password_de_supabase

# JWT Configuration
JWT_SECRET=tu_clave_secreta_jwt_muy_larga_y_segura_de_al_menos_64_caracteres

# Server Configuration
PORT=8081
```

### 3. Actualizar application.properties

Las propiedades ya están configuradas para usar variables de entorno:

```properties
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/conozca_prototype}
spring.datasource.username=${DATABASE_USERNAME:your_username}
spring.datasource.password=${DATABASE_PASSWORD:your_password}
jwt.secret=${JWT_SECRET:mySecretKey...}
server.port=${PORT:8081}
```

## 🚀 Instalación y Ejecución

### Prerrequisitos

- Java 21+
- Maven 3.6+
- Base de datos PostgreSQL (Supabase recomendado)

### Pasos

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/MaximilianoGiovine/Conozca-Prototype.git
   cd prototype
   ```

2. **Configurar variables de entorno**
   ```bash
   # Windows
   set DATABASE_URL=tu_url_de_supabase
   set DATABASE_USERNAME=postgres
   set DATABASE_PASSWORD=tu_password
   set JWT_SECRET=tu_clave_jwt_muy_larga

   # Linux/Mac
   export DATABASE_URL=tu_url_de_supabase
   export DATABASE_USERNAME=postgres
   export DATABASE_PASSWORD=tu_password
   export JWT_SECRET=tu_clave_jwt_muy_larga
   ```

3. **Ejecutar tests**
   ```bash
   mvn test
   ```

4. **Ejecutar aplicación**
   ```bash
   mvn spring-boot:run
   ```

La API estará disponible en `http://localhost:8081`

## 📚 API Endpoints

### 🔐 Autenticación

| Method | Endpoint | Descripción | Público |
|--------|----------|-------------|---------|
| POST | `/api/auth/login` | Iniciar sesión | ✅ |
| POST | `/api/auth/register` | Registrar usuario | ✅ |
| GET | `/api/auth/validate` | Validar token JWT | ❌ |

#### Ejemplo Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "usuario@ejemplo.com",
  "password": "mipassword"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "usuario@ejemplo.com"
}
```

### 👥 Usuarios

| Method | Endpoint | Descripción | Requiere Auth |
|--------|----------|-------------|---------------|
| GET | `/api/users` | Listar usuarios | ✅ |
| GET | `/api/users/{id}` | Obtener usuario | ✅ |
| POST | `/api/users` | Crear usuario | ✅ |
| PUT | `/api/users/{id}` | Actualizar usuario | ✅ |
| DELETE | `/api/users/{id}` | Eliminar usuario | ✅ |

### 📝 Autores

| Method | Endpoint | Descripción | Requiere Auth |
|--------|----------|-------------|---------------|
| GET | `/api/authors` | Listar autores | ❌ |
| GET | `/api/authors/{id}` | Obtener autor | ❌ |
| POST | `/api/authors` | Crear autor | ✅ |
| PUT | `/api/authors/{id}` | Actualizar autor | ✅ |
| DELETE | `/api/authors/{id}` | Eliminar autor | ✅ |

### 📰 Posts

| Method | Endpoint | Descripción | Requiere Auth |
|--------|----------|-------------|---------------|
| GET | `/api/posts` | Listar posts | ❌ |
| GET | `/api/posts/{id}` | Obtener post | ❌ |
| POST | `/api/posts` | Crear post | ✅ |
| PUT | `/api/posts/{id}` | Actualizar post | ✅ |
| DELETE | `/api/posts/{id}` | Eliminar post | ✅ |

### 📚 Ediciones

| Method | Endpoint | Descripción | Requiere Auth |
|--------|----------|-------------|---------------|
| GET | `/api/editions` | Listar ediciones | ❌ |
| GET | `/api/editions/{id}` | Obtener edición | ❌ |
| POST | `/api/editions` | Crear edición | ✅ |
| PUT | `/api/editions/{id}` | Actualizar edición | ✅ |
| DELETE | `/api/editions/{id}` | Eliminar edición | ✅ |

### 💬 Comentarios

| Method | Endpoint | Descripción | Requiere Auth |
|--------|----------|-------------|---------------|
| GET | `/api/comments` | Listar comentarios | ❌ |
| GET | `/api/comments/{id}` | Obtener comentario | ❌ |
| POST | `/api/comments` | Crear comentario | ✅ |
| PUT | `/api/comments/{id}` | Actualizar comentario | ✅ |
| DELETE | `/api/comments/{id}` | Eliminar comentario | ✅ |

## 🔒 Autenticación

### JWT Token

Todos los endpoints protegidos requieren un token JWT en el header `Authorization`:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Roles de Usuario

- **EDITOR**: Puede crear y editar contenido
- **ADMIN**: Acceso completo al sistema

## 🌐 CORS

La API está configurada para aceptar peticiones desde cualquier origen durante desarrollo. Para producción, actualiza la configuración en `SecurityConfig.java`:

```java
configuration.setAllowedOriginPatterns(Arrays.asList("https://tu-frontend.com"));
```

## 📊 Base de Datos

### Modelos Principales

1. **User** - Usuarios del sistema con autenticación
2. **Author** - Autores de artículos con información biográfica
3. **Post** - Publicaciones/artículos del sistema
4. **Edition** - Ediciones de la revista
5. **Comment** - Comentarios en publicaciones

### Relaciones

- `Author` ↔ `Post` (OneToMany)
- `Edition` ↔ `Post` (OneToMany)
- `Post` ↔ `Comment` (OneToMany)

## 🧪 Testing

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar solo tests unitarios
mvn test -Dtest="*Test"

# Ejecutar solo tests de integración
mvn test -Dtest="*IntegrationTest"
```

## 🚀 Despliegue

### Opción 1: Heroku

1. Instalar Heroku CLI
2. ```bash
   heroku create tu-app-name
   heroku config:set DATABASE_URL=tu_url_supabase
   heroku config:set JWT_SECRET=tu_clave_jwt
   git push heroku main
   ```

### Opción 2: Railway

1. Conectar repositorio en [railway.app](https://railway.app)
2. Configurar variables de entorno
3. Deploy automático

### Opción 3: Docker

```dockerfile
FROM openjdk:21-jdk-slim
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 🔧 Configuración Frontend

Para conectar un frontend (React, Vue, Angular), usa estas configuraciones base:

### JavaScript/Fetch
```javascript
const API_BASE_URL = 'http://localhost:8081/api';

// Login
const response = await fetch(`${API_BASE_URL}/auth/login`, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({ username, password }),
});

// Peticiones autenticadas
const authResponse = await fetch(`${API_BASE_URL}/users`, {
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json',
  },
});
```

### Axios
```javascript
// Configuración base
const api = axios.create({
  baseURL: 'http://localhost:8081/api',
});

// Interceptor para token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
```

## 🐛 Troubleshooting

### Error de conexión a base de datos
- Verifica que las variables de entorno estén configuradas
- Confirma que la URL de Supabase sea correcta
- Verifica que el usuario/password de Supabase sean correctos

### Tests fallando
- Ejecuta `mvn clean test`
- Verifica que H2 esté en el classpath para tests
- Revisa logs para errores específicos

### CORS errors
- Confirma que el frontend esté en la lista de orígenes permitidos
- Verifica que los headers estén correctamente configurados

## 📝 Próximos Pasos

1. **Implementar paginación** en endpoints de listado
2. **Agregar logging** más detallado
3. **Crear documentación OpenAPI/Swagger**
4. **Implementar cache** para consultas frecuentes
5. **Agregar métricas** y monitoreo

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ve el archivo [LICENSE](LICENSE) para detalles.

---

**Desarrollado por Maximiliano Giovine**