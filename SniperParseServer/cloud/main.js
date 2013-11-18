
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
        response.success("Hello world!");
        });

Parse.Cloud.beforeSave("Review", function(request, response) {

        var targets = request.object.get("m_alTargets");

        if(!targets || !(targets instanceof Array) || targets.length == 0)
        {

        var players = shuffleArray(request.object.get("m_alPlayers"));
        var targets = new Array();

        for(var i = 0; i < players.length - 1; ++i)
        {
        targets.push(players[i] + "-" + players[i +1]);
        }

        targets.push(players[players.length - 1] + "-" + players[0]);

        request.object.set("m_alTargets", targets);

        }

        response.success();  
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
