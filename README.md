
# DiceGame with MongoDB and JWT

El juego de dados se juega con dos dados. En caso de que el resultado de la suma de los dos dados sea 7, la partida es ganada, si no es perdida. Un jugador/a puede ver un listado de todas las tiradas que ha hecho y el porcentaje de éxito.  

Para poder jugar al juego y realizar una tirada, un usuario/a debe registrarse con un nombre no repetido. Al crearse, se le asigna un identificador numérico único y una fecha de registro. Si el usuario/a así lo desea, puedes no añadir ningún nombre y se llamará "ANÓNIMO". Puede haber más de un jugador "ANÓNIMO".  
Cada jugador/a puede ver un listado de todas las tiradas que ha hecho, con el valor de cada dado y si se ha ganado o no la partida. Además, puede saber su porcentaje de éxito por todas las tiradas que ha hecho.  

No se puede eliminar una partida en concreto, pero sí se puede eliminar todo el listado de tiradas por un jugador/a.  

El software debe permitir listar a todos los jugadores/as que hay en el sistema, el porcentaje de éxito de cada jugador/a y el porcentaje de éxito medio de todos los jugadores/as en el sistema.


## Endpoints

Se puede utilizar PostMan poniendo la siguiente dirección http://localhost:8080 y a continuación:

POST: /players: crea un jugador/a. 

PUT /players: modifica el nombre del jugador/a.

POST /players/{id}/games/ : un jugador/a específico realiza una tirada de los dados.  

DELETE /players/{id}/games: elimina las tiradas del jugador/a.

GET /players/: devuelve el listado de todos los jugadores/as del sistema con su porcentaje medio de éxitos. 

GET /players/{id}/games: devuelve el listado de jugadas por un jugador/a.  

GET /players/ranking: devuelve el ranking medio de todos los jugadores/as del sistema. Es decir, el porcentaje medio de éxitos. 

GET /players/ranking/loser: devuelve al jugador/a con peor porcentaje de éxito.  

GET /players/ranking/winner: devuelve al jugador con peor porcentaje de éxito. 


## Seguridad

En este proyecto tenemos también implmenetada la seguridad con Spring Security y JWT.

Para registrar un usuario administrador debe ser a través de la dirección http://localhost:8080/auth/register y pasar los parámetros user_id, user_name y password.

Para loguearnos debe ser a través de la dirección  http://localhost:8080/auth/login y pasar los parámetros user_name y password.

Y así podremos ejecutar cada endpoint con el token que nos genere al loguearnos.
