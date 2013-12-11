
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:

Parse.Cloud.define("hello", function(request, response) {
        response.success("Hello world!");
        });

Parse.Cloud.beforeSave("KillAction", function(request, response) {

        //if(!request.object.get("isVerified"))
        //Call Facial Recognition Code
        //request.object.set("isVerified", true);

        if(request.object.get("isVerified"))
        {




        var channel = "user_" + request.object.get("player").id;
        var targetName;
        var query = new Parse.Query(Parse.User);		
        //		query.equalTo("objectId", request.object.get("player").id);
        query.get(request.object.get("target").id, 
            {
success: function(target) {
// The object was retrieved successfully.
targetName = target.get("username");
},
error: function(object, error) {
// The object was not retrieved successfully.
// error is a Parse.Error with an error code and description.
}
});

Parse.Push.send({
channels: [ channel ],
data: {
title: "Kill Confirmed",
alert: targetName + " has been confirmed dead",
action: "com.sniper.CONFIRMED_KILL"
}
}, {
success: function() {
},
error: function(error) {
// Handle error
}
});

}

response.success();  
});

Parse.Cloud.afterSave("KillAction", function(request) {
        var channel = "user_" + request.object.get("target").id;
        var killerName = "Player";
        var query = new Parse.Query(Parse.User);		
        //		query.equalTo("objectId", request.object.get("player").id);
        query.get(request.object.get("player").id, {
success: function(player) {
// The object was retrieved successfully.
killerName = player.get("username");
},
error: function(object, error) {
// The object was not retrieved successfully.
// error is a Parse.Error with an error code and description.
}
});
        /*
           query.find({
success: function(results) {
alert("Successfully retrieved " + results.length + " scores.");
// Do something with the returned Parse.Object values
killerName = resuilts[0].get("username");
console.log(results);
},
error: function(error) {
alert("Error: " + error.code + " " + error.message);
}
});   */
if(!request.object.get("isVerified"))
{
    Parse.Push.send({
channels: [ channel ],
data: {
title: "Shot Down",
alert: killerName + " claims to have shot you",
action: "com.sniper.POTENTIAL_KILL",
killActionId: request.object.id,
URL: request.object.get("URL") 
}
}, {
success: function() {
},
error: function(error) {
// Handle error
}
});
}
response.success();
});


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

function shuffleArray(array) {
    for (var i = array.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    return array;
}
