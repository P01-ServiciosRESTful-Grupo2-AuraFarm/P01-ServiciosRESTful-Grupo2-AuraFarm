# 🌾 AuraFarm API 🗿✨

> **+10,000 de Aura** si compila a la primera.😎😎
> 
> **-5,000 de Aura** si le tiras un error HTTP 500 al profesor.🥀🥀

API RESTful en Spring Boot y PostgreSQL para organizar quedadas, batallas 1v1 aleatorias y medir quién es el verdadero *Chad* del ranking.

---

## ⚡ Stack Tecnológico

* **Java & Spring Boot** ☕
* **PostgreSQL** 🐘
* **Maven** 📦


## 🚀 Despliegue de la Base de Datos (2 Opciones)

### Opción A: Con Docker Compose 🐳 (Recomendada)
Para levantar la base de datos sin instalar PostgreSQL en local, ejecuta en la raíz del proyecto:

```bash
docker compose up -d
````

#### 🛠️ Conexión desde un cliente de base de datos (DBeaver / pgAdmin)
Para inspeccionar la base de datos visualmente sin usar la consola:


Abre DBeaver (o pgAdmin) y selecciona crear una nueva conexión de tipo PostgreSQL.


Usa los siguientes datos de acceso (definidos en docker-compose.yml):


Host: localhost


Puerto: 5432


Base de datos: aurafarm_db


Usuario: postgres


Contraseña: aurafarmer


💡 Nota: Asegúrate de que el contenedor esté corriendo (docker compose up -d) antes de intentar probar la conexión en DBeaver.


### Opción B: PostgreSQL Local 🐘
Crea una base de datos vacía llamada aurafarm_db en PostgreSQL.


Arranca el proyecto desde Eclipse. (Spring Boot creará las tablas e insertará los datos de prueba automáticamente).

##  🎯 Endpoints Clave


POST /aurafarm/register — Crea tu cuenta (+100 Aura)


GET /aurafarm/jornadas — Ver las quedadas para farmear (+50 Aura)


POST /aurafarm/jornadas/{id} — Crear jornada (plazas pares obligatorias) (+500 Aura)


PUT /aurafarm/jornadas/{id}/cambiar-plazo — Generar batallas 1v1 al azar (+1000 Aura)


GET /aurafarm/users/ranking — Ver quién manda en el servidor (🗿 Infinito)


## ⚡ AuraFarm | Validación Robusta y Flujo de Errores Transparente


En AuraFarm no hay sitio para el azar ni para la magia negra. Ejecutamos una arquitectura directa, quirúrgica y de rendimiento puro. Cero interceptores globales opacos (@RestControllerAdvice) resolviendo desastres en la sombra: la validez de cada petición se audita en la línea de fuego, directamente en el @RestController.


La Ley del Dominio: Si los datos de entrada no demuestran una integridad absoluta al cruzar el umbral del sistema, la ejecución se cercena al instante con la precisión quirúrgica de un estado HTTP imperturbable.


## 🛡️ El Estándar de Entrada (Escudo Anti-500)Validación Manual Explícita: Cero excepciones fantasma en la consola. 


Validación Manual Explícita: Cero excepciones fantasma en la consola. Evaluamos la integridad de los datos de entrada mediante comprobaciones directas con sentencias if en el controlador antes de tocar la capa de negocio o la base de datos.


Respuestas Predictivas: Cero trazas de error expuestas. El cliente siempre recibe una respuesta ResponseEntity estructurada y limpia.


⚔️Control Preciso de Estados HTTP:


$\color{orange}{\text{400 Bad Request}}$ ➔ Peticiones incompletas, datos de entrada erróneos o campos obligatorios vacíos.


$\color{red}{\text{404 Not Found}}$ ➔ Entidades inexistentes en la base de datos de PostgreSQL.


$\color{green}{\text{200 OK / 201 Created}}$ ➔ Flujo de datos verificado y procesado con éxito.


### 🏛️ Reglas del Servidor para el Equipo


Patrón Guardián: Ninguna lógica de la capa @Service se ejecuta si la petición no ha pasado el filtro previo en el Controller.


Corte Inmediato: Si un campo falla, el controlador interrumpe la ejecución e informa inmediatamente con un ResponseEntity.badRequest().


##  📂 Estructura del Entregable PSP


Item1/ — Código fuente completo (mvn clean ejecutado antes de comprimir en .zip).


Item2/ — Diagrama de Dominio UML en PDF.


Item3/ — Enlace a este repositorio de GitHub con los commits del equipo.


Item4/ — Tablero de Trello (Planificación del Jefe de Proyecto) y archivo dificultades.txt.


##  👑 $\color{purple}{\text{La Élite de AuraFarm \Vert{ Los Guardianes del Core (Grupo 2)} }}$


👑 **Dailos Hernández Reyes (Líder)** — $\color{orange}{\text{Soberano del Proyecto, Gestor de Entregables y Maestro de Inscripciones}}$


🗡️ **Daniel González Álvarez** — $\color{yellow}{\text{Forjador del Diagrama UML y Dominador de las Jornadas}}$


🛡️ **Miguel Ángel González Ortega** — $\color{red}{\text{Centinela de Identidades, Guardián de Actores y Custodio de Roles}}$


🔑 **Juan Antonio Leiva Chiva** — $\color{green}{\text{Señor del Algoritmo 1v1, Motor de Enfrentamientos y Juez de Victorias}}$  


📰 **Juan José Tejero Castro** — $\color{cyan}{\text{Heraldo de Noticias y Cronista del Ranking Supremo}}$


<img width="284" height="480" alt="67GIF" src="https://github.com/user-attachments/assets/f2ca8add-655e-4d6c-b887-01370f983999" />

<img width="446" height="480" alt="MewingGIF" src="https://github.com/user-attachments/assets/8d788252-ec3a-4b7a-bfdd-70ab665d6605" />

<img width="1000" height="600" alt="AuraTuffGIF" src="https://github.com/user-attachments/assets/20858bfe-5ea8-46b6-9930-b629c57368c6" />


Enlace al Trello: https://trello.com/invite/b/6ae36e7ab72d2e9cffa6413a/ATTI52ef808d3fcbc9964af8cfd5cb0168233B83590D/organizacion-aurafram 
