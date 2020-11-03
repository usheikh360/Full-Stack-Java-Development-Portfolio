function getAllGames() {
    fetch("http://localhost:8080/game")
        .then(response => response.json())
        .then((respone) => {
            document.getElementById("allGames").innerHTML = "";//REMOVING ANY TABLES
            document.getElementById("newGameInfoOrErrorMessage").innerHTML = "";//REMOVING ANY ERRORS OR GAME INFO
            document.getElementById("tableHeadings").innerHTML = "<th>Game Id</th> <th>Answer</th> <th>Status</th>";
            respone.forEach(game =>
                document.getElementById("allGames").innerHTML += "<tr>" + "<td>" + game.gameId + "</td>" + "<td>" + game.generatedNumber + "</td>"+  "<td>" + game.status + "</td>" + "</tr>") 
        }            
        )
        .catch((err) => console.log("fail: " + err));
}


function beginGame(){
    
    fetch("http://localhost:8080/begin", {
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json'
        } 
    })
    .then(response => response.json())
    .then((r) => {
    document.getElementById("tableHeadings").innerHTML = "";
    document.getElementById("allGames").innerHTML = "";
        document.getElementById("newGameInfoOrErrorMessage").innerHTML = "<h2> The new Game id is: " + r + "</h2>"})   
}


function getGameById(){
    document.getElementById("newGameInfoOrErrorMessage").innerHTML = "";//ONLY REMOVING THE ERROR OR GAME INFO BECUASE THE TABLE ELEMENT WOULD BE ADJUSTED BELOW
    const gameId = document.forms["specifcGameForm"]["gameId"].value;
    fetch("http://localhost:8080/game/" + gameId)
    .then(response => response.json())
    .then((r) => {
        document.getElementById("tableHeadings").innerHTML = "<th>Game Id</th> <th>Answer</th> <th>Status</th>";
        document.getElementById("allGames").innerHTML = "<tr>" + "<td>" +  r.gameId + "</td>" + "<td>" + r.generatedNumber + "</td>"+  "<td>" + r.status  + "</td>" + "</tr>"})
    .catch((err) => console.log("fail: " + err));
}

function addUsersGuess() {
    const gameID = document.forms["guessForm"]["gameId"].value;
    const guess = document.forms["guessForm"]["guess"].value;
    const data = { gameId: gameID, guess: guess };
    fetch("http://localhost:8080/guess", {

        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            document.getElementById("allGames").innerHTML = "";
            document.getElementById("newGameInfoOrErrorMessage").innerHTML = "";
            document.getElementById("tableHeadings").innerHTML = "";
            if (response.status == 203) {//200 still means request was successfull, should have used 404
                document.getElementById("newGameInfoOrErrorMessage").innerHTML = "<h2> For the guess to be valid, the guess can not contain repeating digits and must have exactly 4 digits.</h2>"
            }else{
                document.getElementById("tableHeadings").innerHTML = "<th>Round number</th> <th>Game Id</th> <th>Guess result</th>";
                    response.json().then((guess) => {
                        document.getElementById("allGames").innerHTML = "<tr>" + "<td>" +  guess.roundNumber + "</td>" + "<td>" + guess.gameId + "</td>"+  "<td>" + guess.result  + "</td>" + "</tr>"
                    }) 
            }
                 
    })
        .catch ((err) => console.log("Error: " + err));
}


function getRoundsByGameId(){
    document.getElementById("newGameInfoOrErrorMessage").innerHTML = "";
    const gameId = document.forms["roundsForSpecificGames"]["gameId"].value;
    fetch("http://localhost:8080/rounds/" + gameId)
    .then(response => response.json())
    .then(function(response){
        document.getElementById("allGames").innerHTML = "";//DELETING THE TABLE ROWS
        response.forEach((round) => {
            document.getElementById("tableHeadings").innerHTML = "<th>Round number</th> <th>Game Id</th> <th>Date</th> <th>Guess result</th>";
            document.getElementById("allGames").innerHTML += "<tr>" + "<td>" +  round.roundNumber + "</td>" + "<td>" + round.gameId + "</td>"+  "<td>" + round.date  + "</td>" + "<td>" + round.result  + "</td>" + "</tr>"})
        }       
    )
    .catch((err) => console.log("fail: " + err));
}
