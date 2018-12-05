Maestro Results DB: Using
============

To make use of the Results DB, please make sure that the following extra variables are exported on every test: 


**Singlepoint Test Variables**: 

| Variable Name    | Default Value       | Description          |
|-------------------|---------------------|----------------------|
| `SUT_NAME` | `null` | The name of the Software Under Test (SUT) |
| `SUT_VERSION` | `null` | The version of the SUT |
| `SUT_TAGS` | `null` | Test tags associated with the SUT deployment |
| `SUT_ID` | `null` | Optional SUT ID  |
| `TEST_TAGS` | `null` | Some test tags associated with the test in execution |
| `LAB_NAME` | `null` | The laboratory name that identifies where the SUT machine is running |