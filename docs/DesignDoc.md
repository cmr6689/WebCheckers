---
geometry: margin=1in
---
# PROJECT Design Documentation

## Team Information
* Team name: Team E
* Team members
  * Ronald Torrelli
  * Cameron Riu
  * Evan Price
  * Mathew Klein

## Executive Summary

The application must allow players to play checkers with other players
who are currently signed-in. The game user interface (UI) will support 
a game experience using drag-and-drop browser capabilities for making moves.

### Purpose

The purpose of the WebCheckers application is to allow a group of users to 
play a game of checkers with each other online.

### Glossary and Acronyms

| Term | Definition |
|------|------------|
| VO | Value Object |
| MVP | Minimum Viable Product | 
| UI | User Interface |

## Requirements

This section describes the features of the application.

> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._

 Signign In
 
 Start Game
 
 Checker Board
 
 Checker Pieces
 
 Resignation
 
 Game Play
 
 Replay
 
 Spectator Mode



### Definition of MVP

The MVP is the most basic form of the program. Having only the most basic of features for time of release. 

### MVP Features

The MVP features are the ability to sign in, select an opponent, start a game, perform basic checkers moves (Move, Single and multi-jump, kinging and winging/losing).  

### Roadmap of Enhancements

We plan to include two top-level enhancements to our program, Spectator Mode and Replay Mode. We plan to implement them in that order as well.

## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](domainmodel.png)

The application domain of WebCheckers is split up into an application, 
model, and UI tier to differentiate responsibilities of certain classes.
The application tier is where we handle the games being played and control
players in and out of a game. The model tier controls the actual game play
such as making the board and making moves. The UI tier is responsible for
the web server itself and each route that a user can play. Each route is 
dependent on whether the user has signed in or not. 


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a
browser.  The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](statechart.jpg)

The user when first navigating to the home page of WebCheckers will see a login button in the navbar and the number of players currently logged in. 
After clicking on the sign in button the player will be brought to the sign in page where they may enter a valid username. 
A valid username is defined as one that is not already in use and that does not contain more than one or only special characters. 
After signing in the player will see a list of all currently active players.   

After the player has selected another player to play against both players are put into the game. After the start of the game the players see the game UI and begin playing. 
From the there they can resign or keeping playing to the game is over. Once the game is over both players can click the exit button to be brought back to the home page.


### UI Tier

![The WebCheckers UI Tier UML Diagram](ui_uml.png)

The UI tier of the WebCheckers application is responsible for handling all
of the changes to the view of a player on the server. The GameData class
stores all of the information in the VM map which is supplied to the game.ftl
that sets up the checkers board based on the VM information. 

The server is run on the WebServer class and contains GET and POST route calls, 
starting with GetHomeRoute which renders the home page. When a player signs in 
they call GetSignInRoute to allow them to see the sign in page and join the server 
with a valid name which calls PostSignInRoute that renders the home page
and shows all other player names that are online instead of just the number 
of players. Players can sign out as well and that calls PostSignOutRoute.

Once a player is signed in they can click on another players name to call
GetGameRoute which displays the checkers board. The player that was clicked
on will automatically call GetGameRoute from the home page after they are
clicked on and will be displayed a version of the board.

After both players have joined the game the red player can make a move first.
The white player will call PostCheckTurnRoute which will not allow them to
make a move until the red player makes a move which calls PostValidateMoveRoute
that verifies whether the move is allowed to be made, and then submits the move
which calls PostSubmitMoveRoute and makes the move on the server. 

Once a player has moved a piece they can use the Backup button to call 
PostBackUpMoveRoute which returns the piece. The player also has the option to
press the Resign button that calls PostResignRoute and removes the player from
the game. When the game is over, PostGameOverRoute is called that displays the
proper messages to each player.

### Application Tier

![The WebCheckers UI Tier UML Diagram](application_uml.png)

The Application tier of the WebCheckers application is responsible for
handling the games that are being run on the server. We have a GameCenter
class that acts as the hub for all the games on the server. It contains a 
list of active and dormant games that can be accessed by providing a player
in one of those games. The PlayerLobby class is where we store player information
on the server. Every player that signs into the server will only be successfully
added to the lobby if they enter a valid name. The instance of GameCenter is
stored in PlayerLobby in order to access from the UI tier.

### Model Tier

![The WebCheckers UI Tier UML Diagram](model_uml.png)

The Model tier of the WebCheckers application is responsible for the rules and
moves of a game of checkers. The BoardView class is how the player would view the
board based on whether they are the red or white player. The Game class allows us
to access the elements of a game such as the players and board view. The Row class
sets up the rows of the board with Spaces that contain a piece, an index, and
the color of the space. The Move class uses Positions both before and after to verify
specific moves made by the player. The Player class is where we store player
information such as name and color.

### Design Improvements

If the project were to continue, we would improve the design of our GameCenter class. 
As it stands, this class performs most of the game logic for multiple games, however;
some of that responsibility is also shared with the PlayerLobby class. If we were
to continue with this project, we would remove some of the game logic within PlayerLobby 
and put it into GameCenter. The metrics of our code show that the PostValidateMoveRoute contains a 
heavy amount of logic. This logic could be improved by being placed elsewhere to allow the 
PostValidateMoveRoute to simply focus on performing after those conditions are met, not checking
those conditions itself.

## Testing
 This section will provide information about the testing performed
 and the results of the testing.

### Acceptance Testing


Number of stories that passed every acceptance criteria test: 0

Number of stories with some failed acceptance test: 3

Number of stories with no testing: 5

Some issues to note: The other player's board will revert to the first players board
after the first player submits their turn.The moves made by a player do not stay on the board 
after the move is submitted.


### Unit Testing and Code Coverage

####UI Tier Code Coverage
![The WebCheckers UI Tier Code Coverage](UI Tier.png)

####Model Tier Code Coverage
![The WebCheckers Model Tier Code Coverage](Model Tier.png)

####Application Tier Code Coverage
![The WebCheckers Application Tier Code Coverage](Application Tier.png)


