from time import sleep

from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options

chrome_options = Options()
chrome_options.binary_location = '/usr/bin/google-chrome'  # Adjust path as needed
# Path to the chromedriver executable on Windows
chromedriver_path = '/home/tkacza2/AAA/ProgramFiles/chromedriver'  # Update this path with the actual path to your chromedriver

service = Service(executable_path=chromedriver_path)
driver = webdriver.Chrome(service=service, options=chrome_options)


# Replace 'your_login_page_url' with the actual URL of the login page
driver.get("http://172.19.254.34:8080/organizaions")

organization_button = driver.find_element(By.XPATH, "/html/body/div[1]/section/div/div[1]/form/button")
organization_button.click()

select_button = driver.find_element(By.XPATH, '//*[@id="dorm"]')
select_button.click()

select_button = driver.find_element(By.XPATH, '/html/body/div/section/div/div[2]/form/select/option[1]')
select_button.click()

# Assuming 'email' and 'password' are the IDs of the input fields
email_input = driver.find_element(By.XPATH, '//*[@id="username"]')
password_input = driver.find_element(By.XPATH, '//*[@id="password"]')

# Replace these with the actual user credentials
email_input.send_keys("a@a.com")
password_input.send_keys("testpassword")

# Assuming 'login_button' is the ID of the login button
login_button = driver.find_element(By.XPATH, "/html/body/div/section/div/div[2]/form/input")
login_button.click()
