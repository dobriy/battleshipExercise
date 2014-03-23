 README
========

##Assumptions:

- Doesn't matter what kind of command goes it could be shot or move, until one ship will be left.
- will not create list of empty cells
- if ship hits the wall, it will not move, just turn (no error)
- if you hit same ship twice, will make no difference.
- for simplicity will return final result, in random order
- I am not testing things, that should be caught by standard validator
- You cannot move ship that been hit

## Requisites
- Java
- Gradle
- Preferred IDE/Text editor
- Internet connection

## Basic commands
- `$ gradle test`: run tests
- `$ gradle build`: builds the project
- `$ java -jar build/libs/battleship-spring-0.1.0.jar`: runs the server

Point your browser to http://localhost:8080/battleship to read the wording of the exercise.
