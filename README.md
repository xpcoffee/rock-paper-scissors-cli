# rock-paper-scissors

A rock-paper-scissors game.

## Pre-requisites

## Running

Launch the application:
```bash
./gradlew bootRun
```

## Usage

It's recommended to use a client like Postman and to import [the OpenAPI definition](./src/main/resources/openapi.yaml)

See existing games

```bash
curl -X GET localhost:8888/game
```

Start a new game

```bash
curl -X PUT localhost:8888/game
# => JSON with gameId
```

Fetch the state of the game

```bash
curl -X GET "localhost:8888/game/${gameId}"
```

Play your move

```bash
curl -X POST \
    "localhost:8888/game/${gameId}" \
    -H "Content-Type:application/json" \
    -d '{ "playerId": "marlene89", "actionType": "rock" }'
```


## Developing

Find reasons for decisions made in [alignment](./docs/alignment.md);