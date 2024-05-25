# rock-paper-scissors

A rock-paper-scissors game.

## Pre-requisites

## Running

## Usage

Start a new game

Play your move

See past games

## Develop


### Requirements

Games
1. a player MUST be able to start a new game
1. a player MUST be able to abandon a game without picking an action
1. the game SHOULD mark ongoing games as abandonned after a TTL
1. the game SHOULD prevent a game from starting if there are too many games in progress
 
Actions
1. a player MUST be able to pick an action in a new game
1. a player MUST NOT see their opponent's action before they have picked their action

End condition
1. a winner MUST be determined as soon as both players have submitted their moves
1. players MUST be notified as soon as a winner has been determined

History
1. a player MUST NOT be able to change the results of a game that is concluded
1. a player SHOULD be able to see previously played games

Players
1. the game MUST support 1 human player against 1 computer player


**Out of scope**

 - authentication (different players & permissions)

## Decisions

### 01 Async preparation phases before the reveal

A simultaneous reveal requires some set-up:

 - one player gives intention to play the game
 - another player joins the game
 - player 1 chooses their action
 - player 2 chooses their action
 - ONLY NOW does reveal + end condition occur

This setup is inherently asynchronous.
   
### 02 Draws are valid end-conditions

In real life, people often re-play the game in case of a draw until a winner is decided.

For simplicity, we're going to consider a draw to be a valid end-state, and force players to start a new game.

### 03 Game state is server-side

To prevent game state from being tampered-with during the preparation phases, we store game state on the server.