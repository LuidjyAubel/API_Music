# API_Music

This is a music api that I have created for a personnal project beacause I wanted to keep track of my music collection beaucause I have a lot of music in différents format and
sometimes I forget or I'm not sure if I have a specific album or version of an album. This application is inspired by another one of my project for a friend who as a lot of lego
and he wanted to keep track of his collection so I have created a lego application who is called Motify who is a bit smaller than this one but the idea is the same.

## Documentation

to see the documentation of the api you can use swagger, you can access it by adding /swagger-ui/index.html to the url of the api

exemple : http://localhost:8080/swagger-ui/index.html

## Database

this Api use a H2 database so you can use the console to see the data in the database, you can access it by adding /h2-console to the url of the api

exemple : http://localhost:8080/h2-console

## Installation

2 optinos :

the first one is to use the war file in the target folder or in the release section of the github repository
the second one is to clone the repository and use the command `./gradlew bootRun` on window and `gradle bootRun` on linux in the root folder of the project

## Usage

This Api is a music api so you can use it to store your music collection and share it with your friends or just to keep track of your music collection,
this is why I have created this API so I can keep track of my music collection and share it with my friends and family.

## Routes
Route User :
- get /api/users/
- get /api/users/{id}
- get /api/users/{id}/album
- post /api/users/
- put /api/users/{id}
- delete /api/users/{id}

Route Groupe :
- get /api/groupe/
- get /api/groupe/{id}
- post /api/groupe/
- put /api/groupe/{id}
- delete /api/groupe/{id}

Route Version :
- get /api/version/
- get /api/version/{id}
- post /api/version/
- put /api/version/{id}
- delete /api/version/

Route Genre :
- get /api/genre/
- get /api/genre/{id}
- post /api/genre/
- put /api/genre/{id}
- delete /api/genre/{id}

Route album :
- get /api/albums/
- get /api/albums/{id}
- post /api/albums/
- put /api/albums/{id}
- delete /api/albums/{id}

## Information

You can use this api is a open source project so you can use it as you want but I would like to be credited if you use it in a project and if you want to contribute to the project you can do it by creating a pull request on the github repository or
make a fork of the project and do what you want with it. If you have question don't hesitate to ask me I will be happy to help you if I can.
I'm not a native english speaker so I'm sorry if there is some mistake in the text of the project I will try to correct them as soon as possible.
I do a lot of little patch to the project when I develop it and others application that call it so I will try to keep the project up to date as much as possible.
