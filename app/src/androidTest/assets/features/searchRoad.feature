Feature: Search roads to check the status

    @search-feature
    Scenario Outline: Valid road
        Given the user is in MainActivity
        When the user types a valid id <id>
        And the user clicks on search button
        Then road name <road name> is displayed

        Examples:
            | id         | road name  |
            | a1         | A1         |
            | a2         | A2         |
            | city route | City Route |
            | inner ring | Inner Ring |

    @search-feature
    Scenario Outline: Invalid road
        Given the user is in MainActivity
        When the user types an invalid id <id>
        And the user clicks on search button
        Then an error message <error message> is displayed

        Examples:
            | id  | error message                    |
            | m25 | The road above is not recognised |