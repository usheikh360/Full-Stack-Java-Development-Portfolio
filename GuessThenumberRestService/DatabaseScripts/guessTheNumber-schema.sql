#Umair Babar Sheikh
#28/08/2020

CREATE DATABASE guessTheNumber;

USE guessTheNumber;

CREATE TABLE game (
  gameId INT AUTO_INCREMENT,
  generatedNumber CHAR(4),
  status VARCHAR(11) NOT NULL,
  guessed VARCHAR(3) NOT NULL,

  CONSTRAINT pk_game
    PRIMARY KEY (gameId)
);

CREATE TABLE round(
  roundNumber INT,
  gameId INT,
  dateTime DATETIME DEFAULT CURRENT_TIMESTAMP,
  numberGuessed CHAR(4) NOT NULL,
  result VARCHAR(6),

  CONSTRAINT pk_round
    PRIMARY KEY (roundNumber, gameId),
  CONSTRAINT fk_game
    FOREIGN KEY (gameId)
    REFERENCES game(gameId)
);
