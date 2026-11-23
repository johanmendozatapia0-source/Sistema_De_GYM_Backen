# GymFit Backend

Backend REST con Spring Boot, Java 21, Spring Data JPA, PostgreSQL y autenticación JWT. Sigue la arquitectura por capas indicada en la guía del proyecto.

## 1. Crear la base de datos PostgreSQL

Inicia PostgreSQL Server y conéctate desde pgAdmin. Haz clic derecho en **Databases → Create → Database…**, escribe `gymfit_db` y guarda. También puedes abrir Query Tool conectado a `postgres` y ejecutar:

```sql
CREATE DATABASE gymfit_db;
```

No tienes que crear las tablas manualmente: al arrancar, Hibernate las crea/actualiza a partir de las entidades. `ddl-auto=update` sirve para desarrollo; antes de producción conviene cambiarlo a migraciones versionadas (Flyway) y `validate`.

## 2. Configurar las credenciales

En PowerShell, desde la carpeta del proyecto, define las variables para la sesión actual (cambia usuario y claves por los de tu instalación):

```powershell
$env:DB_URL = 'jdbc:postgresql://localhost:5432/gymfit_db'
$env:DB_USERNAME = 'postgres'
$env:DB_PASSWORD = 'TU_CLAVE_POSTGRESQL'
$env:JWT_SECRET = 'PEGA_AQUI_UN_SECRETO_BASE64_DE_32_BYTES_O_MAS'
$env:APP_ADMIN_EMAIL = 'admin@gymfit.local'
$env:APP_ADMIN_PASSWORD = 'CAMBIA_ESTA_CLAVE_POR_UNA_SEGURA'
$env:CORS_ALLOWED_ORIGIN = 'http://localhost:4200'
```

Genera una clave JWT aleatoria en PowerShell, y asigna el resultado a `JWT_SECRET`:

```powershell
$bytes = New-Object byte[] 32
[System.Security.Cryptography.RandomNumberGenerator]::Fill($bytes)
[Convert]::ToBase64String($bytes)
```

Usa el mismo terminal para crear el secreto y luego arranca el servidor. La clave JWT debe ser Base64 y tener al menos 32 bytes decodificados. No subas claves ni contraseñas a Git.

## 3. Iniciar

```powershell
.\gradlew.bat bootRun
```

El servidor escucha en `http://localhost:8080`. En el primer arranque se crea el administrador definido por `APP_ADMIN_EMAIL` y `APP_ADMIN_PASSWORD` si todavía no existe. Las contraseñas se guardan con BCrypt.

## 4. API disponible

Excepto registro e inicio de sesión, los endpoints requieren `Authorization: Bearer <token>`.

| Método | Ruta | Uso |
|---|---|---|
| POST | `/api/auth/register` | Registrar usuario de recepción |
| POST | `/api/auth/login` | Obtener JWT |
| GET, POST | `/api/socios` | Listar y crear socios |
| GET | `/api/socios/{codigoSocio}` | Buscar socio por código generado |
| GET, POST | `/api/membresias` | Consultar y crear planes |
| GET, POST | `/api/entrenadores` | Consultar y crear entrenadores |
| GET, POST | `/api/rutinas` | Consultar y crear rutinas; asignación opcional a entrenador |
| GET, POST | `/api/pagos` | Consultar y registrar pago de membresía |
| POST | `/api/acceso/validar` | Registrar acceso permitido/denegado |
| GET | `/api/dashboard/resumen` | Socios activos, ingresos del mes y asistencias de 7 días |

Los DTO de request tienen validación. Un pago exitoso activa/renueva la vigencia del socio. Para control de acceso se verifica que el socio esté activo y su fecha de vigencia no haya pasado; todo intento válido o denegado queda en asistencias.

## 5. Ejemplos rápidos (PowerShell)

Iniciar sesión y guardar el token:

```powershell
$login = Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/auth/login' `
  -ContentType 'application/json' -Body '{"email":"admin@gymfit.local","password":"TU_CLAVE_ADMIN"}'
$token = $login.token
$headers = @{ Authorization = "Bearer $token" }
```

Crear socio:

```powershell
$socio = Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/socios' `
  -Headers $headers -ContentType 'application/json' `
  -Body '{"nombres":"Ana Pérez","dni":"12345678","telefono":"999111222"}'
```

Crear plan; guarda el `id` devuelto:

```powershell
$plan = Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/membresias' `
  -Headers $headers -ContentType 'application/json' `
  -Body '{"nombre":"Mensual","duracionDias":30,"precio":89.90}'
```

Registrar pago y obtener vigencia:

```powershell
$pago = Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/pagos' `
  -Headers $headers -ContentType 'application/json' `
  -Body (@{ socioId = $socio.id; membresiaId = $plan.id } | ConvertTo-Json)
```

Validar entrada con el código que devolvió el socio:

```powershell
Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/acceso/validar' `
  -Headers $headers -ContentType 'application/json' `
  -Body (@{ codigoSocio = $socio.codigoSocio } | ConvertTo-Json)
```

## Estructura

- `entity/`, `repository/`: modelo relacional y persistencia JPA.
- `dto/`: contratos HTTP sin exponer entidades ni hashes de contraseña.
- `service/`: reglas del gimnasio y transacciones.
- `controller/`: rutas REST.
- `security/`, `config/`: JWT, BCrypt, CORS y creación opcional del administrador inicial.
- `exception/`: respuestas HTTP de validación y errores de dominio.
