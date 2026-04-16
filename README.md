# 🚀 Práctica de Git/GitHub: Colaboración en equipo y gestión de ramas

## 1. Introducción y objetivo
Este repositorio es el tronco común de nuestro proyecto de clase. El objetivo de esta práctica es aprender a trabajar de forma colaborativa utilizando un **flujo de trabajo basado en ramas locales**.

Aprenderás a:
* Crear y gestionar ramas locales para no interferir en el trabajo de otros.
* Sincronizar tu repositorio local con los cambios de tus compañeros (`git pull`).
* Fusionar tu trabajo y publicarlo en el tronco común (`main`).

---

## 2. Requisitos previos
1. Tener configurada tu conexión en el terminal (SSH o HTTPS).
2. Haber sido aceptado como **Colaborador** en este repositorio.
3. Tener instalado Git en tu equipo.

---

## 3. El Escenario de la práctica
Cada alumno debe aportar su parte al proyecto. Para evitar conflictos directos, seguiremos esta regla: **Nadie toca la raíz del proyecto**. Cada uno trabajará dentro de su propia carpeta.

### Tarea a realizar:
1. Crear una carpeta con tu nombre (ej: `miguel`).
2. Dentro de esa carpeta, crear un archivo llamado `README.md`.
3. Escribir en él tu nombre completo y una breve frase sobre la práctica y el contenido de tu carpeta.

---

## 4. Paso a paso (Guía de comandos)

### Fase 1: Preparación
Clona el repositorio en tu ordenador y entra en la carpeta:
```bash
git clone https://github.com/maortiz17/tarea-git-github.git
cd tarea-git-github
```
Si tienes configurado tu par de claves SSH puedes utilizar:
```bash
git clone git@github.com:maortiz17/tarea-git-github.git
cd tarea-git-github
```
**Adjunta una captura de pantalla con el repositorio recién clonado. Solo contendrá el archivo `README.md` y la carpeta `.git` (Recuerda que el `.git` está oculto. Utiliza `ls -lrta` para que se visualice)**
### Fase 2: Trabajo en tu rama dentro de tu Git local
Crea una rama con tu nombre para trabajar de forma aislada:
```bash
git switch -c rama-tu-nombre
```
**Adjunta una captura de pantalla con el resultado de ejecución del comando `git branch`**
### Fase 3: Registro de los cambios
Crea tu carpeta y tu archivo `README.md`. Copia también ahí todas las clases de tu práctica de programación (solo los archivos `*.java`). Si no vas a realizar la práctica copia una clase cualquiera. Luego, guarda tu trabajo en el historial de tu rama local:
```bash
git add .
git commit -m "Añadida carpeta de [Tu Nombre]"
```
**Adjunta una captura en la que se vea el resultado de ejecución del comando `git status`.**
* Primero cuando todo esté en tu **área de trabajo**, y por tanto pendiente de trackeo. 
* Luego cuando esté todo en el **área de stage**. 
* Por último **tras hacer el commit** en tu rama. Ejecuta además un `git log --oneline` en este caso.
### Fase 4: Sincronización con el grupo
Antes de entregar, debemos asegurarnos de que tenemos lo que otros compañeros hayan subido mientras nosotros trabajábamos:
```bash
# 1. Volvemos a la rama principal
git switch main

# 2. Bajamos los cambios actuales de GitHub
git pull origin main
```
**Adjunta una captura con el resultado de ejecución del comando `git pull` sobre tu rama main**
### Fase 5: Integración y Entrega (El Merge)
Ahora uniremos tu trabajo al `main` local y lo subiremos a la nube:
```bash
# 1. Fusionamos tu rama en main
git merge rama-tu-nombre

# 2. Subimos el resultado final a GitHub
git push origin main
```
* Adjunta una captura de pantalla con el **resultado de ejecución de cada comando** 
* Adjunta por último una captura en la que se vea el **resultado de tu subida al repositorio remoto**
---

## 5. 🆘 Resolución de Problemas (El error del Push)
Si al hacer `git push` recibes un error de tipo `[rejected] (fetch first)`, significa que un compañero ha subido su trabajo justo antes que tú. Haz lo siguiente:

1. Ejecuta: `git pull origin main`
2. Se abrirá un editor de texto pidiendo un mensaje de "Merge".
   * En **Nano**: Pulsa `Ctrl+O`, `Enter`, `Ctrl+X`.
   * En **Vim**: Escribe `:wq` y pulsa `Enter`.
3. Vuelve a ejecutar: `git push origin main`

---

## 6. Entregables para el informe
Realiza el informe en pdf entrégalo en la tarea abierta en el aula virtual. Además se revisará tu aportación al repositorio en GitHub
