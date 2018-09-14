function getTestProperties(testInfoUrl, num) {
    axios.get(testInfoUrl).then(
        function (response) {

            $("#apiName"+ num).text("API Name: " + response.data.apiName);
            $("#apiVersion" + num).text("API Version: " + response.data.apiVersion);
            $("#durable" + num).text("Durable: " + response.data.durable);
            $("#limitDestinations" + num).text("Destinations: " + response.data.limitDestinations);
            $("#messageSize" + num).text("Message Size: " + response.data.messageSize);
            $("#messagingProtocol" + num).text("Protocol: " + response.data.messagingProtocol);
            $("#variableSize" + num).text("Variable Size: " + response.data.variableSize);
            $("#maxAcceptableLatency" + num).text("Maximum Acceptable Latency: " + response.data.maxAcceptableLatency);
        }
    )
    .catch(function (error) {
        console.log(error);
    });
}