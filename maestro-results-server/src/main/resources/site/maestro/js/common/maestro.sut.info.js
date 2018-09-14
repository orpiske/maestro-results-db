function getSutInfo(sutUrl, num) {
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
}