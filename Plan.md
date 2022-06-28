list of US:

US-1 As a user, I would like to see all my rental agreements
US-2 As a user, I would like to click on a rental agreement and see all details about the house
US-3 As an admin, I would like to see all tenants currently living in particular house
US-4 As an admin I would like to create new rental agreements, tenants and houses
US-5 As an admin, I would like to update a rental agreement to change a tenant
US-6 As an admin I would like to update all information about a rental agreement, its tenants and its houses
US-7 As an admin, I would like to delete a rental agreement




sorted by size:
US-1            link user with tenant - onetoone username ?
US-7            
US-3
US-2
US-4
US-5
US-6



TODO:
Setup database          local: DONE     digital Ocean:
Setup entityclasses     DONE    not testet     
Setup DTO's             DONE                    
Sortout git and maven deploy    remove the tests that will fail, as the start project has some.
Maven autodeploy backend working ?
Setup basic website
Setup login system - work on US- try and hold it to iterations.
    Facades
    Tests
    implement functionallity to website, this should be done in iterations, as per userstory - work on this, this is your usual problem, get points where you can.
Setup autodeploy frontend


progress:       Frontend    Backend     Tests   Deployment(B/F)
US-1
US-7
US-3
US-2
US-4
US-5
US-6



//todo look into username ? onetoone? how to implement it into the Tenant class and DTO




optimise - fixes if time (there wont be)-


phonenumber is int... should be long only goes to 9+change digits (10 if first number is 1)
startDate and endDate are both int. thats a mistake. for ref. i will use DDMMYYYY format.