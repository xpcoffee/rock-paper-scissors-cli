# Alignment

This file tracks thoughts used to align with others.

<!-- TOC -->
* [Alignment](#alignment)
  * [Project requirements](#project-requirements)
  * [Decisions](#decisions)
    * [01 Async preparation phases before the reveal](#01-async-preparation-phases-before-the-reveal)
    * [02 Draws are valid end-conditions](#02-draws-are-valid-end-conditions)
    * [03 Re-use the core classes from rock-paper-scissors](#03-re-use-the-core-classes-from-rock-paper-scissors)
    * [04 Manually build the CLI](#04-manually-build-the-cli)
<!-- TOC -->


## Project requirements

Games
1. a player MUST be able to start a new game
2. a player MUST be able to abandon a game without picking an action
3. a player MUST be able to play multiple rounds before the game ends
4. the game SHOULD mark ongoing games as abandoned after a TTL
5. the game SHOULD prevent a game from starting if there are too many games in progress

Actions
1. a player MUST be able to pick an action in a new game
2. a player MUST NOT see their opponent's action before they have picked their action

End condition
1. a winner MUST be determined as soon as both players have submitted their moves
2. players MUST be notified as soon as a winner has been determined

History
1. a player MUST NOT be able to change the results of a game that is concluded
2. a player SHOULD be able to see previously played games

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

### 03 Re-use the core classes from rock-paper-scissors webapp

I originally built this as a web-application; most of the core game logic can be re-used.

See the previous project here [rock-paper-scissors](https://github.com/xpcoffee/rock-paper-scissors).

### 04 Manually build the CLI

Original plan was to use [Spring Shell](https://docs.spring.io/spring-shell/reference/getting-started.html) as the CLI tooling, but many things don't work due to 
console limitations between IntelliJ, Windows terminals, and JLine (which is what Spring Shell is built on).

After trying with one external tool I think it's now best to just do a custom implementation.