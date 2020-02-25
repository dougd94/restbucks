Feature: The Logout feature
	As a logged in user
	I want to logout of the application
	So that my account cannot be accessed

Background:
	Given a user is logged in
Scenario: the logged-in user should be able to log out
	When a logged-in user clicks the logout button
	Then the Log in button is displayed