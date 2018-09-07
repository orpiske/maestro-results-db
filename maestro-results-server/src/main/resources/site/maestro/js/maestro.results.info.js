$(document).ready(function() {
    var id = getUrlVars()["test-id"]
    var url = "/api/test/" + id + "/sut"

    axios.get(url).then(
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
    }
)
