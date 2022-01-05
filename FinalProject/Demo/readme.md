#  Capstone project : Ecobike

## Getting Started

Welcome to the Ecobike project. Here is a guideline to help you get started.
## Requirement
- Java11 or lower
- Gradle
- MySql ( at port 3306 )

## Folder Structure

The workspace contains the following folders, where:

- `src\main`: the folder to maintain sources
- `src\main\resource`: the folder to store static fxml file, ORM configuration.
- `src\test`: the folder for testing purpose


## Add VM arguments
In IntelliJ IDEA, click on `Run` -> `Edit Configurations...`  . Add VM options as below:
> `--add-opens java.base/java.net=ecobike --add-opens java.base/java.lang=ecobike --add-opens java.base/java.lang.reflect=ecobike`

## Database connector configuration
Open SQL database connection at port 3366 <br></br> 
Go to `src\main\resource\META-INF\persistence.xml` : Change `username` and `password` to your local database's configuration  


## Author
- Hoàng Huy Quân: 20183609
- Nguyễn Đức Thắng: 20183629
- Vũ Tấn Khang: 20183561
- Rattanak Neariroth: 20180278
