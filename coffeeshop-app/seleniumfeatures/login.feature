Feature: User Login 
	As a user
	I want to login to the application
	and be directed to the correct dashboard
	
	@Login
	Scenario Outline: the user should be able to login
			Given the user is on the login page
			When the user enters username as <username>
			And user enters password as <password>
			And the user clicks on login
			Then the user should see order or see order <button> 
			
			Examples:
			| username | password | button |
			| Customer | customer | ORDER COFFEE | 
			| Worker | worker | SEE ORDERS | 
