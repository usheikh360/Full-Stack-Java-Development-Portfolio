# Guess The Number REST Service

The goal of this project was to create REST server to facilitate playing a number guessing game known as "Bulls and Cows". 
In each game, a 4-digit number is generated where every digit is different. For each round, the user guesses a number and is told the exact and partial digit matches.

• An exact match occurs when the user guesses the correct digit in the correct position.
• A partial match occurs when the user guesses the correct digit but in the wrong position.

Once the number is guessed (exact matches for all digits) the user wins the game.


# The Application demonsteates an understanding of:

• The use of Spring Boot REST and the use of JDBC Template to access the database

• Use of MVC design pattern 

• A Service layer to manage the game rules, such as generating initial answers for a game and calculating the results of a guess.

• Unit Testing of all of the public DAO interface methods 

• Use of Postman to verify your endpoints behave the way I would expect them to be


# The REST endpoints created for this application are:

• "begin" - POST – Starts a game, generates an answer, and sets the correct status. Should return a 201 CREATED message as well as the created gameId.

• "guess" – POST – Makes a guess by passing the guess and gameId in as JSON. The program must calculate the results of the guess and mark the game finished if the guess is correct. It returns the Round object with the results filled in.

• "game" – GET – Returns a list of all games. Be sure in-progress games do not display their answer.

• "game/{gameId}" - GET – Returns a specific game based on ID. Be sure in-progress games do not display their answer.
  "rounds/{gameId} – GET – Returns a list of rounds for the specified game sorted by time.



