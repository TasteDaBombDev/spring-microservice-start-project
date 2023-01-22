This project is a kickoff starting point for a microservice apporach for Spring Framework. 

# Prerequisites

You need java 16 to run this project.

You need mysql db named postme for this app. Did not had time to create migrations

# Starting the project

1. Clone the repository
2. Open it in idea (the root folder)
3. Install all the dependeincies in all microservices (Idea does it by default)
3. Create a simple spring configuration for each microservice where you set only the coresponding main class in the configuration tab
4. Run the Core service and wait to become available
5. Monitor the status of all Eureka microservices in localhost:8761
6. Run Gateway service and wait to become active
7. Run all other services

# Running the Migration

There is no need to run the migration. It is run automatically in each microservice. Just make sure you have the database named 'postme'

