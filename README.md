# NiceTry
### Cynthia Labrador Caballero
Proyecto Final DAM
![image](https://cdn.discordapp.com/attachments/787363155494830091/913754037936087050/unknown.png)

# Readme de REALESTATE
### (BackEnd) 

> Con esta aplicación podrás gestionar, crear o borrar tus composiciones personalizadas con los
> personajes de Teamfight Tactics.
> Puedes votar las listas de otros usuarios y realizar comentarios.
> En parte actua como red social, aparte de ser una app para compartir conocimientos sobre las
> mejores composiciones y consejos sobre el juego.
> Conecta a los usuarios.


## Instalacion

La Api está desplegada en Heroku pero si la quieres ejecutar en local es necesario disponer de una aplicación que permita ejecutar proyectos maven, un ejemplo podría ser un IDE como Intellij u otro cualquiera.
Hacer un git clone de este repositorio y ejecutar la carpeta Backend.
En la parte del Backend el proyecto está basado en Intellij y en java17.
URL en Heroku:
```sh
https://nicetry-api.herokuapp.com/
```

## Pomp

Las dependencias que usaremos para eeste proyecto serán

- [H2DataBase] - Un gestor de base de datos que nos proporcionará una persistencia de datos en un xml (Pruebas en local)
- [SpringWeb] - Será nuestro servidor web local
- [Lombok] - Una herramienta muy útil que proporcionará atajos a la hora de construir entidades y relaciones
- [OpneApi3] - Será con Swagger la manera de comentar los controladores de manera cómoda con anotaciones
- [SpringJpa] - Será la manera que tengamos de de acceder a la base de datos basada en Hibernate
- [Jsonwebtoken] - Nos servirá para crear y modificar tokens
- [Apache-Tika] - Detecta y extrae metadatos y contenido de texto estructurado de varios documentos utilizando bibliotecas de análisis existentes
- [Imgscalr] - Escala las imágenes que guardemos en la app
- [Postgresql] - Un gestor de base de datos que guarda los datos que introduzcamos en la app en la base de datos creada en Heroku
- [Springframework Security] - Permite gestionar todo lo relativo a la seguridad de nuestra aplicación web, desde el protocolo de seguridad, hasta los roles que necesitan los usuarios para acceder a los diferentes recursos de la aplicación

## Controladores

| Controller | Descripcción |
| ------ | ------ |
| ChampionsController | En este controllador meteremos todos los endPoints que tengan que ver con campeones |
| CommentController | En este controllador meteremos todos los endPoints que tengan que ver con comentarios |
| CompositionController | En este controllador meteremos todos los endPoints que tengan que ver con las composiciones |
| TraitsController |En este controllador meteremos todos los endPoints que tengan que ver con las características de los campeones |
| UserController |En este controllador meteremos todos los endPoints que tengan que ver con las características de los usuarios |


#### ChampionsController
```sh
createChampion  //  Crea un campeón, debe de tener un trait agregado // 201 || 400
getChampion  //  Obtiene un campeón por id // 200 || 404
getChampionByName  //  Obtiene un campeón por nombre // 200 || 404
getAllChampions  // Obtiene todos los campeones // 200 || 404
updateChampion // Actualiza un campeón // 200 || 404
deleteChampion // Borra un campeón // 200 || 404
```

#### CommentController
```sh
getComments  //  Devuelve todos los comentarios de una composición // 200 || 404
createComment  //  Crea un comentario // 201
deleteComment  //  Borrar tu comentario // 200 || 404
```

#### CompositionController
```sh
getComposition  //  Obtiene las composiciones de un usuario // 200 || 404
getAllComposition  // Obtiene todas las composiciones // 200 || 404
getCompositionById  // Obtiene una composicion por id // 200 || 404
createComposition  // Crea una nueva composicion // 201 || 400
updateComposition // Actualizar una composicion // 200 || 400
deleteComposition // Borra una composicion // 200 || 404
addVote // Votar una composicion // 200 || 400
removeVote // Borrar tu voto // 200 || 404
```

#### TraitsController

```sh
createTrait  //  Crea una nueva característica // 201 || 400
getTraitById  //  Encontrar una característica por id // 200 || 404
getTraitByName // Encontrar una característica por nombre // 200 || 404
getAllTraits // Encontrar todas las características // 200 ||404
updateTrait // Editar una característica // 200 || 400
deleteTrait // Borra una característica // 200 || 404 || 400
```

#### UserController

```sh
getAllUsers  //  Obtener todos los usuarios // 201 || 404
getAllAdmins  //  Obtener todos los administradores // 200 || 404
updateProfile // Editar mi perfil de usuario // 200
```



 Creado por: Cynthia Labrador Caballero
