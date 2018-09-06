$(document).ready(function() {
    var id = getUrlVars()["test-id"]
    var url = "/api/test/" + id + "/sut"

    axios.get(url).then(
        function (response) {
            console.log(response);

            $("#sutName").text(response.data.sutName);
            $("#sutVersion").text(response.data.sutVersion);
    })
    .catch(function (error) {
        console.log(error);
      });
    }
)
