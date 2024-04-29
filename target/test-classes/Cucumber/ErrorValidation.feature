
@tag
Feature: Error Validation
  I want to use this template for my feature file
  
  Background: 
	Given I landed on E-Commerce website

  @ErrorValidation
  Scenario Outline: Negative Test of Entering Incorrect Login Credentials
    Given Logged in with email <email> and password <password>
    Then "Incorrect email or password." error message is displayed

    Examples: 
      | email  							 			| password 			 |
      | wrongEmail@gmail.com 			| wrongPasssword |
