echo "start mvn clean instal"
mvn clean install
repeat_command "echo" 10
echo "start mvn spring:runserver"
mvn spring-boot:run