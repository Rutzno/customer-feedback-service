# Customer-Feedback-Service
- In this project, we use Spring Boot with MongoDB database
- We also use docker to containerize a MongoDB database
- We have different services REST (endpoint) :
  - @PostMapping("/feedback") : to save, store a feedback ;
  - @GetMapping("/feedback/{id}") : to get a feedback by id ;
  - @GetMapping("/feedback") : to retrieve feedbacks by data filtering, sorting and paging through MongoRepository interface
- ... 
