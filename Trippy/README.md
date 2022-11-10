# Trippy - Midterm project

### Creating database structure:

- Open create_table_scripts.ddl
- Run all scripts separated by dashes in descending order
- Run scripts separately

### URIs to manipulate data from the DB

- URIs to be appended to your localhost port URL.

### Review:

- @GetMapping 

api/review/all_reviews

api/reviews/{id}

- @PostMapping 

api/reviews/review_new

- @PutMapping 

api/review/{id}

- @DeleteMapping 

api/review/{id}

### User:

- @GetMapping 

api/user/all_users

api/user/user_id/{userId}

api/user/user_email/{userEmail}

api/user/user_name/{userName}

- @PostMapping 

api/user/new_user

### Venue:

- @GetMapping

api/venue/all_venues

api/venue/average_rating/{venueId}

api/venue/venue_type/{venueType}

api/venue/venue_city/{venueCity}

api/venue/

api/venue/

- @PostMapping

api/venue/new_venue

## Architecture:

- Followed standard N-tier architecture pattern 
- Packaged by feature
- Design influenced by https://phauer.com/2020/package-by-feature/

## What works:
- CRUD operations based on the specifications of the project
- Logging 
- DataSource connectivity
- Exception handling 
- Validation

## What doesn't work:
- Unit tests
- Could not adhere to the DTO design pattern