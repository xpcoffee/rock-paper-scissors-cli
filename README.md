# rock-paper-scissors

A rock-paper-scissors game.

## Pre-requisites

* Java 21 SDK

## Running

Launch the application:
```bash
./gradlew bootRun
```

## Usage

It's recommended to use a client like Postman and to import [the OpenAPI definition](./src/main/resources/openapi.yaml)

---
The available actions are...

Start a new game<br>
_You can specify the number of human players. Computer adversaries will be used for missing players._

```bash
curl -X PUT localhost:8888/game -d '{ "humanPlayers": 1 }'
# => JSON with gameId
```

Play a move

```bash
curl -X POST \
    "localhost:8888/game/${gameId}" \
    -H "Content-Type:application/json" \
    -d '{ "playerId": "marlene89", "actionType": "rock" }'
# => game details after your move is played
```

Fetch the state of the game<br>
_Currently you need to poll for results on pending games._

```bash
curl -X GET "localhost:8888/game/${gameId}"
# => game details
```

See existing games

```bash
curl -X GET localhost:8888/game
# => list of games with their details
```


## Developing

### Resources

Find reasons for decisions made in [alignment](./docs/alignment.md);

### Running tests

```bash
./gradlew test
```

### Making changes to the API

1. Update [openapi.yaml](./src/main/resources/openapi.yaml)
2. Generate interfaces
    ```bash
    ./gradlew openApiGenerate
    ```
3. Code with the new interfaces