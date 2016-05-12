
var data = null;

function test1()
{
    host.print("test 1");
}

function test2()
{
    
    host.print("test 2");
}

function create()
{
    data = {
        current: 0,
        players: host.getPlayers(),
        card: {
            getSpecials: function() { return [ 'test1', 'test2']; },
            getCards: function() { return 5; },
            actions:  function() { return 6; },
            buys:  function() { return 7; }
        }
    };    
    return data.card;
}

function setData(value) 
{
    data = JSON.parse(value);
}

function getData()
{
    return JSON.stringify(data);
}
