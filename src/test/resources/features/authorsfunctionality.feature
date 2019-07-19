Feature: Authors functionality.
  Users should be able to list existing authors, update information about authors, delete or add new authors
  As a user
  I want to get list of authors
  In order to work with books of those authors

  Scenario: User wants to get list of authors
    Given list of authors exists
    When I want to get list of all the authors
    Then all authors are enlisted

  Scenario: User wants to rent a book which is available
    Given book 'Flauberts Parrot' exists in library
    And it is available
    When I want to rent a book 'Flauberts Parrot'
    Then book will be rented to me

  Scenario: User wants to rent a book which is not available
    Given book 'Flauberts Parrot' exists in library
    And it is not available
    When I want to rent a book 'Flauberts Parrot'
    Then book will not be rented to me
    And status unavailable will be shown








