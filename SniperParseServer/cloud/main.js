
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:

Parse.Cloud.define("hello", function(request, response) {
        response.success("Hello world!");
        });

Parse.Cloud.beforeSave("KillAction", function(request, response) {


        response.success();  
        });

Parse.Cloud.afterSave("KillAction", function(request) {
        var channel = "user_" + request.object.get("target");
        Parse.Push.send({
            channels: [ channel ],
            data: {
                title: "Shot Down",
                alert: request.object.get("player") + " claims to have shot you"
                }
            }, {
            success: function() {
            },
            error: function(error) {
                // Handle error
            }
        });

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

        request.object.set("debugInfo", "Start Date is valid: " + (startDate instanceof Date));

        if(currentDate.timeNow() <= startDate)
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
