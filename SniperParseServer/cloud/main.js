
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:

Parse.Cloud.define("hello", function(request, response) {
    response.success("Hello world!");
});

Parse.Cloud.beforeSave("KillAction", function(request, response) {
    //Pull request information
    var channel = "user_" + request.object.get("player");
    var currPlayer = request.object.get("player");
    var currTarget = request.object.get("target");
    var killVerified = request.object.get("isVerified");
    var targetName = "Target";
        
        //If the kill is being updated as a success
        if(killVerified)
        {
            //Query for user and send push notification
            console.log("Kill being verified");
            var user = new Parse.User;
            user.id = request.object.get("target");
            user.fetch({
                success: function(user){
                    targetName = user.get("username");
                    Parse.Push.send({
                        channels: [ channel ],
                        data: {
                            title: "Kill Confirmed",
                            alert: targetName + " has been confirmed dead",
                            action: "com.sniper.CONFIRMED_KILL"
                        }
                    }, {
                        success: function() {
                            response.success();
                        },
                        error: function(error) {
// Handle error
                        }
                    });
                }
            });
            console.log("Confirmed notification sent");
            //Query for game associated with kill
            var Game = Parse.Object.extend("Game");
            var query = new Parse.Query(Game);
            query.equalTo("players", currPlayer);
            query.lessThanOrEqualTo("startTime", new Date());
            query.greaterThanOrEqualTo("endTime", new Date()); 
            console.log("Starting games query"); 
            query.find({
                success: function(results) {
                    console.log("Found some gamez");
                    // Iterate over games to remove killed player
                    for (var i = 0; i < results.length; i++) { 
                        console.log("Processing game: ", results[i].get("name"));
                        //Remove player from game
                        var players = results[i].get("players");
                        console.log("Before remove players: " + players);
                        for(var j = 0; j < players.length; j++) {
                            if(players[j] == currTarget) {
                                players.splice(j, 1);
                            }
                        }
                        
                        console.log("After remove players: " + players);
                        //Reassign targets
                        var targets = results[i].get("targets");
                        console.log("Before remove targets: " + targets);
                        var newHunter, newTarget, p1, p2;
                        for(var j = 0; j < targets.length; j++) {
                            currPair = targets[j];
                           if(currPair.indexOf(currTarget) == 0) {
                                console.log("Pair 1: " + currPair);
                                newTarget = currPair.substring(11);
                                p1 = currPair;
                            } else if(currPair.indexOf(currTarget) == 11) {
                                console.log("Pair 2: " + currPair);
                                newHunter = currPair.substring(0, 10);
                                p2 = currPair;
                            }
                        }
                        //Remove pair 1
                        for(var j = 0; j < targets.length; j++) {
                            if(targets[j] == p1) {
                                targets.splice(j, 1);
                                break;
                            }
                        }
                        //Remove pair 2
                        for(var j = 0; j < targets.length; j++) {
                            if(targets[j] == p2) {
                                targets.splice(j, 1);
                                break;
                            }
                        }
                        var newSet = newHunter + "-" + newTarget;
                        targets.push(newSet);
                        console.log("After remove targets: " + targets);
                        
                        
                        //If game is over, send push notification and set end game
                        //time to now
                     if(players.length <= 1) {
                        gameChannel = "game_" + results[i].id;
                        Parse.Push.send({
                        channels: [ gameChannel ],
                        data: {
                            title: "GAME OVER",
                            alert: currPlayer.get("firstName") + " has won",
                            action: "com.sniper.CONFIRMED_KILL"
                        }
                        }, {
                        success: function() {
                            response.success();
                        },
                        error: function(error) {
// Handle error
                        }
                        });
                        results[i].set("endTime", new Date());
                      }
                      //Save game changes
                        results[i].set("players", players);
                        results[i].set("targets", targets);
                        results[i].save();
                    }
                },
                error: function(error) {
                    console.error("Error: " + error.code + " " + error.message);
                }  
            });
        }
        else
        { 
            response.success();
        }
});

Parse.Cloud.afterSave("KillAction", function(request) {
    //Pull in request information
    var channel = "user_" + request.object.get("target");
    var killVerified = request.object.get("isVerified");
    var killerName = "Player";

    //Query for usera associated and send push notification
    var user = new Parse.User;
    user.id = request.object.get("player");
    user.fetch({
        success: function(user){
            killerName = user.get("username");
            if(!killVerified)
            {
                Parse.Push.send({
                    channels: [ channel ],
                    data: {
                        //title: "Shot Down",
                        //alert: killerName + " claims to have shot you",
                        action: "com.sniper.POTENTIAL_KILL",
                        killActionId: request.object.id,
                        URL: request.object.get("URL") 
                    }
                }, {
                    success: function() {
                        response.success("Potential Kill Notification Sent");
                    },
                    error: function(error) {
// Handle error
}
});
            }
            else
            {
                response.success("Kill Action Saved");
            }
        }
    });
});


//Function to send a start game push notification to all players in game
function setUpGameStartNotification(gameId, gameName, startTime)
{
    var query = new Parse.Query(Parse.Installation);
    query.equalTo('channels', gameId);

    Parse.Push.send({
        where: query,
        data: {
            title: gameName + "has begun",
            alert: gameName + "has begun at " + startTime.toString()  
        },
        push_time: startTime 
    }, 
    {
        success: function() {
// Push was successful
},
error: function(error) {
// Handle error
}
});
}

//Before save on Game, every time a player is added, re-generate
//a new targets array
Parse.Cloud.beforeSave("Game", function(request, response) {

    var startDate = new Date(request.object.get("startTime"));
    var currentDate = new Date(); 

    request.object.set("debugInfo", "Start Date is: " + startDate.toString());

    if(currentDate <= startDate)
    {

        var players = shuffleArray(request.object.get("players"));
        var targets = new Array();

        for(var i = 0; i < players.length - 1; ++i)
            targets.push(players[i] + "-" + players[i +1]);

        targets.push(players[players.length - 1] + "-" + players[0]);

        request.object.set("targets", targets);

    }

    response.success();  
});

Parse.Cloud.afterSave("Game", function(request) 
{
    if(!request.object.get("notifSent"))
    {
        request.object.set("notifSent", "sent");
        setUpGameStartNotification(request.object.id, request.object.get("name"), new Date(request.object.get("startTime")));
    }
});

//shuffles an array indexes
function shuffleArray(array) {
    for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    return array;
}
