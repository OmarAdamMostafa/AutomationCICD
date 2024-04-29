@tag
Feature: Purchase the order from E-Commerce website
  I want to use this template for my feature file

	Background: 
	Given I landed on E-Commerce website

  @Regression
  Scenario Outline: Positive Test of Submitting the Order
    Given Logged in with email <email> and password <password>
    When I add the product <productName> to Cart
    And Checkout product <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." is displayed in ConfirmationPage
    
    Examples: 
      | email  							 			| password 		| productName  		|
      | polegywork@gmail.com 			| Roltoma1234 | ZARA COAT 3  		|
      | omarahmed123443@gmail.com | Roltoma1234 | ADIDAS ORIGINAL |
