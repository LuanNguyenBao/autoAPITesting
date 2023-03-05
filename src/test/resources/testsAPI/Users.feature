Feature: Create the new users.

  @API
  Scenario Outline: Verify create user api functionality
    Given setup create user api
    When set name as <name>
    And set gender as <gender>
    And set email as <email>
    And set status as <status>
    And creates user with the details provided
    Then verify the response code is <status_code>
    And verify the user is created successfully
    And cleanup the user data created
    Examples:
      | name         | gender | email                             | status   | status_code |
      | Alek Levin   | male   | alek_levin1@fakemail.com          | active   | 201         |
      | Khiem Cao    | male   | khiem_cao2@fakemail.com           | inactive | 201         |
      | Luan Nguyen  | male   | luan_nguyen3@fakemail.com         | active   | 201         |

  @API
  Scenario Outline: Verify update user api functionality
    Given update details of user with email <search_by_email>
    When update name of user to <name>
    And update gender of user to <gender>
    And update status of user to <status>
    And update email of user to <email>
    And test data is prepared
    And updates user with details provided
    Then verify the response code is <status_code>
    And verify the user details are updated successfully
    And cleanup the user data created
    Examples:
      | search_by_email            | name         | gender | email                             | status   | status_code |
      | alek_leven1@fakemail.com   | Alek Update  | male   | alek_levin_updated1@fakemail.com  | inactive | 200         |
      | khiem_cao2@fakemail.com    | Khiem Update | male   | khiem_cao_updated2@fakemail.com   | active   | 200         |
      | luan_nguyen3@fakemail.com  | Luan Update  | female | luan_nguyen_updated3@fakemail.com | active   | 200         |