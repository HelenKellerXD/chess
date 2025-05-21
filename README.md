# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```





Phase 2 Sequence Diagram

link to diagram -> https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYHSQ4AAaz5HRgyQyqRgotGMGACClHDCKAAHtCNIziSyTqDcSpyvyoIycSIVKbCkdLjAFJqUMBtfUZegAKK6lTYAiJW3HXKnbLmcoAFicAGZuv1RupgOTxlMfVBvGUVR07uq3R6wvJpeg+gd0BxMEbmeoHUU7ShymgfAgECG8adqyTVKUQFLMlbaR1GQztMba6djKUFBwOHKBdp2-bO2Oaz2++7MgofGBUrDgDvUiOq6vu2ypzO59vdzawcvToCLtmEdDkWoW1hH8C607s9dc2KYwwOUqxPAeu71BAJZoJMIH7CGlB1hGGDlAATE4TgJgMAGjLBUyPFM4GpJB0F4as5acKYXi+P4ATQOw5IwAAMhA0RJAEaQZFkyDmGyv7lNUdRNK0BjqAkaAJqqKCzC8bwcAcv4FF+lw9FJMn6K8SwAucwIFHejYIKxACSaCwixbGouisTYvpBRdiypTklSNJSaORJrgUU7cryVqCsKOZDIBEpSrKVp5mgEDMAAZr4lH6QAPGG9mks2rYIVAiW5PppQAHKRZysUcH6AZBmgmDIWAmX8TGUZYUmqgpvc6aZtA5RSTAEXRYVZZmJRkrurKJlUBqSBzlUGlyUuKCZclPYwDyIDQJC4AwLJSxTWG+T8XywSHtASAAF4oEV-ooIG4nlTxGBVcUlwxgAjHVLKNSsGZZuUa3vPNrILUtKDgD1FYnu5Z6bfWVDgvO1qLvpbKzb2-YoNee5Eces3nhyl4uoet7moYYPKc+UJIlAkSqB+mCEz+t1-thgW4cB+FfERJGloz5HpUhV1gGhGFYVJZEEX0LNQWzcGA5RnjeH4gReCg6DMaxvjMBx6SZJdOTMGa1DOhU0g+kxPr1D6zQtKJqjid0IvQelSk6Zc1voJT9tQHpeOlIZ9g1Du+6HqzaA2Xjdmng5HAoNwW6Hr7EGiwHwNMt2nkctI4cUoYyN+WEjtlYTN0NqU5nK2TFO-plhOlP0nvknOys9RVeclKU6GYb0fRV8dMDKzA5NrlFLYS5WUs0YEkJzkx0IwAA4oBrKq1xGu8drjeVJPRum-YgFW37se2-k5fZ87QKUG7+fILE09JtHxGx4HDbByDDlOcj-tX-7bkJyySdcjyHCshn2hCiztvG2-UZTzTQMNZAc5IDQRgFNGaIcUotjbKXMM2VKS7V3PtI6J0SoXXrmgmmpQHpPWTKmCYb1WowCGiNOc-sB7wKSogns-95AbXyPDJyF81CwnfuOVQX8nL5gQFPQCNpc6EPztw1Q49YjFwQFgVBuRy6yOYBAKKwiYAbyTAva6kjG7NywtItMZ81EaO0WoCig9qIywCNgHwUBsDcHgIjURao57qwqnxIhlRagNHXpvTB19oIJgsTlQCCkaZ2yPmUbOswwlBUJifCGFoNxyBQNw2ECTRjTGzrfFJKB74f1JE-YB6BX433jvwr+3lf7YxvAA-yB9QGyh5JA0aMAYEK0YbkeGqUUE0wbuCDBRFsHHWKmdUqujKr6Luk4R6rd6ovQoS1bMNCoEwHoVYnp8NWHAHYfDKKP9MnZJQHwjy+QpxHM4KybhmctGAXCeKFpUMFRKgeaMJ5hgNRah1PqWIhpmHU3zm4lAuMGzAuXq6X5XpoITPOsGX8XNNbRjjKQhq5DmrvQ+YYPMPz3Tak6d6GC2yEpMIfkgtKtkOHMIRpuDJgEsmPMAucxOlzMazlBeIl2iVsrcLgIjeRijBnKJdqUflriyA+DXNM3lRDDGt24XhU5Uwe7dj7q2MIaTMjLF6tY6WtFLDh0MskGAAApCAPJQWBB0AgUA0ppneJ1tmaolJhItAsVvGOITehOOAEaqAcAICGSgPEwCRlpCROddEp8pQ4kwB6H6gNQaQ1htGBG7SMTknggAFaWrQJk+NFiI0yTtcm4N0B8miCKfwxyFJn6xwqdBVln92XfxufUo8jSgHeqdi8tptDOmxzgWS3ptL+npSGRaEZe0oCHXGadBFZUCFZSISQxZz1yF9EoWsiBg6tl6p2bSvZBzaXXI4Cc8N0gW3qC-ue25Yju04ojcFAa1rpAQDUB1SKMA9TQiqWuSF4IJ2wzBvxPKYACo+E4PCqZSKwYVWIU4WqG6yFNW3asskgEjCftZJ1X9-ywAMNHfDO5MMg40opT2GIKboA1GwOUpNlBaNQGxC8ljmziVHqo6UDj0KCVhFhU7UDS9nT8cLEJtAsGLrwfDNzVF8ZUMYvQzurDapFohvzL8olpFSV4wQTxjjIRfhIFtWIal8MPQcDgBqYEsImOBoraxgDbKpx+C0JkLlACQAOMhKQBzGgJGrvzhank3ChWTrDOXbh5r80dylb3ZBlhZWzLKAqxMm77h9Bi6FtAx0GFD1sV4f18BuB4HVNgJxhB4iJBSGrbimsnXLz1gbI2JtWjGF3uXEAZW4TYiSVtd2PX3TWgAEK8NPTxsOEcUBWlUBN2aX9ptp00b5cjd9KPFJ7E5ObE2XmxG2CIm4P0kjSFG4w+GkIwAOLQKyOQMAyuxBgPdKG4KShyvzkGtAUVkDgGk8GAh1UnBuHRcsjD2K1vyG7gQVsq1IQGAwMR-T5KtulBAxR+Gy3MjSIW8wpbqdPO2a82wiztKuEzz2yFaHh2cUnZkOdsll2KQ3bu8wR7zAXtkZJyK7KX2fuEDAP95d3MPuNxjCDpTYPVOdsFAd2H2B4fmCRw2AzqP0cbcxwTlA-t5s3oEW2rH3zYcntJzxpyOvKdvrlyIrpt3wH04u7Sq7LP1Rs-dE9l7qN1vveCwU3jiR+d-cXVMwHa7geg63dLrnwBqfy8V4jg4h6SPjuQYfJ8ovwSIBGxF1B5dGR6AMDAYbeAO5qpZBqhAyXQ-OqbnzSXW78-6EMMX0eDCgA


Txt for diagram (below)

actor Client
participant Server
participant Handler
participant Service
participant DataAccess
database db

entryspacing 0.9
group #navy Registration #white
Client -> Server: [POST] /user\n{"username":" ", "password":" ", "email":" "}
Server -> Handler: {"username":" ", "password":" ", "email":" "}
Handler -> Service: register(RegisterRequest)
Service -> DataAccess: getUser(username)
DataAccess -> db:Find UserData by username
break User with username already exists
DataAccess --> Service: UserData
Service --> Server: AlreadyTakenException
Server --> Client: 403\n{"message": "Error: username already taken"}
end
DataAccess --> Service: null
Service -> DataAccess:createUser(userData)
DataAccess -> db:Add UserData
Service -> DataAccess:createAuth(authData)
DataAccess -> db:Add AuthData
Service --> Handler: RegisterResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "authToken" : " "}
end

group #orange Login #white
Client -> Server: [POST] /session\n{username, password}
Server->Handler:{username, password}
Handler->Service:logIn(LoginRequest)
Service->DataAccess:getUser(username)
DataAccess->db:Find UserData by username
break Username not found
Service<--DataAccess:null
Server<--Service:NotFoundException
Client<--Server:404\n{"message": "Error: username not found"}
end
break Invalid Password
Service<--DataAccess: incorrect password
Service-->Server: UnauthorizedException
Client<--Server:401\n{"message":"Error: password is incorrect"}
end
DataAccess --> Service: UserData
Service -> DataAccess:createAuth(authData)
DataAccess -> db:Add AuthData
Service --> Handler: RegisterResult
Handler --> Server: {"username" : " ", "authToken" : " "}
Server --> Client: 200\n{"username" : " ", "authToken" : " "}
end

group #green Logout #white
Client -> Server: [DELETE] /session\nauthToken
Server->Handler:authToken
Handler->Service:logOut(authToken)
Service->DataAccess:deleteAuth(authToken)
DataAccess->db:Delete AuthData by authToken
Handler<--Service:LogoutResult
Server<--Handler:{"logged out"}
Client<--Server:200\n{"logged out successful"}
end

group #red List Games #white
Client -> Server: [GET] /game\nauthToken
Server->Handler:authToken
Handler->Service:listGames(authToken)
Service->DataAccess:getAuthToken(authToken)
DataAccess->db:Finds AuthData by authToken
break invalid token 
Service<--DataAccess:null
Server<--Service:UnauthorizedException
Client<--Server:401\n{"message":"Error: Invalid Token"}
end
Service<--DataAccess:AuthData
Service->DataAccess:getGames()
DataAccess->db:get all GameData
Handler<--Service:GamesListResult
Server<--Handler:List of all games
Client<--Server:200\n{"Games": list of games}
end

group #purple Create Game #white
Client -> Server: [POST] /game\nauthToken\n{gameName}
Server->Handler:authToken, gameName
Handler->Service:createGame(gameName,authToken)
Service->DataAccess:getAuthToken(authToken)
DataAccess->db:Finds AuthData by authToken
break invalid token 
Service<--DataAccess:null
Server<--Service:UnauthorizedException
Client<--Server:401\n{"message":"Error: Invalid Token"}
end
Service<--DataAccess:AuthData
Service->DataAccess:findGame(gameName)
DataAccess->db:finds GameData by gameName
break User with gameName already exists
DataAccess --> Service: GameData
Service --> Server: AlreadyTakenException
Server --> Client: 403\n{"message": "Error: game name already taken"}
end
Service<--DataAccess:null
Service->DataAccess:createGame(gameName)
DataAccess->db:Add GameData
Handler<-Service:GameCreateResult
Server<-Handler:GameCreate Success
Client<-Server:200\n{"Game" : gameName "successfully created"
end

group #yellow Join Game #black
Client -> Server: [PUT] /game\nauthToken\n{playerColor, gameID}
Server->Handler:authToken, {playerColor, gameID}
Handler->Service:joinGame(authToken, gameID, playerColor)
Service->DataAccess:getAuthToken(authToken)
DataAccess->db:Finds AuthData by authToken
break invalid token 
Service<--DataAccess:null
Server<--Service:UnauthorizedException
Client<--Server:401\n{"message":"Error: Invalid Token"}
end
Service<--DataAccess:AuthData
Service->DataAccess:findGame(gameID)
DataAccess->db:finds GameData by gameID
break Game Does not exist
DataAccess --> Service:null
Service --> Server:NotFoundException
Server --> Client:404\n{"message": "Error: game does not exist"}
end
Service<--DataAccess:GameData
Service->DataAccess:isColorOpen(playerColor)
break Color Taken
Service<--DataAccess:Color Already Taken
Service --> Server: AlreadyTakenException
Server --> Client: 403\n{"message": "Error: game color already taken"}
end
Service<--DataAccess:Color availible
Service->DataAccess:addCaller(playerColor)
DataAccess->db:update GameData current players
Handler<--Service:JoinGameResult
Server<--Handler:Game Joined Successfully
Client<--Server:200\n{"message":"Game Joined"}
end

group #gray Clear application #white
Client -> Server: [DELETE] /db
Server->Handler:clear()
Handler->Service:clearDB()
Service->DataAccess:deleteUsers()
DataAccess->db:delete all UserData
Service->DataAccess:getUsers()
break still users in DB
Service<-DataAccess:returns at least 1 UserData
Server<-Service:ConflictException
Client<-Server:409\n{"message":"Error: UserData still present"}
end
Service<--DataAccess:null
Service->DataAccess:deleteGames()
DataAccess->db:delete all GameData
Service->DataAccess:getGames()
break still games in DB
Service<-DataAccess:returns at least 1 GameData
Server<-Service:ConflictException
Client<-Server:409\n{"message":"Error: AuthData still present"}
end
Service<--DataAccess:null
Service->DataAccess:deleteTokens()
DataAccess->db:delete all AuthData
Service->DataAccess:getTokens()
break still tokens in DB
Service<-DataAccess:returns at least 1 authData
Server<-Service:ConflictException
Client<-Server:409\n{"message":"Error: GameData still present"}

end
Service<--DataAccess:null
Handler<-Service:ClearResult
Server<-Handler:Database cleared successfully
Client<-Server:200\n{"message":"Database cleared"}
end
