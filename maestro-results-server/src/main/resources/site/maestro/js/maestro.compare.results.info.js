function getSutInfo(id, num) {
    var sutUrl = "/api/test/" + id + "/sut"

    axios.get(sutUrl).then(
        function (response) {
            $("#sutName" + num).text(response.data.sutName);
            $("#sutVersion" + num).text(response.data.sutVersion);


            $("#sutId" + num).text("ID: " + response.data.sutId);
            $("#sutTags" + num).text("Tags: " + response.data.sutTags);

            if (response.data.sutJvmInfo == null || response.data.sutJvmInfo == "") {
                $("#sutJvmInfo" + num).text("JVM: no JVM info provided or not applicable for the SUT");
            }
            else {
                $("#sutJvmInfo" + num).text("JVM: " + response.data.sutJvmInfo);
            }

    })
    .catch(function (error) {
        console.log(error);
    });

    var testInfoUrl = "/api/test/" + id + "/number/" + num;

    axios.get(testInfoUrl).then(
        function (response) {

            $("#testId"+ num).text("ID: " + response.data.testId);
            $("#testName" + num).text("Name: " + response.data.testName);

            if (response.data.testTags == null || response.data.testTags == "") {
                $("#testTags" + num).text("Tags: no tags were provided for this test");
            }
            else {
                $("#testTags" + num).text("Tags: " + response.data.testTags);
            }

        }
    )
    .catch(function (error) {
        console.log(error);
    });
 }

$(document).ready(function() {
    var tId0 = getUrlVars()["t0"]
    var tNum0 = getUrlVars()["n0"]
    var tId1 = getUrlVars()["t1"]
    var tNum1 = getUrlVars()["n1"]

    getSutInfo(tId0, 0)
    getSutInfo(tId1, 1)
}

)
