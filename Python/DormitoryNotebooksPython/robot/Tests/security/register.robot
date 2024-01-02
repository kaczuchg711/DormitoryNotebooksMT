*** Settings ***
Library  SeleniumLibrary
Library  register.py
Resource  ../../Resources/login.robot

*** Variables ***
${organization_id}  1

*** Test Cases ***
Register
    register_test