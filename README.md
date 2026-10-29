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
* **`@RestControllerAdvice`** 🛡️ *(Nuestro escudo sagrado anti-suspenso)*

## 🚀 Despliegue de la Base de Datos (2 Opciones)

### Opción A: Con Docker Compose 🐳 (Recomendada)
Para levantar la base de datos sin instalar PostgreSQL en local, ejecuta en la raíz del proyecto:

```bash
docker compose up -d
````

### Opción B: PostgreSQL Local 🐘
Crea una base de datos vacía llamada aurafarm_db en PostgreSQL.


Arranca el proyecto desde Eclipse. (Spring Boot creará las tablas e insertará los datos de prueba automáticamente).

##  🎯 Endpoints Clave


POST /aurafarm/auth/register — Crea tu cuenta (+100 Aura)


GET /aurafarm/jornadas — Ver las quedadas para farmear (+50 Aura)


POST /aurafarm/admin/jornadas — Crear jornada (plazas pares obligatorias) (+500 Aura)


PUT /aurafarm/admin/jornadas/{id}/cerrar — Generar batallas 1v1 al azar (+1000 Aura)


GET /aurafarm/ranking — Ver quién manda en el servidor (🗿 Infinito)


##  📂 Estructura del Entregable PSP


Item1/ — Código fuente completo (mvn clean ejecutado antes de comprimir en .zip).


Item2/ — Diagrama de Dominio UML en PDF.


Item3/ — Enlace a este repositorio de GitHub con los commits del equipo.


Item4/ — Tablero de Trello (Planificación del Jefe de Proyecto) y archivo dificultades.txt.


##  👑 El Consejo del Aura (Grupo 2)


🐺 Dailos Hernández Reyes(Líder) — Arquitecto del Core, Algoritmo 1v1 y Escudo Anti-500


🗡️ [Compañero 1] — Señor de las Jornadas e Inscripciones


🛡️ [Compañero 2] — Guardián del RGPD y Usuarios


📰 [Compañero 3] — Heraldo de Noticias y Ranking


🎨 [Compañero 4] — Maestro del Frontend y Empaquetado Final


<img width="284" height="480" alt="67GIF" src="https://github.com/user-attachments/assets/f2ca8add-655e-4d6c-b887-01370f983999" />

<img width="250" height="280" alt="despicablememinionsGIF" src="https://github.com/user-attachments/assets/86860bec-f2fb-4d49-a8a8-a10a21725d60" />

<img width="480" height="266" alt="AuraTuffGIF" src="https://github.com/user-attachments/assets/20858bfe-5ea8-46b6-9930-b629c57368c6" />


<img width="446" height="480" alt="MewingGIF" src="https://github.com/user-attachments/assets/8d788252-ec3a-4b7a-bfdd-70ab665d6605" />


Enlace al Trello: https://trello.com/invite/b/6ae36e7ab72d2e9cffa6413a/ATTI52ef808d3fcbc9964af8cfd5cb0168233B83590D/organizacion-aurafram 
