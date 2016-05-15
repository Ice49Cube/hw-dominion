package dominion.frontend.responses;

import dominion.routing.ResultBase;

public class StartGameResult extends ResultBase {

    public int id;
    public GameCard[] cards;
    public Player[] players;
    public String cardSet;
    public StartGameResult() {
        super("startGame");
    }
}

/*
"{
	"code": 200,
	"success": true,
	"method": "startGame",
	"id": 39,
	"players": [
		{
			"id": 102,
			"name": "one",
			"cards": [
				{
					"cardId": 649,
					"id": 1016,
					"pile": "Hand",
					"order": 5
				},
				{
					"cardId": 649,
					"id": 1017,
					"pile": "Hand",
					"order": 6
				},
				{
					"cardId": 649,
					"id": 1018,
					"pile": "Hand",
					"order": 7
				},
				{
					"cardId": 649,
					"id": 1019,
					"pile": "Hand",
					"order": 8
				},
				{
					"cardId": 649,
					"id": 1020,
					"pile": "Hand",
					"order": 9
				}
			],
			"actions": 1,
			"buys": 1
		},
		{
			"id": 103,
			"name": "two"
		},
		{
			"id": 104,
			"name": "three"
		}
	],
	"cards": [
		{
			"id": 648,
			"count": 40,
			"cost": 3,
			"deck": "Treasure",
			"isAction": false,
			"isCoin": true,
			"name": "silver",
			"value": 2
		},
		{
			"id": 649,
			"count": 39,
			"cost": 0,
			"deck": "Treasure",
			"isAction": false,
			"isCoin": true,
			"name": "copper",
			"value": 1
		},
		{
			"id": 650,
			"count": 12,
			"cost": 8,
			"deck": "Victory",
			"isAction": false,
			"isCoin": false,
			"name": "province",
			"value": 0
		},
		{
			"id": 651,
			"count": 12,
			"cost": 5,
			"deck": "Victory",
			"isAction": false,
			"isCoin": false,
			"name": "duchy",
			"value": 0
		},
		{
			"id": 652,
			"count": 12,
			"cost": 2,
			"deck": "Victory",
			"isAction": false,
			"isCoin": false,
			"name": "estate",
			"value": 0
		},
		{
			"id": 653,
			"count": 20,
			"cost": 0,
			"deck": "Victory",
			"isAction": false,
			"isCoin": false,
			"name": "curse",
			"value": 0
		},
		{
			"id": 654,
			"count": 10,
			"cost": 5,
			"deck": "Kingdom",
			"isAction": true,
			"isCoin": false,
			"name": "laboratory",
			"value": 0
		},
		{
			"id": 655,
			"count": 10,
			"cost": 4,
			"deck": "Kingdom",
			"isAction": true,
			"isCoin": false,
			"name": "thief",
			"value": 0
		},
		{
			"id": 656,
			"count": 10,
			"cost": 5,
			"deck": "Kingdom",
			"isAction": true,
			"isCoin": false,
			"name": "mine",
			"value": 0
		},
		{
			"id": 657,
			"count": 10,
			"cost": 5,
			"deck": "Kingdom",
			"isAction": true,
			"isCoin": false,
			"name": "festival",
			"value": 0
		},
		{
			"id": 658,
			"count": 10,
			"cost": 4,
			"deck": "Kingdom",
			"isAction": true,
			"isCoin": false,
			"name": "smithy",
			"value": 0
		},
		{
			"id": 659,
			"count": 10,
			"cost": 3,
			"deck": "Kingdom",
			"isAction": true,
			"isCoin": false,
			"name": "workshop",
			"value": 0
		},
		{
			"id": 660,
			"count": 10,
			"cost": 5,
			"deck": "Kingdom",
			"isAction": true,
			"isCoin": false,
			"name": "library",
			"value": 0
		},
		{
			"id": 661,
			"count": 10,
			"cost": 4,
			"deck": "Kingdom",
			"isAction": true,
			"isCoin": false,
			"name": "throne_room",
			"value": 0
		},
		{
			"id": 662,
			"count": 10,
			"cost": 6,
			"deck": "Kingdom",
			"isAction": true,
			"isCoin": false,
			"name": "adventurer",
			"value": 0
		},
		{
			"id": 663,
			"count": 10,
			"cost": 5,
			"deck": "Kingdom",
			"isAction": true,
			"isCoin": false,
			"name": "witch",
			"value": 0
		}
	],
	"cardSet": "Random"
}"*/