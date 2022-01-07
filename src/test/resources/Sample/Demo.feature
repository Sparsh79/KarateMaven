Feature: Dummy Test

  Background:
    * url baseURL
    * path 'users'

  @demo
  Scenario: Demo
    * param page = '2'
    * method GET
    * status 200
    * print response
    * match $.page == '#present'