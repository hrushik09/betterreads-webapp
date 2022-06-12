# Better Reads

View the deployed [Website](https://betterreads-web-app.herokuapp.com/).

A highly available and scalable Spring Boot application that can handle millions of data records and thousands of
users.

This app stores the catalogue of every book ever published in the world.

Created a separate Spring Boot app [betterreads-data-loader](https://github.com/hrushik09/betterreads-data-loader) to process over 26 million lines of data from [Open Library](https://openlibrary.org/data) and stored it in Apache Cassandra for fast retrieval.

On homepage, users are requested to login via GitHub to save their reading progress. Users also have the ability to
search for a book without having to login.

After login, users are taken to the dashboard which shows the books currently being read, recently read and their user
ratings.

Clicking on any book here or on the search page will show the corresponding book page.

A book page shows the cover art, title, author names and description (if available). Logged-in users also get a form to
fill in the book progress. Users can:

- fill in the start date and end date of reading;
- mark a certain book as currently reading, finished or did not finish;
- give a rating.

Technologies used:

- Application Tier: Spring Boot
- Database: Apache Cassandra (hosted on DataStax)
- Data Layer: Spring Data Cassandra
- Security: Spring Security
- View Layer: Thymeleaf

--------------------------
Inspired by https://github.com/koushikkothagal/betterreads-webapp