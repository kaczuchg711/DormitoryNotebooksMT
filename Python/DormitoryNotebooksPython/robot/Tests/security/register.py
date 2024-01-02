from time import sleep

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.firefox.options import Options as FirefoxOptions
def register_test():
    options = FirefoxOptions()
    options.add_argument("--headless")
    driver = webdriver.Firefox(options=options)
    driver.get("http://127.0.0.1:8000/")
    select_organization(driver,1)
    driver.find_element(By.XPATH, '/html/body/div/section/div/div/div[2]/div/form/button').click()
    driver.find_element(By.XPATH, '//*[@id="id_first_name"]').send_keys("Norbert")
    driver.find_element(By.XPATH, '//*[@id="id_last_name"]').send_keys("Kalik")
    driver.find_element(By.XPATH, '//*[@id="id_email"]').send_keys("kalik@kalik.com")
    driver.find_element(By.XPATH, '//*[@id="id_room"]').send_keys("206")
    driver.find_element(By.XPATH, '//*[@id="id_password1"]').send_keys("pomidorowa")
    driver.find_element(By.XPATH, '//*[@id="id_password2"]').send_keys("pomidorowa")
    driver.find_element(By.XPATH, '/html/body/div/section/form/button').click()
    driver.get("127.0.0.1:8000/create_basic_db")

    driver.close()

def select_organization(driver, organization=1):
    xpath = f"/html/body/div/section/div/div[{organization}]/form/button"
    driver.find_element(By.XPATH, xpath).click()