# PokeAPI inegration exercice

## Requirements
![Static Badge](https://img.shields.io/badge/JDK-21-orange?logo=openjdk)
![Static Badge](https://img.shields.io/badge/Maven-4-red?logo=apachemaven)
![Static Badge](https://img.shields.io/badge/Springboot-3.5.2-green?logo=springboot)

## Runing the application
Execute com.alea.pokeapi.PokeapiApplication from your IDE or use the command:
```shell
mvn spring-boot:run
```

## Endpoints
> **NOTE** Default port: 8080

Returns the 5 heaviest pokemons
```
/pokemon/weight
```

Returns the 5 highest pokemons
```
/pokemon/height
```

Returns the 5 pokemons with more base experience
```
/pokemon/base-experience
```

## Exercise explanation
### Controller
The controller just returns the list of 5 pokemon filtering by its property, this could be improved by making only 1 endpoint and giving by parameter the element to filter. Also, it could be done with a pagination system to return the values paginated, and the length be determined by another parameter (currently the limit is hardcoded in the service call).

### Service
All 3 functions from the service use a common function to retrieve the pokemons from the external API.
The problem with this API is that the elements to filter are only accessible by getting each pokemon individually, which gives a very long execution time for each request. My solution to this issue is to make a cache to only the first request be slow and the next ones use the cached data (if I had more time, I would have tried to do a batch service to fetch the API to maintain the cache up to date without needing to do in the execution time of a request).
The cache has a time to live of 5 minutes set in the application.yml file.

### Error handler
I've done a custom error handler to catch the custom errors that could be thrown (like an error trying to connect to an external API), so all the return of this error is parsed as a response. This could be used by a hypothetic frontend to show this custom error message on the screen.