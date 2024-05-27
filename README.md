# rock-paper-scissors-cli

A rock-paper-scissors command-line game.

For an API based implementation, see [xpcoffee/rock-paper-scissors](https://github.com/xpcoffee/rock-paper-scissors).

## Output

```text
-------------------
Rock-Paper-Scissors
-------------------

Please enter your name: rick

Games left: 4
┌- Possible moves
| 1. rock
| 2. paper
| 3. scissors
└ 0. exit
Pick a move: 2

┌- Game Results
├┬ Player:computer-adversary-bff71a39-292b-4c9d-ad3c-101997986ba9
|└ Action:Scissors
├┬ Player:rick
|└ Action:Paper
└ Winner: computer-adversary-bff71a39-292b-4c9d-ad3c-101997986ba9
-- Press <ENTER> to continue --
```

## Pre-requisites

* Java 21 SDK

## Running

* via **command line** (e.g. via Github codespace): 
  ```bash
  ./gradlew -q --console plain run
  ```
* via **IntelliJ**: double-click `run` from Gradle tasks.

## Developing

### Resources

Find reasons for decisions made in [alignment](./docs/alignment.md);

### Running tests

```bash
./gradlew test
```
