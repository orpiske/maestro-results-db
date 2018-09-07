$(document).ready(function() {
    var id = getUrlVars()["test-id"]
    var sutUrl = "/api/test/" + id + "/sut"

    axios.get(sutUrl).then(
        function (response) {
            console.log(response);

            $("#sutName").text(response.data.sutName);
            $("#sutVersion").text(response.data.sutVersion);


            $("#sutId").text("ID: " + response.data.sutId);
            $("#sutTags").text("Tags: " + response.data.sutTags);

            if (response.data.sutJvmInfo == null || response.data.sutJvmInfo == "") {
                $("#sutJvmInfo").text("JVM: no JVM info provided or not applicable for the SUT");
            }
            else {
                $("#sutJvmInfo").text("JVM: " + response.data.sutJvmInfo);
            }

    })
    .catch(function (error) {
        console.log(error);
    });

    var testInfoUrl = "/api/test/" + id + "/number/0";

    axios.get(testInfoUrl).then(
        function (response) {
            console.log(response);

            $("#testId").text("ID: " + response.data.testId);
            $("#testName").text("Name: " + response.data.testName);

            if (response.data.testTags == null || response.data.testTags == "") {
                $("#testTags").text("Tags: no tags were provided for this test");
            }
            else {
                $("#testTags").text("Tags: " + response.data.testTags);
            }

        }
    )
    .catch(function (error) {
        console.log(error);
    });
 }

)
