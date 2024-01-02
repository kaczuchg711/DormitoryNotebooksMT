*** Settings ***
Library  SeleniumLibrary
Library  Dialogs

Resource  ../Resources/login.robot

*** Keywords ***
Remove breakdown
    Go to request breakdown page
    pause execution
    Click Button  xpath=/html/body/div/table/tbody/tr[2]/th[7]/button
    pause execution

Request breakdown
    Go to request breakdown page
    Click element  xpath=/html/body/section/div/div/form/button

Go to request breakdown page
    Click element  xpath=/html/body/section/div/div/div[2]/form/button
    Wait Until Element Is Visible  xpath=//*[@id="id_description"]
    Input Text  xpath=//*[@id="id_description"]  przykladowy opis


