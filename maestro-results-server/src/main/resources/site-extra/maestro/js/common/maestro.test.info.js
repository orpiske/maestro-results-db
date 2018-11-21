function getTestInfo(testInfoUrl, num) {
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