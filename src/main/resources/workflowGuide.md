# 🛠️ Guía Maestro del Flujo de Trabajo

Esta página recoge todas las convenciones y pasos de nuestro flujo de trabajo en GitHub: nombrado de ramas, commits, Pull Requests, y el flujo completo desde la idea hasta el merge.

## 📖 Introducción

En este documento encontrarás nuestro **Git Flow simplificado** (sin `git flow start`/`finish`) y las pautas para que el equipo trabaje de forma ordenada y coherente.

--- 

## 🌿 Nomenclatura de ramas

**Formato general**
```
tipo/short-description
```
- Minúsculas
- `kebab-case` (guiones)
- Descripción breve y clara

| Tipo        | Uso                                                   |
|-------------|-------------------------------------------------------|
| `feature/`  | Nuevas funcionalidades                                |
| `bugfix/`   | Corrección de errores                                 |
| `release/`  | Preparación de versiones estables                     |
| `hotfix/`   | Parches urgentes en `main`                            |
| `refactor/` | Reestructuración de código sin cambiar funcionalidad  |
| `docs/`     | Cambios en documentación                              |
| `chore/`    | Tareas de mantenimiento (dependencias, scripts, etc.) |

**Ejemplos**
```
feature/user-authentication
bugfix/fix-nullpointer-login
release/1.4.0
hotfix/password-reset-urgent
refactor/order-service-cleanup
docs/update-workflow_guide
chore/update-dependencies
```

---

## ✍️ Convenciones de commits

**Formato general**
```
<tipo>: <mensaje corto en presente>
```

| Tipo     | Prefijo     | ¿Cuándo usarlo?                                                           | Ejemplo de mensaje                        |
|----------|-------------|---------------------------------------------------------------------------|-------------------------------------------|
| Feature  | `feat:`     | Para añadir una nueva funcionalidad al proyecto                           | `feat: add user authentication`           |
| Bugfix   | `fix:`      | Para corregir errores o comportamientos inesperados                       | `fix: handle null email in registration`  |
| Refactor | `refactor:` | Para modificar el código sin cambiar su comportamiento (ni funcionalidad) | `refactor: simplify UserService logic`    |
| Docs     | `docs:`     | Para cambios en la documentación (README, Wiki, comentarios...)           | `docs: update API usage in README`        |
| Test     | `test:`     | Para añadir o modificar tests (unitarios, de integración...)              | `test: add unit tests for AuthController` |
| Chore    | `chore:`    | Para tareas de mantenimiento que no afectan al código de producción       | `chore: upgrade spring-boot to 2.7.5`     |

Si necesitas más detalle, añade un cuerpo tras una línea en blanco:
```
feat: add registration endpoint

- Validate input with Bean Validation
- Persist User entity
- TODO: add integration tests
```

---

## 🎯 Kanban en Jira : https://zohra.atlassian.net/jira/software/projects/SCRUM/boards/1?atlOrigin=eyJpIjoiMThmOTM3MmY4ZGE2NGQ3NGEzYjhkNjAyY2FjYjliNDUiLCJwIjoiaiJ9

Tascas creadas en el Backlog y repartidas en Sprint. 


---

## 🔀 Pull Requests

- Crear PR **desde** la rama de trabajo **hacia** `dev`.
- **Título** del PR:
  ```
  feat: add user-authentication (#12)
  ```
- **Descripción**:
    - Breve resumen de cambios.
- Asignar revisores y añadir comentarios según convención.

---

## 🚀 Flujo de trabajo completo

1. ** Crear Tarjeta en Backlog de Jira **
2. ** Mover la tarjeta a un Sprint **
3. **Crear rama** desde `dev`:
   ```bash
   git checkout dev
   git checkout -b feature/short-description
   ```
4. **Desarrollar** y **commitear** según convención.
5. **Push** al remoto:
   ```bash
   git push -u origin feature/short-description
   ```
8. **Crear Pull Request** hacia `dev`.
8. **Revisión** por el equipo.
9. **Merge** manual cuando esté aprobado.
11. **Cerrar Issue** (automático con `Closes #n`).
12. **Eliminar rama** local y remota:
    ```bash
    git branch -d feature/short-description
    git push origin --delete feature/short-description
    ```

---

## ✅ Checklist paso a paso

- [ ] Tarjeta creada en Jira
- [ ] Rama creada (`feature/...`)
- [ ] Commits con mensajes según convención
- [ ] Push de la rama al remoto
- [ ] PR abierto con descripción 
- [ ] Merge 
- [ ] Rama borrada  

