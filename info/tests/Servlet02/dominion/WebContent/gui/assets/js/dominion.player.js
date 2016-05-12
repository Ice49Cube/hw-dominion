
// Part of Game namespace
window.Game = window.Game || {};

/**
 * createPlayer factory method
 */
// Factory pattern, createPlayer is a static method of the global Game object,
// it creates and returns a player object.
window.Game.createPlayer = function (name) {
	"use strict";
	// note: name is a private variable
	
	var cardsInHand = [];
	var cardsInStack = [];
	
	// just a private function for demo,
	// could be done in the other functions
	var addCardToArray = function(array, card) {
		array.push(card);
	};
	
	// these are also private, but MADE PUBLIC, see below
	var addCardToHand = function(card) {
		addCardToArray(cardsInHand, card);
		// here should be:
		//cardsInHand.push(card);
	};
		
	var addCardToStack = function(card) {
		addCardToArray(cardsInStack, card);
		// WHAT COULD BE HERE INSTEAD OF THE LINE ABOVE???
	};
	
	var getName = function() {
		return name;
	};
	
	// return player object(rather an interface)
	// make private methods public
	return {
		addCardToHand: addCardToHand,
		addCardToStack: addCardToStack,
		getName: getName,
		thisVariableIsBothPublicAndPrivate: "yes, and maybe a bad idea, interface is \"nicer\"..."
	};
};
